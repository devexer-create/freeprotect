package com.normalsmp.freeProtect.Commands;

import com.normalsmp.freeProtect.Enums.ACTIONTYPE;
import com.normalsmp.freeProtect.FreeProtect;
import com.normalsmp.freeProtect.LogQueue;
import com.normalsmp.freeProtect.Lookup.LookupCache;
import com.normalsmp.freeProtect.Lookup.LookupService;
import com.normalsmp.freeProtect.Util.ArgParser;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

import static com.normalsmp.freeProtect.Util.ArgParser.parseTimeString;

public class LookupCommand {

    public static void handle(Player p, String[] args) {
        Map<String, String> kv = ArgParser.parseKeyValueArgs(args);

        // radius
        int radius = 0;
        if (kv.containsKey("radius")) {
            try {
                radius = Integer.parseInt(kv.get("radius"));
            } catch (NumberFormatException e) {
                p.sendMessage("§cInvalid radius value!");
                return;
            }
        }

        // event
        ACTIONTYPE event;
        if (!kv.containsKey("event")) {
            p.sendMessage("§cMust contain an event!");
            return;
        }
        try {
            event = ACTIONTYPE.valueOf(kv.get("event"));
        } catch (IllegalArgumentException e) {
            p.sendMessage("§cInvalid event!");
            return;
        }

        // time
        long timeFilter = -1;
        if (kv.containsKey("time")) {
            try {
                timeFilter = parseTimeString(kv.get("time"));
                if (timeFilter <= 0) {
                    p.sendMessage("§cInvalid time format! Use like 30s, 5m, 2h, 1d");
                    return;
                }
            } catch (NumberFormatException e) {
                p.sendMessage("§cInvalid time value!");
                return;
            }
        }

        // player
        String playerFilter;
        if (kv.containsKey("player")) {
            playerFilter = kv.get("player");
        } else {
            playerFilter = null;
        }


        int finalRadius = radius;
        ACTIONTYPE finalEvent = event;

        long finalTimeFilter = timeFilter;
        Bukkit.getScheduler().runTaskAsynchronously(FreeProtect.getPlugin(), () -> {
            LogQueue.flush();
            List<List<net.md_5.bungee.api.chat.BaseComponent[]>> prepared;
            try {
                prepared = LookupService.runLookup(p, finalEvent, finalRadius, finalTimeFilter, playerFilter);
            } catch (Exception ex) {
                Bukkit.getScheduler().runTask(FreeProtect.getPlugin(), () ->
                        p.sendMessage("§cError reading region data: " + ex.getMessage()));
                return;
            }

            LookupCache.setResults(p.getUniqueId(), prepared);
            Bukkit.getScheduler().runTask(FreeProtect.getPlugin(), () -> PageCommand.show(p, 1));
        });
    }
}
