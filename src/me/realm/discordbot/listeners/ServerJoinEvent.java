package me.realm.discordbot.listeners;

import me.realm.discordbot.UnlockedBot;
import me.realm.discordbot.channel.Info;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.managers.GuildController;

public class ServerJoinEvent extends ListenerAdapter {

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent e) {
        Member member = e.getMember();
        if (member.getRoles().size() == 0) {
            GuildController guildController = member.getUser().getJDA().getGuildById(Info.GUILD_ID).getController();
            guildController.addRolesToMember(member, UnlockedBot.getJDA().getRolesByName(Info.CLIENT_STRING, true)).queue();
        }
    }

}
