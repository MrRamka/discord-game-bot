package com.yabcompany.discord.util;

import com.yabcompany.discord.model.ServerMessage;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@Component
public class MessageSender {

    private List<Color> colorList = new ArrayList<>();

    @PostConstruct
    private void init() {
        colorList.add(new Color(108, 92, 231));
        colorList.add(new Color(214, 48, 49));
        colorList.add(new Color(85, 239, 196));
    }

    private static final String BOLD = "**";

    public void sendMessage(ServerMessage message) {
        sendDiscordMessage(message);

    }

    private void sendDiscordMessage(ServerMessage message) {
        MessageChannel messageChannel = message.getMessage().getChannel();
        MessageEmbed embedBuilder = new EmbedBuilder()
                .setTitle(message.getTitle())
                .setAuthor(message.getAuthor())
                .setFooter(message.getFooter())
                .setColor(colorList.get(0))
                .addField("Commands",
                        setBold(message.getDescription()), true
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
}
