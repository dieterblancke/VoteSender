package com.dbsoftwares.votesender.utils;

/*
 * Created by DBSoftwares on 30 augustus 2017
 * Developer: Dieter Blancke
 * Project: CMS
 * May only be used for CentrixPVP
 */

import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.SimpleCommandMap;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

public class CommandUtilities {

    public static void forceRegister(Command command) {
        try {
            Field commandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");

            commandMap.setAccessible(true);
            CommandMap map = (CommandMap) commandMap.get(Bukkit.getServer());

            Command cmd = map.getCommand(command.getName());
            if (cmd != null) {
                forceUnregister(cmd.getName(), cmd.getAliases());
            }

            map.register(command.getName(), command);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static void forceUnregister(String command, List<String> commands) {
        try {
            Field commandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            commandMap.setAccessible(true);

            SimpleCommandMap scm = (SimpleCommandMap) commandMap.get(Bukkit.getServer());

            Field field = scm.getClass().getDeclaredField("knownCommands");
            field.setAccessible(true);

            Map<String, Command> cmds = (Map<String, Command>) field.get(scm);

            if (cmds.containsKey(command)) {
                cmds.remove(command);
            }
            for (String s : commands) {
                if (cmds.containsKey(s)) {
                    cmds.remove(s);
                }
            }
            List<String> remove = Lists.newArrayList();
            for (Map.Entry<String, Command> entry : cmds.entrySet()) {
                if (entry.getValue().getAliases() == null) {
                    continue;
                }
                for (String cmd : commands) {
                    for (String s : entry.getValue().getAliases()) {
                        if (s.equalsIgnoreCase(cmd)) {
                            remove.add(entry.getKey());
                        }
                    }
                }
            }
            for (String s : remove) {
                cmds.remove(s);
            }

            field.set(scm, cmds);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}