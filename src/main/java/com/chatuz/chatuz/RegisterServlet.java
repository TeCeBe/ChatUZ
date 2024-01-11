package com.chatuz.chatuz;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.sql.*;
import org.mindrot.jbcrypt.BCrypt;

public class RegisterServlet extends HttpServlet {

    // Obsługa metody POST dla rejestracji użytkowników
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {

        // Pobieranie danych użytkownika z żądania
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email"); // Pobieranie adresu e-mail

        // Walidacja długości hasła
        if (password == null || password.length() < 8) {
            try {
                // Przekierowanie do formularza rejestracji z błędem o krótkim haśle
                response.sendRedirect("register.jsp?error=short_password");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }

        // Hashowanie hasła przy użyciu BCrypt
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // Łączenie z bazą danych
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://sql.freedb.tech:3306/freedb_chatuz?useSSL=false", "freedb_tecebe", "%Dn@fSFRz&ph7%3");

            // Sprawdzanie, czy nazwa użytkownika jest już zajęta
            ps = con.prepareStatement("SELECT username FROM users WHERE username = ?");
            ps.setString(1, username);
            rs = ps.executeQuery();

            if (rs.next()) {
                // Przekierowanie do formularza rejestracji z błędem o zajętej nazwie użytkownika
                response.sendRedirect("register.jsp?error=username_taken");
            } else {
                // Sprawdzanie, czy email jest już używany
                ps = con.prepareStatement("SELECT email FROM users WHERE email = ?");
                ps.setString(1, email);
                rs = ps.executeQuery();

                if (rs.next()) {
                    // Przekierowanie do formularza rejestracji z błędem o zajętym e-mailu
                    response.sendRedirect("register.jsp?error=email_taken");
                } else {
                    // Dodawanie nowego użytkownika do bazy danych
                    ps = con.prepareStatement("INSERT INTO users (username, password, email) VALUES (?, ?, ?)");
                    ps.setString(1, username);
                    ps.setString(2, hashedPassword);
                    ps.setString(3, email);
                    int i = ps.executeUpdate();

                    if (i > 0) {
                        // Przekierowanie do strony logowania po pomyślnej rejestracji
                        response.sendRedirect("login.jsp");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Database access error.", e);
        } finally {
            // Zamykanie zasobów bazy danych
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
