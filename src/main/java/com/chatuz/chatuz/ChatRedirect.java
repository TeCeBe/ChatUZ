package com.chatuz.chatuz;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;

import jakarta.json.*;

import java.sql.*;

public class ChatRedirect extends HttpServlet {

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError(e);
        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String userName = (String) session.getAttribute("username");
        if (userName == null || userName.isEmpty()) {
            userName = "Anonim";
        }
        String nick = request.getParameter("nickname");
        request.setAttribute("nick", nick);
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://sql.freedb.tech:3306/freedb_chatuz?useSSL=false", "freedb_tecebe", "%Dn@fSFRz&ph7%3");
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM (SELECT date, id_from, id_to, message FROM messages WHERE (id_from='" + userName + "' AND id_to='" + nick + "') OR (id_to='" + userName + "' AND id_from='" + nick + "') ORDER BY date DESC LIMIT 100) AS subquery ORDER BY date ASC;");
            ResultSet rs = ps.executeQuery();
            JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
            while (rs.next()) {
                JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
                jsonObjectBuilder.add("date", rs.getString("date").toString());
                jsonObjectBuilder.add("id_from", rs.getString("id_from").toString());
                jsonObjectBuilder.add("id_to", rs.getString("id_to").toString());
                jsonObjectBuilder.add("message", rs.getString("message").toString());
                jsonArrayBuilder.add(jsonObjectBuilder.build());
            }
            request.setAttribute("data", jsonArrayBuilder.build().toString());
        } catch (SQLException e) {
            throw new ServletException("Błąd podczas komunikacji z bazą danych", e);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/chat.jsp");
        dispatcher.forward(request, response);
    }

    private Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://sql.freedb.tech:3306/freedb_chatuz?useSSL=false";
        String user = "freedb_tecebe";
        String password = "%Dn@fSFRz&ph7%3";
        return DriverManager.getConnection(url, user, password);
    }
}
