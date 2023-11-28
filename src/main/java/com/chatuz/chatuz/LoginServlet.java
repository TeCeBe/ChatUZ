package com.chatuz.chatuz;

import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Validate the login (you should compare with the data stored in the database)
        // For simplicity, we'll use a hardcoded username and password
        if ("exampleuser".equals(username) && "examplepassword".equals(password)) {
            // Successful login, you can redirect to a welcome page
            response.sendRedirect("welcome.jsp");
        } else {
            // Invalid login, redirect back to login page
            response.sendRedirect("login.jsp");
        }
    }
}