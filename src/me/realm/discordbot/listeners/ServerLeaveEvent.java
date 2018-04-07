package me.realm.discordbot.listeners;

import me.realm.discordbot.channel.Ticket;
import me.realm.discordbot.wrapper.Util;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class ServerLeaveEvent extends ListenerAdapter {

    @Override
    public void onGuildMemberLeave(GuildMemberLeaveEvent e) {
        User user = e.getUser();
        if (!Util.isTicket(user)) return;
        Ticket ticket = Util.getTicket(user);
        ticket.getTextChannel().delete().queue();
        Util.removeTicket(ticket);
    }

}
