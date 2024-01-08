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

@ServerEndpoint("/chat")
public class ChatEndpoint {
    Connection conn = null;
    PreparedStatement ps = null;

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

    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {

    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.out.println("Error in session: " + session.getId());
        throwable.printStackTrace();
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException, SQLException, ParseException {
        JsonReader jsonReader = Json.createReader(new StringReader(message));
        JsonObject jsonObject = jsonReader.readObject();
        jsonReader.close();
        String id_from = jsonObject.getString("id_from");
        String id_to = jsonObject.getString("id_to");
        String date = jsonObject.getString("timestamp");
        String messagej = jsonObject.getString("content");
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        inputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date datep = inputFormat.parse(date);
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String mysqlDateString = outputFormat.format(datep);
        try (Connection conn = getConnection() ){
            ps = conn.prepareStatement("INSERT INTO messages (id_from, id_to, date, message) VALUES (?, ?, ?, ?);");
            ps.setString(1, id_from);
            ps.setString(2, id_to);
            ps.setString(3, mysqlDateString);
            ps.setString(4, messagej);
            int i = ps.executeUpdate();
        } finally {

        }
    }

    private Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://sql.freedb.tech:3306/freedb_chatuz?useSSL=false";
        String user = "freedb_tecebe";
        String password = "%Dn@fSFRz&ph7%3";
        return DriverManager.getConnection(url, user, password);
    }
}
