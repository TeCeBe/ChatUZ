package com.chatuz.chatuz;

import java.io.*;
import jakarta.servlet.http.*;
import java.sql.*;
import org.mindrot.jbcrypt.BCrypt;

public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Statyczny blok inicjalizacyjny do ładowania sterownika MySQL
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError(e);
        }
    }

    // Obsługa metody POST dla logowania użytkowników
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Sprawdzanie poprawności danych logowania
        if (isValidLogin(username, password)) {
            // Ustawienie atrybutu sesji dla zalogowanego użytkownika
            HttpSession session = request.getSession();
            session.setAttribute("username", username);

            // Przekierowanie do strony głównej po pomyślnym logowaniu
            response.sendRedirect("Main.jsp");
        } else {
            // Przekierowanie do formularza logowania przy błędnym logowaniu
            response.sendRedirect("login.jsp?error=invalid_login");
        }
    }

    // Metoda do sprawdzania poprawności danych logowania
    private boolean isValidLogin(String username, String password) {
        try (Connection conn = getConnection()) {
            String query = "SELECT password FROM users WHERE username = ?";
            try (PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setString(1, username);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        // Pobieranie i weryfikacja hashowanego hasła
                        String storedHash = rs.getString("password");
                        return BCrypt.checkpw(password, storedHash);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Metoda do nawiązywania połączenia z bazą danych
    private Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://sql.freedb.tech:3306/freedb_chatuz?useSSL=false";
        String user = "freedb_tecebe";
        String password = "%Dn@fSFRz&ph7%3";
        return DriverManager.getConnection(url, user, password);
    }
}
