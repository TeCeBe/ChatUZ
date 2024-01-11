package com.chatuz.chatuz;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

/*Ten servlet służy do obsługi aktualizacji stanu checkboxa Trybu imprezy z bazą danych.
Status w bazie danych nie wpływa na działanie trybu, ponieważ jest on realizowany w chat.jsp w javascripcie.
 */

public class PartyModeServlet extends HttpServlet {


    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //odczyt parametrów z requesta oraz sesji
        int partyMode;
        HttpSession session = req.getSession();
        String userName = (String) session.getAttribute("username");
        String partyModeParam = req.getParameter("partyMode");
        //jeśli request nie jest pusty to...
        if (partyModeParam != null) {
            //parsowanie do int
            partyMode = Integer.parseInt(partyModeParam);
            try {
                //łączenie z bazą danych
                Connection conn = DriverManager.getConnection("jdbc:mysql://sql.freedb.tech:3306/freedb_chatuz?useSSL=false", "freedb_tecebe", "%Dn@fSFRz&ph7%3");
                PreparedStatement ps = conn.prepareStatement("UPDATE users SET party_mode=? WHERE username=?");
                ps.setString(1, partyModeParam);
                ps.setString(2, userName);
                //wykonaj aktualizację stanu
                int rowsUpdated = ps.executeUpdate();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
