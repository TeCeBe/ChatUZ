package com.chatuz.chatuz;

import java.io.*;
import jakarta.servlet.*;

import jakarta.servlet.http.*;

public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        //TU BEDZIE TRZEBA POROWNAC DANE Z BAZA DANYCH
        if ("exampleuser".equals(username) && "examplepassword".equals(password)) {
            // JEZELI DOBRY LOGIN TO PRZEKIERUJ DO STRONY WELCOME
            response.sendRedirect(request.getContextPath() + "/welcome.jsp");
        } else {
            // ZLY LOGIN POWROT DO STRONY LOGINU
            response.sendRedirect("login.jsp");
        }
    }
}