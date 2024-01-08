package com.chatuz.chatuz;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.sql.*;
import org.mindrot.jbcrypt.BCrypt;

public class RegisterServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (password == null || password.length() < 8) {
            try {
                response.sendRedirect("register.jsp?error=short_password");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }

        // Haszowanie hasła za pomocą biblioteki BCrypt (zawarta w pom.xml)
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://sql.freedb.tech:3306/freedb_chatuz?useSSL=false", "freedb_tecebe", "%Dn@fSFRz&ph7%3");

            ps = con.prepareStatement("SELECT username FROM users WHERE username = ?");
            ps.setString(1, username);
            rs = ps.executeQuery();

            if (rs.next()) {
                response.sendRedirect("register.jsp?error=username_taken");
            } else {
                ps = con.prepareStatement("INSERT INTO users (username, password) VALUES (?, ?)");
                ps.setString(1, username);
                ps.setString(2, hashedPassword);
                int i = ps.executeUpdate();

                if (i > 0) {
                    response.sendRedirect("login.jsp");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Database access error.", e);
        } finally {
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
