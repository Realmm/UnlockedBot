package me.realm.discordbot;

import me.realm.discordbot.channel.Info;
import me.realm.discordbot.channel.Ticket;
import me.realm.discordbot.listeners.DeleteChannelEvent;
import me.realm.discordbot.listeners.MessageEvent;
import me.realm.discordbot.listeners.ServerJoinEvent;
import me.realm.discordbot.listeners.ServerLeaveEvent;
import me.realm.discordbot.wrapper.Util;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.*;

import java.util.stream.Stream;


public class UnlockedBot {

    private static JDA jda;

    public static void main(String[] args) {
        setupJDA();
        registerListeners();
        customiseBot();
        registerTickets();
    }

    private static void registerListeners() {
        Stream.of(
                new MessageEvent(),
                new DeleteChannelEvent(),
                new ServerLeaveEvent(),
                new ServerJoinEvent()
        ).forEach(getJDA()::addEventListener);
    }

    private static void setupJDA() {
        JDABuilder builder = new JDABuilder(AccountType.BOT);
        builder.setToken("NDMxNDcwNjM1NTYzMzUyMDc2.DafOVg.XS8c61cF9DvViAko_bNe0eOv2AE");
        builder.setAutoReconnect(true);
        builder.setStatus(OnlineStatus.ONLINE);

        try {
            jda = builder.buildBlocking();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void customiseBot() {
        getJDA().getPresence().setGame(Game.of(Game.GameType.DEFAULT, Info.GAME));
    }

    private static void registerTickets() {
        Guild guild = UnlockedBot.getJDA().getGuildById(Info.GUILD_ID);
        for (TextChannel textChannel : guild.getTextChannels()) {
            String name = textChannel.getName();
            String[] nameArray = name.split("-");
            if (nameArray.length < 3) continue;

            StringBuilder userNameStringBuilder = new StringBuilder();
            for (int i = 1; i < nameArray.length - 1; i++) {
                userNameStringBuilder.append(nameArray[i]).append("-");
            }
            String userNameBuilt = userNameStringBuilder.toString();

            String userName = userNameBuilt.substring(0, userNameBuilt.length() - 1);
            User user = null;

            for (Member member : UnlockedBot.getJDA().getGuildById(Info.GUILD_ID).getMembers()) {
                User userNew = member.getUser();
                if (userNew.getName().equalsIgnoreCase(userName)) user = userNew;
            }

            if (user == null) continue;

            String idString = nameArray[nameArray.length - 1];
            int id = idString.matches("\\d+") ? Integer.parseInt(idString) : 0;

            Util.addTicket(new Ticket(user, textChannel, id));

        }
    }

    public static JDA getJDA() {
        return jda;
    }

}
