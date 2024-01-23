package com.chatuz.chatuz;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import org.mindrot.jbcrypt.BCrypt;

public class ProcesResetowaniaHaslaServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String token = request.getParameter("token");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        String dbUrl = "jdbc:mysql://sql.freedb.tech:3306/freedb_chatuz?useSSL=false";
        String dbUser = "freedb_tecebe";
        String dbPassword = "%Dn@fSFRz&ph7%3";

        try {
            if (!newPassword.equals(confirmPassword)) {
                request.setAttribute("error", "Hasla nie sa takie same.");
                request.getRequestDispatcher("resetowaniehasla.jsp").forward(request, response);
                return;
            }

            if (newPassword.length() < 8) {
                request.setAttribute("error", "Haslo musi miec co najmniej 8 znaków.");
                request.getRequestDispatcher("resetowaniehasla.jsp").forward(request, response);
                return;
            }

            // Hashowanie hasła przy użyciu BCrypt
            String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());

            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
                 PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE email = ? AND reset_token = ?")) {

                ps.setString(1, email);
                ps.setString(2, token);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        Timestamp tokenExpiry = rs.getTimestamp("token_expiry");
                        Timestamp currentTime = new Timestamp(System.currentTimeMillis());

                        if (currentTime.before(tokenExpiry)) {
                            try (PreparedStatement psUpdate = con.prepareStatement(
                                    "UPDATE users SET password = ?, reset_token = NULL, token_expiry = NULL WHERE email = ?")) {
                                psUpdate.setString(1, hashedPassword);
                                psUpdate.setString(2, email);
                                psUpdate.executeUpdate();

                                HttpSession session = request.getSession();
                                session.setAttribute("message-success", "Haslo zmienione pomyslnie");
                                response.sendRedirect("login.jsp");
                            }
                        } else {
                            request.setAttribute("error", "Token uwierzytelniania wygasl");
                            request.getRequestDispatcher("resetowaniehasla.jsp").forward(request, response);
                        }
                    } else {
                        request.setAttribute("error", "Bledny email lub token uwierzytelniania");
                        request.getRequestDispatcher("resetowaniehasla.jsp").forward(request, response);
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new ServletException("Database access error.", e);
        }
    }
}
