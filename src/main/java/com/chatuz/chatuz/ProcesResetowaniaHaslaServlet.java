package com.chatuz.chatuz;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

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
            // Sprawdzenie zgodności i długości hasła
            if (!newPassword.equals(confirmPassword)) {
                request.setAttribute("error", "Hasla nie są takie same.");
                request.getRequestDispatcher("resetowaniehasla.jsp").forward(request, response);
                return;
            }

            if (newPassword.length() < 8) {
                request.setAttribute("error", "Haslo musi miec co najmniej 8 znaków.");
                request.getRequestDispatcher("resetowaniehasla.jsp").forward(request, response);
                return;
            }

            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
                 PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE email = ? AND reset_token = ?")) {

                ps.setString(1, email);
                ps.setString(2, token);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        Timestamp tokenExpiration = rs.getTimestamp("token_expiry");
                        Timestamp currentTime = new Timestamp(System.currentTimeMillis());

                        if (currentTime.before(tokenExpiration)) {
                            // Token jest prawidłowy, aktualizacja hasła
                            try (PreparedStatement psUpdate = con.prepareStatement(
                                    "UPDATE users SET password = ?, reset_token = NULL, token_expiry = NULL WHERE email = ?")) {
                                psUpdate.setString(1, newPassword); // Hasło powinno być zahashowane
                                psUpdate.setString(2, email);
                                psUpdate.executeUpdate();

                                // Ustawianie komunikatu o sukcesie w sesji
                                HttpSession session = request.getSession();
                                session.setAttribute("message-success", "Haslo zmienione pomyslnie");
                                response.sendRedirect("login.jsp");
                            }
                        } else {
                            // Token wygasł
                            request.setAttribute("error", "Token uwierzytelniania wygasł");
                            request.getRequestDispatcher("resetowaniehasla.jsp").forward(request, response);
                        }
                    } else {
                        // Błędny token lub email
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
