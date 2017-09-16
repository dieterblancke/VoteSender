package com.dbsoftwares.votesender.commands;

/*
 * Created by DBSoftwares on 14 september 2017
 * Developer: Dieter Blancke
 * Project: VoteSender
 */

import com.dbsoftwares.votesender.VoteSender;
import com.dbsoftwares.votesender.processor.VoteProcessor;
import com.dbsoftwares.votesender.utils.Utilities;
import com.google.common.collect.Lists;
import com.vexsoftware.votifier.model.Vote;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Date;

public class VoteSendCommand extends Command {

    public VoteSendCommand() {
        super("votesend");
        setAliases(Lists.newArrayList("votesender", "sendvote"));
    }

    @Override
    public boolean execute(CommandSender sender, String aliase, String[] args) {
        if (!sender.hasPermission("votesend.admin")) {
            sender.sendMessage(Utilities.format("&c&lVoteSender &fYou don't have the permission to do this!"));
            return false;
        }
        if (args.length == 1) {
            if (args[0].contains("reload") || args[0].contains("rl")) {
                VoteSender.getInstance().reload();
                sender.sendMessage(Utilities.format("&c&lVoteSender &fThe config has been reloaded!"));
                return true;
            }
        } else if(args.length == 3 && args[0].contains("send")) {
            String player = args[1];
            String service = args[2];

            Vote vote = new Vote();
            vote.setUsername(player);
            vote.setServiceName(service);
            vote.setAddress("1.2.3.4");
            vote.setTimeStamp(String.valueOf(new Date().getTime()));
            new VoteProcessor(vote);

            sender.sendMessage(Utilities.format("&c&lVoteSender &fYou have sent a test vote for &c" + player + "&f!"));
            return true;
        }
        sender.sendMessage(Utilities.format("&c&lVoteSender &fHelp:"));
        sender.sendMessage(Utilities.format("&c- &f/votesend reload &7| Reloads the plugin."));
        sender.sendMessage(Utilities.format("&c- &f/votesend send (player) (service) &7| Sends a test vote."));
        sender.sendMessage(Utilities.format("&c- &f/votesend help &7| Returns this list."));
        sender.sendMessage(Utilities.format("&c- &f/votesend amount &7| Returns the amount of votes sent through."));
        return false;
    }
}