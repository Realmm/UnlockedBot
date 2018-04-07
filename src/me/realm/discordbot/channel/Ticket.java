package me.realm.discordbot.channel;

import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;

public class Ticket {

    private final User user;
    private final int id;
    private final TextChannel textChannel;

    public Ticket(User user, TextChannel textChannel, int id) {
        this.user = user;
        this.id = id;
        this.textChannel = textChannel;
    }

    public User getUser() {
        return user;
    }

    public int getId() {
        return id;
    }

    public TextChannel getTextChannel() {
        return textChannel;
    }

}
