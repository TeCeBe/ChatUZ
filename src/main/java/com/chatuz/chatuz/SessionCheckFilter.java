package com.chatuz.chatuz;

import jakarta.servlet.Filter;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/*Jest to filtr sprawdzający, czy użytkownik ma otwartą sesję
uniemożliwia to wchodzenie na stronę czatu bez bycia zalogowanym.
 Obejście tego zabezpieczenia zostało zabezpieczone brakiem pustego atrybutu "nickname" w chat.jsp
 */

public class SessionCheckFilter implements Filter {

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        //definicje
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);
        //sprawdzanie czy username jest pusty oraz czy sesja jest pusta. Jeśli nie to idzie dalej
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp"); // Niezalogowany użytkownik jest przekierowywany do strony logowania
        } else {
            chain.doFilter(req, res); // Zalogowany użytkownik jest przekierowywany do żądanej strony
        }
    }

    public void init(FilterConfig config) throws ServletException {
        // Opcjonalna metoda inicjalizacji
    }

    public void destroy() {
        // Opcjonalna metoda czyszczenia
    }
}
