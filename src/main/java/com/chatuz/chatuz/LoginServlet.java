package com.chatuz.chatuz;

import java.io.*;
import jakarta.servlet.http.*;
import java.sql.*;

public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (isValidLogin(username, password)) {
            // przekieruj do czatu po zalogowaniu poprawnie
            response.sendRedirect("Welcome.jsp");
        } else {
            // przekieruj do loginu z parametrem zły login
            response.sendRedirect("login.jsp?error=invalid_login");
        }
    }

    private boolean isValidLogin(String username, String password) {
        try (Connection conn = getConnection()) {
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            try (PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setString(1, username);
                ps.setString(2, password);
                try (ResultSet rs = ps.executeQuery()) {
                    // If a row is returned, the login is valid
                    return rs.next();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Nieprawidlowy login
    }

    private Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/baza";
        String user = "root";
        String password = "";
        return DriverManager.getConnection(url, user, password);
    }
}