package com.chatuz.chatuz;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.mockito.Mockito.*;

class LoginServletTest {

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;
    private LoginServlet servlet;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        servlet = new LoginServlet();
        when(request.getSession()).thenReturn(session);
    }

    @Test
    void doPost_ValidCredentials_RedirectsToMainPage() throws IOException {
        // Symulowanie poprawnych danych logowania
        when(request.getParameter("username")).thenReturn("validUser");
        when(request.getParameter("password")).thenReturn("validPassword");
        // Załóżmy, że isValidLogin zwraca true dla tych danych (to wymagałoby mockowania bazy danych lub metody isValidLogin)

        servlet.doPost(request, response);

        // Weryfikacja, czy użytkownik został przekierowany na stronę główną
        verify(response).sendRedirect("Main.jsp");
    }

    @Test
    void doPost_InvalidCredentials_RedirectsToLoginWithError() throws IOException {
        // Symulowanie niepoprawnych danych logowania
        when(request.getParameter("username")).thenReturn("invalidUser");
        when(request.getParameter("password")).thenReturn("invalidPassword");
        // Załóżmy, że isValidLogin zwraca false dla tych danych

        servlet.doPost(request, response);

        // Weryfikacja, czy użytkownik został przekierowany na stronę logowania z błędem
        verify(response).sendRedirect("login.jsp?error=invalid_login");
    }

}
