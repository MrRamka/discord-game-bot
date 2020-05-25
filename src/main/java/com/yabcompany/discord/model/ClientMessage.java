package com.yabcompany.discord.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import net.dv8tion.jda.api.entities.Message;
import org.springframework.web.socket.WebSocketSession;

import javax.websocket.Session;

@Data
@Getter
@AllArgsConstructor
@Builder
public class ClientMessage {

    private String username;

    private String message;

    private Message disMessage;

}
