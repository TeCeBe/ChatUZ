package com.chatuz.chatuz;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;

public class ChatRedirect extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nick = request.getParameter("nickname");
        request.setAttribute("nick", nick);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/chat.jsp");
        dispatcher.forward(request, response);
    }
}
