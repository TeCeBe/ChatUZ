package com.chatuz.chatuz;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;

import jakarta.json.*;

import java.sql.*;

public class ChatRedirect extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nick = request.getParameter("nickname");
        request.setAttribute("nick", nick);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/chat.jsp");
        dispatcher.forward(request, response);
        /*try {
            // Utwórz połączenie z bazą danych
            Connection conn = DriverManager.getConnection("jdbc:mysql://sql.freedb.tech:3306/freedb_chatuz?useSSL=false", "freedb_tecebe", "%Dn@fSFRz&ph7%3");

            // Wykonaj zapytanie SQL
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM messages WHERE 'id_from'=sesja AND 'id_to'=link LIMIT 100");
            ResultSet rs = ps.executeQuery();

            // Przekształć wyniki zapytania na łańcuch JSON
            JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
            while (rs.next()) {
                JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
                jsonObjectBuilder.add("id", rs.getInt("id"));
                jsonObjectBuilder.add("message", rs.getString("message"));
                // Dodaj inne pola, jeśli są potrzebne
                jsonArrayBuilder.add(jsonObjectBuilder.build());
            }

            // Przekaż dane do strony JSP
            request.setAttribute("data", jsonArrayBuilder.build().toString());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/chat.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Błąd podczas komunikacji z bazą danych", e);
        }
    }*/
    }
}

/*
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>My JSP Page</title>
    <script>
        window.onload = function() {
            var data = JSON.parse('${data}');
            var div = document.getElementById('myDiv');
            for (var i = 0; i < data.length; i++) {
                var p = document.createElement('p');
                p.textContent = data[i].message;
                div.appendChild(p);
            }
        };
    </script>
</head>
<body>
<div id="myDiv"></div>
</body>
</html>
*/
