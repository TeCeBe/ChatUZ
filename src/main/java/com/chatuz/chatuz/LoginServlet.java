package com.chatuz.chatuz;

import java.io.*;
import jakarta.servlet.http.*;
import java.sql.*;
import org.mindrot.jbcrypt.BCrypt;

public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (isValidLogin(username, password)) {
            HttpSession session = request.getSession();
            session.setAttribute("username", username);

            response.sendRedirect("Main.jsp");
        } else {
            response.sendRedirect("login.jsp?error=invalid_login");
        }
    }

    private boolean isValidLogin(String username, String password) {
        try (Connection conn = getConnection()) {
            String query = "SELECT password FROM users WHERE username = ?";
            try (PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setString(1, username);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
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

    private Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://sql.freedb.tech:3306/freedb_chatuz?useSSL=false";
        String user = "freedb_tecebe";
        String password = "%Dn@fSFRz&ph7%3";
        return DriverManager.getConnection(url, user, password);
    }
}
