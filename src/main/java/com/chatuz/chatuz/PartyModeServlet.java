package com.chatuz.chatuz;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

public class PartyModeServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int partyMode;
        HttpSession session = req.getSession();
        String userName = (String) session.getAttribute("username");
        String partyModeParam = req.getParameter("partyMode");
        if (partyModeParam != null) {
            partyMode = Integer.parseInt(partyModeParam);
            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://sql.freedb.tech:3306/freedb_chatuz?useSSL=false", "freedb_tecebe", "%Dn@fSFRz&ph7%3");
                PreparedStatement ps = conn.prepareStatement("UPDATE users SET party_mode=? WHERE username=?");
                ps.setString(1, partyModeParam);
                ps.setString(2, userName);
                int rowsUpdated = ps.executeUpdate();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
