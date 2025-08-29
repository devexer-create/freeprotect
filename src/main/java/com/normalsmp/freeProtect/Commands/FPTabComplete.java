package com.normalsmp.freeProtect.Commands;

import com.normalsmp.freeProtect.Enums.ACTIONTYPE;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class FPTabComplete implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        List<String> options = new ArrayList<>();

        // First argument: subcommands
        if (args.length == 1) {
            options.add("lookup");
            options.add("help");
            options.add("tplog");
            StringUtil.copyPartialMatches(args[0], options, completions);
            return completions;
        }

        // Subcommand: lookup
        if (args[0].equalsIgnoreCase("lookup")) {
            String currentArg = args[args.length - 1]; // always check the "current" arg being typed

            if (!currentArg.contains(":")) {
                // Suggest keys when no colon yet
                options.add("radius:");
                options.add("player:");
                options.add("event:");
                options.add("time:");
                StringUtil.copyPartialMatches(currentArg, options, completions);
            } else if (currentArg.startsWith("radius:")) {
                options.add("5");
                options.add("10");
                options.add("20");
                options.add("30");

                String afterColon = currentArg.substring("radius:".length());
                List<String> numberCompletions = new ArrayList<>();
                StringUtil.copyPartialMatches(afterColon, options, numberCompletions);

                for (String nc : numberCompletions) {
                    completions.add("radius:" + nc);
                }
            } else if (currentArg.startsWith("time:")) {
                // Suggest some handy presets
                options.add("30s");
                options.add("5m");
                options.add("30m");
                options.add("1h");
                options.add("24h");

                String afterColon = currentArg.substring("time:".length());
                List<String> timeCompletions = new ArrayList<>();
                StringUtil.copyPartialMatches(afterColon, options, timeCompletions);

                for (String t : timeCompletions) {
                    completions.add("time:" + t);
                }
            } else if (currentArg.startsWith("player:")) {
                // Add online players
                Bukkit.getOnlinePlayers().forEach(op -> options.add(op.getName()));

                // Add offline players (cached by server, e.g. previously joined)
                for (var offline : Bukkit.getOfflinePlayers()) {
                    if (offline.getName() != null) {
                        options.add(offline.getName());
                    }
                }

                String afterColon = currentArg.substring("player:".length());
                List<String> playerCompletions = new ArrayList<>();
                StringUtil.copyPartialMatches(afterColon, options, playerCompletions);

                for (String name : playerCompletions) {
                    completions.add("player:" + name);
                }
            } else if (currentArg.startsWith("event:")) {
                for (ACTIONTYPE actiontype : ACTIONTYPE.values()) {
                    options.add(String.valueOf(actiontype));
                }

                String afterColon = currentArg.substring("event:".length());
                List<String> eventCompletions = new ArrayList<>();
                StringUtil.copyPartialMatches(afterColon, options, eventCompletions);

                //Not all implemented, only these for now
                //for (String p : eventCompletions) {
                //    completions.add("event:" + p);
                //}
                completions.add("event:" + String.valueOf(ACTIONTYPE.PlayerBlockBreak));
                completions.add("event:" + String.valueOf(ACTIONTYPE.PlayerBlockPlace));
            }
        }

        // Subcommand: tplog
        if (args[0].equalsIgnoreCase("tplog")) {
            if (args.length == 2) {
                // suggest worlds
                for (World world : Bukkit.getWorlds()) {
                    options.add(world.getName());
                }
                StringUtil.copyPartialMatches(args[1], options, completions);
            } else if (args.length == 3 || args.length == 4 || args.length == 5) {
                // suggest coordinate placeholders
                options.add("~"); // relative coords
                options.add("0");
                options.add("100");
                options.add("200");
                StringUtil.copyPartialMatches(args[args.length - 1], options, completions);
            }
            return completions;
        }

        return completions;
    }
}
