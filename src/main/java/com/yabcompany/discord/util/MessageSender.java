package com.yabcompany.discord.util;

import com.yabcompany.discord.model.ClientMessage;
import com.yabcompany.discord.model.ServerMessage;
import com.yabcompany.discord.ws.WebSocketHandler;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.PostConstruct;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;


@Component
@Slf4j
public class MessageSender {

    private List<Color> colorList = new ArrayList<>();
    public final static Color BLUE_COLOR = new Color(108, 92, 231);
    public final static Color RED_COLOR = new Color(214, 48, 49);
    public final static Color GREEN_COLOR = new Color(85, 239, 196);
    private Set<WebSocketSession> webSocketSessionSet = WebSocketHandler.webSocketSessionSet;

    @PostConstruct
    private void init() {
        colorList.add(BLUE_COLOR);
        colorList.add(RED_COLOR);
        colorList.add(GREEN_COLOR);
    }

    private static final String BOLD = "**";

    public void sendMessage(ServerMessage message) {
        if (message.getDisMessage() != null) {

            sendDiscordMessage(message);
        }
        for (WebSocketSession ws : webSocketSessionSet) {
            sendWsMessage(message, ws);
        }
    }

    private void sendDiscordMessage(ServerMessage message) {
        MessageChannel messageChannel = message.getDisMessage().getChannel();
        MessageEmbed embedBuilder = new EmbedBuilder()
                .setTitle(message.getTitle())
                .setAuthor(message.getAuthor())
                .setFooter(message.getFooter())
                .setColor(message.getColor())
                .addField("Description:",
                        (message.getDescription()), true
                ).build();
        messageChannel.sendMessage(
                embedBuilder
        ).queue();
    }

    private String setBold(String message) {
        return BOLD + message + BOLD;
    }

    public Color getRandomColor() {
        Random random = new Random();
        return colorList.get(random.nextInt(colorList.size()));
    }

    private void sendWsMessage(ServerMessage message, WebSocketSession webSocketSession) {

        String stringBuilder = message.getAuthor() + "\n" +
                message.getDescription() + "\n" +
                message.getFooter();
        TextMessage t = new TextMessage(stringBuilder);

        try {
            webSocketSession.sendMessage(t);
        } catch (IOException e) {
            log.error("Exception: " + e.getMessage());
        }

    }


    public ServerMessage createMessage(
            ClientMessage message,
            String author,
            String description,
            String footer,
            Color color
    ) {
        return ServerMessage.builder()
                .disMessage(message.getDisMessage())
                .author(author)
                .color(color)
                .description(description)
                .footer(footer)
                .message(message)
                .build();
    }
}
