package me.realm.discordbot.commands;

import me.realm.discordbot.UnlockedBot;
import me.realm.discordbot.channel.Info;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.*;

public class RemoveCommand {

    private TextChannel textChannel;
    private Message message;
    private User sender;

    public RemoveCommand(User sender, TextChannel textChannel, Message message) {
        this.textChannel = textChannel;
        this.message = message;
        this.sender = sender;

        removeFromChannel();
    }

    private void removeFromChannel() {
        Member member = UnlockedBot.getJDA().getGuildById(Info.GUILD_ID).getMember(sender);

        boolean canTypeToRemove = false;

        for (Role role : member.getRoles()) {
            if (!role.getName().equalsIgnoreCase(Info.MANAGER_STRING) && !role.getName().equalsIgnoreCase(Info.ADMIN_STRING)) continue;
            canTypeToRemove = true;
            break;
        }

        if (!canTypeToRemove) return;

        String idString = message.getContentRaw().split(" ").length == 2 ? message.getContentRaw().split(" ")[1] : "";
        long id = idString.matches("\\d+") ? Long.parseLong(idString) : 0;

        if (id == 0) return;

        User user = UnlockedBot.getJDA().getUserById(id);
        Member memberUser = UnlockedBot.getJDA().getGuildById(Info.GUILD_ID).getMember(user);

        for (PermissionOverride permissionOverride : textChannel.getMemberPermissionOverrides()) {
            Member mem = permissionOverride.getMember();
            if (mem.equals(memberUser) && !mem.getRoles().stream().map(Role::getName).anyMatch(s -> s.equalsIgnoreCase(Info.MANAGER_STRING))) continue;
            textChannel.putPermissionOverride(memberUser).setDeny(Permission.MESSAGE_READ).queue();
        }

    }

}
