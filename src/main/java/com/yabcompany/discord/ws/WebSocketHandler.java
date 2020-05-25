package com.yabcompany.discord.ws;

import com.yabcompany.discord.model.ClientMessage;
import com.yabcompany.discord.util.MainCommandParser;
import net.dv8tion.jda.api.entities.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class WebSocketHandler extends AbstractWebSocketHandler {

    private MainCommandParser mainCommandParser;


    public WebSocketHandler(MainCommandParser mainCommandParser){
        this.mainCommandParser = mainCommandParser;

    }
    public static Set<WebSocketSession> webSocketSessionSet = Collections.synchronizedSet(new HashSet<>());

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        System.out.println("New Text Message Received" + message);
        webSocketSessionSet.add(session);
        String[] strings = message.getPayload().split("#");
        ClientMessage clientMessage = ClientMessage.builder()
                .username(strings[0])
                .message(strings[1])
                .build();
        mainCommandParser.parse(clientMessage);

//        session.sendMessage(message);

    }
}
