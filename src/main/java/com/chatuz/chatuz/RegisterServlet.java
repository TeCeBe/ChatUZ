package com.chatuz.chatuz;

import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Validate the data (you should add more validation and error handling)
        if (username != null && password != null && !username.isEmpty() && !password.isEmpty()) {
            // Store the user information in the database (you need to implement this part)
            // For simplicity, we'll just print the data for now
            System.out.println("Registered user: " + username);

            // Redirect to login page
            response.sendRedirect("login.jsp");
        } else {
            // Handle invalid data
            response.sendRedirect("register.jsp");
        }
    }
}
