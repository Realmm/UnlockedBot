package me.realm.discordbot.listeners;

import me.realm.discordbot.wrapper.Util;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.channel.text.TextChannelDeleteEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class DeleteChannelEvent extends ListenerAdapter {

    @Override
    public void onTextChannelDelete(TextChannelDeleteEvent e) {
        TextChannel channel = e.getChannel();
        if (Util.isTicket(channel)) Util.removeTicket(Util.getTicket(channel));
    }

}
