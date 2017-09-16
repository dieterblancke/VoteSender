package com.dbsoftwares.votesender;

/*
 * Created by DBSoftwares on 14 september 2017
 * Developer: Dieter Blancke
 * Project: VoteSender
 */

import com.dbsoftwares.votesender.commands.VoteSendCommand;
import com.dbsoftwares.votesender.processor.VoteProcessor;
import com.dbsoftwares.votesender.server.VoteServer;
import com.dbsoftwares.votesender.utils.CommandUtilities;
import com.dbsoftwares.votesender.utils.Utilities;
import com.google.common.collect.Lists;
import com.vexsoftware.votifier.model.VotifierEvent;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class VoteSender extends JavaPlugin {

    @Getter private static VoteSender instance;
    @Getter private Boolean debug;
    @Getter private List<VoteServer> servers = Lists.newArrayList();

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        CommandUtilities.forceRegister(new VoteSendCommand());

        reload();

        Bukkit.getPluginManager().registerEvents(new Listener() {

            @EventHandler
            public void onVote(VotifierEvent event) {
                new VoteProcessor(event.getVote());
            }

        }, this);
    }

    public void reload() {
        servers.clear();

        debug = getConfig().getBoolean("debug");

        ConfigurationSection scs = getConfig().getConfigurationSection("Servers");
        for (String server : scs.getKeys(false)) {
            ConfigurationSection cs = scs.getConfigurationSection(server);

            servers.add(new VoteServer(server.toLowerCase(), Utilities.getPublicKey(cs.getString("Key")), cs.getString("IP"), cs.getInt("Port"), cs.getString("Service")));
        }
    }
}