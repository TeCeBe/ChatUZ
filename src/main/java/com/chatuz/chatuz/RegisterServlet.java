package com.chatuz.chatuz;

import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.sql.*;

public class RegisterServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/baza", "root", "");

            ps = con.prepareStatement("SELECT username FROM users WHERE username = ?");
            ps.setString(1, username);
            rs = ps.executeQuery();

            if (rs.next()) {
                // Username is taken, redirect to register.jsp with an error parameter
                response.sendRedirect("register.jsp?error=username_taken");
            } else {
                ps = con.prepareStatement("INSERT INTO users (username, password) VALUES (?, ?)");
                ps.setString(1, username);
                ps.setString(2, password);
                int i = ps.executeUpdate();

                if (i > 0) {
                    // Redirect to login page after successful registration
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