package me.realm.discordbot.wrapper;

import me.realm.discordbot.channel.Ticket;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class Util {

    private static Set<Ticket> ticketSet = new HashSet<>();

    public static void addTicket(Ticket ticket) {
        ticketSet.add(ticket);
    }

    public static void removeTicket(Ticket ticket) {
        ticketSet.remove(ticket);
    }

    public static Collection<Ticket> getTickets() {
        return new HashSet<>(ticketSet);
    }

    public static Ticket getTicket(int id) {
        for (Ticket tick : getTickets()) {
            if (tick.getId() != id) continue;
            return tick;
        }
        return null;
    }

    public static Ticket getTicket(User user) {
        for (Ticket tick : getTickets()) {
            if (!tick.getUser().equals(user)) continue;
            return tick;
        }
        return null;
    }

    public static Ticket getTicket(TextChannel channel) {
        for (Ticket tick : getTickets()) {
            if (!tick.getTextChannel().equals(channel)) continue;
            return tick;
        }
        return null;
    }

    public static boolean isTicket(int id) {
        return getTicket(id) != null;
    }

    public static boolean isTicket(User user) {
        return getTicket(user) != null;
    }

    public static boolean isTicket(TextChannel channel) {
        return getTicket(channel) != null;
    }

}
