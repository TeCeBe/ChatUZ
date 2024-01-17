package com.chatuz.chatuz;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static jakarta.servlet.SessionTrackingMode.URL;

@WebServlet("/pobierzUzytkownikow")
public class FriendsServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Map<String, Object>> listaUzytkownikow = new ArrayList<>();

        //uzyskaj sesje
        HttpSession session = request.getSession();
        String userName = (String) session.getAttribute("username");
        if (userName == null || userName.isEmpty()) {
            userName = "Anonim";
        }

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://sql.freedb.tech:3306/freedb_chatuz?useSSL=false", "freedb_tecebe", "%Dn@fSFRz&ph7%3");
            String query = "SELECT id_to FROM messages WHERE id_from =" + userName;
            try (PreparedStatement preparedStatement = conn.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    String user = resultSet.getString("id_to");

                    Map<String, Object> uzytkownik = new HashMap<>();
                    uzytkownik.put("id_to", user);

                    listaUzytkownikow.add(uzytkownik);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.setAttribute("listaUzytkownikow", listaUzytkownikow);
        request.getRequestDispatcher("/Main.jsp").forward(request, response);
    }
}
