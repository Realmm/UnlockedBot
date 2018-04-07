package me.realm.discordbot.wrapper;

import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Bot {

    public static void sendMessage(TextChannel channel, String message) {
        channel.sendMessage(message).queue();
    }

    public static void sendMessage(TextChannel channel, MessageEmbed message) {
        channel.sendMessage(message).queue();
    }

}
