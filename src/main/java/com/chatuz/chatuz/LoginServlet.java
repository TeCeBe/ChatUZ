package com.chatuz.chatuz;

import java.io.*;
import jakarta.servlet.http.*;
import java.sql.*;
import org.mindrot.jbcrypt.BCrypt;

public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Statyczny blok inicjalizacyjny do ładowania sterownika bazy danych MySQL
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        // Pobieranie nazwy użytkownika i hasła z żądania
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            // Sprawdzanie poprawności danych logowania
            if (isValidLogin(username, password)) {
                // Utworzenie sesji i przekierowanie do głównej strony aplikacji
                HttpSession session = request.getSession();
                session.setAttribute("username", username);
                response.sendRedirect("FriendsServlet");
            } else {
                // Przekierowanie do strony logowania z komunikatem o błędzie
                response.sendRedirect("login.jsp?error=invalid_login");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Metoda do sprawdzania poprawności logowania (zmieniona na package-private do testowania)
    boolean isValidLogin(String username, String password) throws SQLException {
        try (Connection conn = getConnection()) {
            // Przygotowanie zapytania SQL do weryfikacji użytkownika
            String query = "SELECT password FROM users WHERE username = ?";
            try (PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setString(1, username);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        // Porównanie hasła podanego przez użytkownika z hasłem w bazie danych
                        String storedHash = rs.getString("password");
                        return BCrypt.checkpw(password, storedHash);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    // Prywatna metoda do nawiązywania połączenia z bazą danych
    private Connection getConnection() throws SQLException {
        // Dane do połączenia z bazą danych
        String url = "jdbc:mysql://sql.freedb.tech:3306/freedb_chatuz?allowPublicKeyRetrieval=true&useSSL=false";
        String user = "freedb_tecebe";
        String password = "%Dn@fSFRz&ph7%3";
        return DriverManager.getConnection(url, user, password);
    }
}
