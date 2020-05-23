package com.yabcompany.discord.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import net.dv8tion.jda.api.entities.Message;

import java.awt.*;

@Data
@AllArgsConstructor
@Builder
public class ServerMessage {

    private Message message;

    private String author;

    private String title;

    private String description;

    private Color color;

    private String footer;

}
