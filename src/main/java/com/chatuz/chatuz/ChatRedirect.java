package com.chatuz.chatuz;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;

import jakarta.json.*;

import java.sql.*;
import java.util.Objects;

/*Servlet służący do obsługi wybierania odbiorcy czatu.
Sposób działania:
URL/ChatRedirect?username=x, gdzie x=nazwa użytkownika.
Nazwa użytkownika jest sprawdzana czy w ogóle istnieje, jeśli nie to przekierowuje do specjalnej strony.
 */

public class ChatRedirect extends HttpServlet {

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError(e);
        }
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //uzyskaj sesje
        HttpSession session = request.getSession();
        String userName = (String) session.getAttribute("username");
        //sprawdź sesję, niemożliwy brak sesji z powodu filtru, funkcja niepotrzebna po zrobieniu 100%
        if (userName == null || userName.isEmpty()) {
            userName = "Anonim";
        }
        //uzyskaj nick z sesji
        String nick = request.getParameter("nickname");
        request.setAttribute("nick", nick);
        //łączenie z bazą, sprawdzenie, czy użytkownik w ogóle istnieje by nie pisać do kogoś kto nie istnieje
        try {
            //łączenie
            Connection conn = DriverManager.getConnection("jdbc:mysql://sql.freedb.tech:3306/freedb_chatuz?useSSL=false", "freedb_tecebe", "%Dn@fSFRz&ph7%3");
            PreparedStatement usercheck = conn.prepareStatement("SELECT COUNT(*) AS count FROM users WHERE username='" + nick + "';");
            ResultSet usercheckrs = usercheck.executeQuery();
            //sprawdzanie
            if (usercheckrs.next())
            {
                if (Objects.equals(usercheckrs.getString("count"), "0"))
                {
                    //jeśli nie znaleziono to przekieruj
                    response.sendRedirect("not-found.jsp");
                }
                else
                {
                    //jeśli znaleziono idź dalej, łączenie z bazą
                    //odcyzt wiadomości
                    PreparedStatement ps = conn.prepareStatement("SELECT * FROM (SELECT date, id_from, id_to, message FROM messages WHERE (id_from='" + userName + "' AND id_to='" + nick + "') OR (id_to='" + userName + "' AND id_from='" + nick + "') ORDER BY date DESC LIMIT 100) AS subquery ORDER BY date ASC;");
                    ResultSet rs = ps.executeQuery();
                    //odczyt trybu imprezy z bazy
                    PreparedStatement pmcheck = conn.prepareStatement("SELECT party_mode FROM users WHERE username='" + userName + "';");
                    ResultSet pmrs = pmcheck.executeQuery();
                    //zaktualizuj stan checkboxa
                    if (pmrs.next())
                    {
                        request.setAttribute("party_mode", pmrs.getInt("party_mode"));
                    }
                    //stwórz arraya z wiadomościami aby potem go przesłać do strony
                    JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
                    while (rs.next()) {
                        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
                        jsonObjectBuilder.add("date", rs.getString("date").toString()); //data
                        jsonObjectBuilder.add("id_from", rs.getString("id_from").toString()); //id od kogo
                        jsonObjectBuilder.add("id_to", rs.getString("id_to").toString()); //id do kogo
                        jsonObjectBuilder.add("message", rs.getString("message").toString()); //treść
                        jsonArrayBuilder.add(jsonObjectBuilder.build());
                    }
                    request.setAttribute("data", jsonArrayBuilder.build().toString());
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/chat.jsp"); //chat.jsp plik ze stroną chatu
                    dispatcher.forward(request, response); //dokonaj przekirowania z przekazainem argumentów
                }
            }

        } catch (SQLException e) {
            throw new ServletException("Błąd podczas komunikacji z bazą danych", e);
        }
    }

    private Connection getConnection() throws SQLException {
        //komunikacja z bazą
        String url = "jdbc:mysql://sql.freedb.tech:3306/freedb_chatuz?useSSL=false";
        String user = "freedb_tecebe";
        String password = "%Dn@fSFRz&ph7%3";
        return DriverManager.getConnection(url, user, password);
    }
}
