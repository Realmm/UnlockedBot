package me.realm.discordbot.commands;

import me.realm.discordbot.UnlockedBot;
import me.realm.discordbot.channel.Info;
import me.realm.discordbot.channel.Ticket;
import me.realm.discordbot.wrapper.Bot;
import me.realm.discordbot.wrapper.Util;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.managers.ChannelManager;
import net.dv8tion.jda.core.managers.GuildController;
import net.dv8tion.jda.core.managers.GuildManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class NewCommand {

    public NewCommand(TextChannel channel, User user) {
        if (Util.isTicket(user)) {
            EmbedBuilder eb = new EmbedBuilder();
            eb.setColor(Color.RED);
            eb.setTitle("Ticket request already created!");
            eb.setDescription("By: " + user.getName() + "\n\nType '-close' inside ticket to delete.");

            Bot.sendMessage(channel, eb.build());
            return;
        }

        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(Color.GREEN);
        eb.setTitle("Ticket request created!");
        eb.setDescription("By: " + user.getName());

        Bot.sendMessage(channel, eb.build());

        createChannel(user);
    }

    private void createChannel(User creator) {
        GuildController guildController = creator.getJDA().getGuildById(Info.GUILD_ID).getController();
        int id = getNewTicketId(creator);
        guildController.createTextChannel("ticket-" + creator.getName() + "-" + id).queue(channel -> {
                    ChannelManager channelManager = channel.getManager();
                    GuildManager guildManager = channelManager.getGuild().getManager();
                    List<Member> members = guildManager.getGuild().getMembers();
                    List<Member> admins = new ArrayList<>();

                    for (Member member : members) {
                        for (Role role : member.getRoles()) {
                            if (!role.getName().equalsIgnoreCase(Info.ADMIN_STRING)) continue;
                            admins.add(member);
                        }
                    }

                    for (Member member : members) {
                        if (member.getUser().equals(creator)) continue;
                        channel.createPermissionOverride(member).setDeny(Permission.MESSAGE_READ).queue();
                    }

                    for (Member admin : admins) {
                        if (admin.getUser().equals(creator)) continue;
                        channel.createPermissionOverride(admin).setAllow(Permission.MESSAGE_READ).queue();
                    }

                    EmbedBuilder eb = new EmbedBuilder();
                    eb.setTitle("New Ticket! User: " + creator.getName());
                    eb.setColor(Color.CYAN);
                    eb.setDescription("Please enter your full request here! \n" +
                            "Make sure to let us know whether you are looking for a quote/timeframe,\n" +
                            "or have a budget in mind, and we will work around you!\n\n" +
                            "A support representative will be with you as soon as possible!");

                    Bot.sendMessage((TextChannel) channel, eb.build());

                    Util.addTicket(new Ticket(creator, (TextChannel) channel, id));

                }
        );


    }

    private int getNewTicketId(User user) {
        Set<Integer> ids = new HashSet<>();

        Guild guild = UnlockedBot.getJDA().getGuildById(Info.GUILD_ID);
        for (TextChannel textChannel : guild.getTextChannels()) {
            String name = textChannel.getName();
            String customerName = user.getName().replace("-", "").toLowerCase().trim();
            String stringId = name.replace(customerName, "").replace("ticket", "").replace("-", "");
            int added = stringId.matches("\\d+") ? Integer.parseInt(stringId) : 0;
            ids.add(added);
        }

        int id = 0;

        while (ids.contains(id)) {
            id = ThreadLocalRandom.current().nextInt(1, 1000);
        }

        return id;
    }

}
