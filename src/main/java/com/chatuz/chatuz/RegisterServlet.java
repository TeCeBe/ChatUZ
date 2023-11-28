package com.chatuz.chatuz;

import java.io.*;
import jakarta.servlet.*;

import jakarta.servlet.http.*;


public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // WALIDACJA DANYCH + UROZMAICIC POZNIEJ
        if (username != null && password != null && !username.isEmpty() && !password.isEmpty()) {
            // TUTAJ BAZA DANYCH MA BYĆ
            // NARAZIE PRINTUJEMY
            System.out.println("Registered user: " + username);

            // PRZEKIERUJ DO STRONY LOGOWANIA
            response.sendRedirect("login.jsp");
        } else {
            // NIEPRAWIDLOWE DANE
            response.sendRedirect("register.jsp");
        }
    }
}
