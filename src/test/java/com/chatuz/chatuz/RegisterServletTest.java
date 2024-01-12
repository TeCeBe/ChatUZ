package com.chatuz.chatuz;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.mockito.Mockito.*;

class RegisterServletTest {

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    private RegisterServlet servlet;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        servlet = new RegisterServlet();
    }

    @Test
    void doPost_WhenPasswordIsShort_ShouldRedirectToError() throws ServletException, IOException {
        // Ustawianie zachowania mockowanych obiektów
        when(request.getParameter("password")).thenReturn("short");

        servlet.doPost(request, response);

        // Sprawdzanie, czy wykonano przekierowanie do odpowiedniej strony
        verify(response).sendRedirect("register.jsp?error=short_password");
    }

    // Tutaj możesz dodać więcej testów dla różnych scenariuszy, np. username_taken, email_taken, itd.
}