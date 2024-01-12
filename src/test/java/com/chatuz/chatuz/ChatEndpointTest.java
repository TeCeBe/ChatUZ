package com.chatuz.chatuz;

import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpointConfig;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
class ChatEndpointTest {

    @Mock
    private Session session;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    void onMessage() throws IOException, SQLException, ParseException {
        ChatEndpoint chatEndpoint = new ChatEndpoint();
        ServerEndpointConfig.Configurator configurator = new ServerEndpointConfig.Configurator();
        ServerEndpointConfig serverEndpointConfig = ServerEndpointConfig.Builder.create(ChatEndpoint.class, "/chat").configurator(configurator).build();
        chatEndpoint.onOpen(session);
        chatEndpoint.onMessage("Hello, world!", session);
        verify(session).getBasicRemote().sendText("Hello, world!");
    }

}