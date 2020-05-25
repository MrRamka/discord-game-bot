package com.yabcompany.discord.bot;

import com.yabcompany.discord.model.ClientMessage;
import com.yabcompany.discord.util.MainCommandParser;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;

@Component
public class GamingBotListener extends ListenerAdapter {

    @Autowired
    private MainCommandParser mainCommandParser;

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        Message message = event.getMessage();

        MessageChannel messageChannel = message.getChannel();
        ClientMessage clientMessage = ClientMessage.builder()
                .message(message.getContentRaw())
                .username(message.getAuthor().getName())
                .disMessage(message)
                .build();
        mainCommandParser.parse(clientMessage);

//        messageChannel.sendMessage("Hi! " + message.getAuthor().getName())
//                .queue();

    }

}
