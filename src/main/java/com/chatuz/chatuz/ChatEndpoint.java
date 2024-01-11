package com.chatuz.chatuz;

import jakarta.websocket.*;
import jakarta.websocket.server.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/*Endpoint służy do obsługi WebSocketa oraz
zapisywania wiadomości do bazy danych przy każdej przychodzącej wiadomości.
Nieskonfigurowanie go będzie skutkować niedziałaniem przesyłania na bieżąco i zapisu do bazy.
Odczyt z bazy działa bez niego.
 */

@ServerEndpoint("/chat")
public class ChatEndpoint {
    Connection conn = null;
    PreparedStatement ps = null;

    //sterownik do bazy
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError(e);
        }
    }
    @OnOpen
    public void onOpen(Session session) {
        //nieużywane
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        //nieużywane
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        //nieużywane
        System.out.println("Error in session: " + session.getId());
        throwable.printStackTrace();
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException, SQLException, ParseException {
        for (Session s : session.getOpenSessions()) {
            s.getBasicRemote().sendText(message);
        }
        //deklaracja parsera JSON i jego obsługa
        JsonReader jsonReader = Json.createReader(new StringReader(message));
        JsonObject jsonObject = jsonReader.readObject();
        jsonReader.close();
        //wyciąganie danych z JSONa
        String id_from = jsonObject.getString("id_from"); //id od kogo?
        String id_to = jsonObject.getString("id_to"); //id dla kogo
        String date = jsonObject.getString("timestamp"); //data wiadomosci
        String messagej = jsonObject.getString("content"); //jej zawartość
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"); //format daty
        inputFormat.setTimeZone(TimeZone.getTimeZone("UTC")); //format daty
        Date datep = inputFormat.parse(date); //parsowanie daty
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //nowy format daty
        String mysqlDateString = outputFormat.format(datep); //cd
        //zapisanie wiadomości do bazy
        try (Connection conn = getConnection() ){
            ps = conn.prepareStatement("INSERT INTO messages (id_from, id_to, date, message) VALUES (?, ?, ?, ?);"); //przygotowanie zapytania
            //uzupełnienie zmiennymi
            ps.setString(1, id_from);
            ps.setString(2, id_to);
            ps.setString(3, mysqlDateString);
            ps.setString(4, messagej);
            //wykonanie zapytania
            int i = ps.executeUpdate();
        } finally {
            //nieużywane
        }
    }

    private Connection getConnection() throws SQLException {
        //łączenie się z bazą danych
        String url = "jdbc:mysql://sql.freedb.tech:3306/freedb_chatuz?useSSL=false";
        String user = "freedb_tecebe";
        String password = "%Dn@fSFRz&ph7%3";
        return DriverManager.getConnection(url, user, password);
    }
}
