package com.chatuz.chatuz;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Map;

@WebFilter("/login.jsp") // Filtr będzie stosowany do wszystkich żądań kierowanych do login.jsp
public class TranslationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
        // Metoda inicjalizująca filtr, tutaj nie musimy niczego konfigurować
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession();

        String language = (String) session.getAttribute("language");
        if (language == null) {
            language = "pl"; // Domyślny język, jeśli nie ustawiono innego
        }

        Map<String, String> translations = TranslationService.getTranslations(language);
        request.setAttribute("loginText", translations); // Ustawienie mapy tłumaczeń jako atrybutu żądania

        chain.doFilter(request, response); // Przekazanie sterowania dalej w łańcuchu filtrów/servletów
    }

    @Override
    public void destroy() {
        // Metoda wywoływana podczas niszczenia filtru, tutaj również nie musimy niczego konfigurować
    }
}
