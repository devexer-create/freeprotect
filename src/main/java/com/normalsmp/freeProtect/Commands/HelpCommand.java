package com.normalsmp.freeProtect.Commands;

import org.bukkit.entity.Player;

public class HelpCommand {
    public static void sendHelp(Player p) {
        p.sendMessage("§8----- §3FreeProtect §7| Help §8-----");
        p.sendMessage("§b/fp lookup §7event:§fPlayerBlockBreak §7radius:§f<N>");
        p.sendMessage("§7  Example: §f/fp lookup event:PlayerBlockBreak radius:10");
        p.sendMessage("§b/fp page §f<N> §7- show a specific results page");
        p.sendMessage("§b/fp help §7- this help");
    }
}
