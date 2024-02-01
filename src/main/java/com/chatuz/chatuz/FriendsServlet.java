package com.chatuz.chatuz;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
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

public class FriendsServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("FriendsServlet doGet called"); // Dodano logowanie

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        List<Map<String, Object>> listaUzytkownikow = new ArrayList<>();
        List<Map<String, Object>> topActiveUsers = new ArrayList<>(); // List to store top active users

        HttpSession session = request.getSession();
        String userName = (String) session.getAttribute("username");
        if (userName == null || userName.isEmpty()) {
            userName = "Anonim";
        }

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://sql.freedb.tech:3306/freedb_chatuz?allowPublicKeyRetrieval=true&useSSL=false", "freedb_tecebe", "%Dn@fSFRz&ph7%3");

            // Query to retrieve distinct friends
            String queryFriends = "SELECT DISTINCT id_to AS id_to FROM messages WHERE id_from = ?";
            try (PreparedStatement preparedStatement = conn.prepareStatement(queryFriends)) {
                preparedStatement.setString(1, userName);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String user = resultSet.getString("id_to");
                        Map<String, Object> uzytkownik = new HashMap<>();
                        uzytkownik.put("id_to", user);
                        listaUzytkownikow.add(uzytkownik);
                    }
                }
            }

            // Query to count messages sent by each user and retrieve top 10 active users
            String queryTopActiveUsers = "SELECT DISTINCT id_to, COUNT(*) AS message_count FROM messages WHERE id_from = ? GROUP BY id_to ORDER BY message_count DESC LIMIT 10";
            try (PreparedStatement preparedStatement = conn.prepareStatement(queryTopActiveUsers)) {
                preparedStatement.setString(1, userName);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String user = resultSet.getString("id_to");
                        int messageCount = resultSet.getInt("message_count");
                        Map<String, Object> activeUser = new HashMap<>();
                        activeUser.put("id_to", user);
                        activeUser.put("message_count", messageCount);
                        topActiveUsers.add(activeUser);
                    }
                }
            }
        } catch (SQLException e) {
            JsonObject errorJson = Json.createObjectBuilder()
                    .add("error", "Problem z bazą danych: " + e.getMessage())
                    .build();
            response.getWriter().write(errorJson.toString());
            return;
        }

        JsonArray jsonListaUzytkownikow = Json.createArrayBuilder().build();
        for (Map<String, Object> uzytkownik : listaUzytkownikow) {
            JsonObject jsonUzytkownik = Json.createObjectBuilder(uzytkownik).build();
            jsonListaUzytkownikow = Json.createArrayBuilder(jsonListaUzytkownikow).add(jsonUzytkownik).build();
        }

        JsonArray jsonTopActiveUsers = Json.createArrayBuilder().build();
        for (Map<String, Object> activeUser : topActiveUsers) {
            JsonObject jsonActiveUser = Json.createObjectBuilder(activeUser).build();
            jsonTopActiveUsers = Json.createArrayBuilder(jsonTopActiveUsers).add(jsonActiveUser).build();
        }

        request.setAttribute("data", jsonListaUzytkownikow.toString());
        request.setAttribute("topActiveUsers", jsonTopActiveUsers.toString()); // Add top active users to request
        RequestDispatcher dispatcher = request.getRequestDispatcher("/Main.jsp"); //chat.jsp plik ze stroną chatu
        dispatcher.forward(request, response); //dokonaj przekirowania z przekazainem argumentów
    }
}
