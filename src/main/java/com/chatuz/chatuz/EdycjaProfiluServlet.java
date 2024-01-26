package com.chatuz.chatuz;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/EdycjaProfiluServlet")
public class EdycjaProfiluServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Dane do połączenia z bazą danych
    private static final String JDBC_URL = "jdbc:mysql://sql.freedb.tech:3306/freedb_chatuz?useSSL=false";
    private static final String USER = "freedb_tecebe";
    private static final String PASSWORD = "%Dn@fSFRz&ph7%3";

    // Metoda do nawiązywania połączenia z bazą danych
    private Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Błąd podczas ładowania sterownika JDBC", e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        // Odczytaj wartości z formularza
        String nowyEmail = request.getParameter("email");
        String noweHaslo = request.getParameter("haslo");
        String trybImprezy = request.getParameter("trybImprezy");

        // Pobierz identyfikator użytkownika z sesji lub z formularza (zależy od implementacji)
        String idString = request.getParameter("userId");
        int id = Integer.parseInt(idString);

        // Wykonaj operacje na bazie danych (zapisz wartości)
        Connection connection = null;
        try {
            connection = getConnection();

            // Aktualizacja danych w bazie danych
            String updateQuery = "UPDATE uzytkownicy SET email = ?, haslo = ?, tryb_imprezy = ? WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setString(1, nowyEmail);
                preparedStatement.setString(2, noweHaslo);
                preparedStatement.setString(3, trybImprezy);
                preparedStatement.setInt(4, id); // Ustaw identyfikator użytkownika

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    out.println("<html><body><h2>Profil został zaktualizowany</h2></body></html>");
                } else {
                    out.println("<html><body><h2>Błąd podczas aktualizacji profilu</h2></body></html>");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            out.println("<html><body><h2>Błąd podczas aktualizacji profilu</h2></body></html>");
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
