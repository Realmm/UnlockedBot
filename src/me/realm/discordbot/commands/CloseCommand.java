package me.realm.discordbot.commands;

import me.realm.discordbot.channel.Ticket;
import me.realm.discordbot.wrapper.Util;
import net.dv8tion.jda.core.entities.TextChannel;

public class CloseCommand {

    private TextChannel channel;

    public CloseCommand(TextChannel channel) {
        this.channel = channel;
        closeChannel();
    }

    private void closeChannel() {
        if (!Util.isTicket(channel)) return;
        Ticket ticket = Util.getTicket(channel);
        ticket.getTextChannel().delete().queue();
        Util.removeTicket(ticket);
    }



}
