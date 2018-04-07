package me.realm.discordbot.commands;

import me.realm.discordbot.UnlockedBot;
import me.realm.discordbot.channel.Info;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.*;

public class AddCommand {

    private TextChannel textChannel;
    private Message message;
    private User sender;

    public AddCommand(User sender, TextChannel textChannel, Message message) {
        this.textChannel = textChannel;
        this.message = message;
        this.sender = sender;

        addToChannel();
    }

    private void addToChannel() {
        Member member = UnlockedBot.getJDA().getGuildById(Info.GUILD_ID).getMember(sender);

        boolean canTypeToAdd = false;

        for (Role role : member.getRoles()) {
            if (!role.getName().equalsIgnoreCase(Info.MANAGER_STRING) && !role.getName().equalsIgnoreCase(Info.ADMIN_STRING)) continue;
            canTypeToAdd = true;
            break;
        }

        if (!canTypeToAdd) return;

        String idString = message.getContentRaw().split(" ").length == 2 ? message.getContentRaw().split(" ")[1] : "";
        long id = idString.matches("\\d+") ? Long.parseLong(idString) : 0;

        if (id == 0) return;

        User user = UnlockedBot.getJDA().getUserById(id);
        Member memberUser = UnlockedBot.getJDA().getGuildById(Info.GUILD_ID).getMember(user);

        for (PermissionOverride permissionOverride : textChannel.getMemberPermissionOverrides()) {
            Member mem = permissionOverride.getMember();
            if (mem.equals(memberUser)) continue;
            textChannel.putPermissionOverride(memberUser).setAllow(Permission.MESSAGE_READ).queue();
        }

    }



}
