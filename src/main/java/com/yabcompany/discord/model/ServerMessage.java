package com.yabcompany.discord.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import net.dv8tion.jda.api.entities.Message;
import org.springframework.web.socket.WebSocketSession;

import java.awt.*;

@Data
@AllArgsConstructor
@Builder
public class ServerMessage {

    private ClientMessage message;

    private String author;

    private String title;

    private String description;

    private Color color;

    private String footer;

    private Message disMessage;

}
