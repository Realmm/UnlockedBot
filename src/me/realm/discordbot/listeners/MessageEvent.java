package me.realm.discordbot.listeners;

import me.realm.discordbot.channel.Info;
import me.realm.discordbot.commands.AddCommand;
import me.realm.discordbot.commands.CloseCommand;
import me.realm.discordbot.commands.NewCommand;
import me.realm.discordbot.commands.RemoveCommand;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.Arrays;
import java.util.List;

public class MessageEvent extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        if (!e.isFromType(ChannelType.TEXT)) return;
        if (e.getGuild().getIdLong() != Info.GUILD_ID) return;
        String cmd = e.getMessage().getContentRaw();
        String prefix = "-";

        List<String> commandsToWriteInChannel = Arrays.asList("new");

        if (!e.getAuthor().isBot() && e.getTextChannel().getName().equalsIgnoreCase(Info.TICKET_CHANNEL_NAME)) {
            if (!cmd.startsWith(prefix)) {
                e.getMessage().delete().queue();
            } else {
                String command = cmd.split(prefix)[1].toLowerCase().trim();
                if (!commandsToWriteInChannel.contains(command)) {
                    e.getMessage().delete().queue();
                    return;
                }
            }
        }

        if (cmd.startsWith(prefix)) {
            String command = cmd.split(prefix)[1].toLowerCase().trim();

            String singleCommand = command.split(" ")[0];

            if (!e.getTextChannel().getName().equalsIgnoreCase(Info.TICKET_CHANNEL_NAME) && commandsToWriteInChannel.contains(command)) return;

            switch (singleCommand) {
                case "new":
                    new NewCommand(e.getTextChannel(), e.getAuthor());
                    break;
                case "close":
                    new CloseCommand(e.getTextChannel());
                    break;
                case "add":
                    new AddCommand(e.getAuthor(), e.getTextChannel(), e.getMessage());
                    break;
                case "remove":
                    new RemoveCommand(e.getAuthor(), e.getTextChannel(), e.getMessage());
                    break;
            }
        }

    }

}
