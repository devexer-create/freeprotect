package com.normalsmp.freeProtect.Commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class FPCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) {
            sender.sendMessage("§eCannot execute from non Player");
            return true;
        }

        if (args.length == 0) {
            HelpCommand.sendHelp(p);
            return true;
        }

        String sub = args[0].toLowerCase();
        switch (sub) {
            case "lookup" -> LookupCommand.handle(p, args);
            case "page"   -> PageCommand.handle(p, args);
            case "help"   -> HelpCommand.sendHelp(p);
            case "tplog"  -> handleTpLog(p, args);
            default       -> p.sendMessage("§cUnknown subcommand. Try /fp help");
        }
        return true;
    }

    private void handleTpLog(Player p, String[] args) {
        if (args.length < 5) {
            p.sendMessage("§cUsage: /fp tplog <world> <x> <y> <z>");
            return;
        }

        String worldName = args[1];
        double x, y, z;

        try {
            x = Double.parseDouble(args[2]);
            y = Double.parseDouble(args[3]);
            z = Double.parseDouble(args[4]);
        } catch (NumberFormatException e) {
            p.sendMessage("§cCoordinates must be numbers.");
            return;
        }

        World world = Bukkit.getWorld(worldName);
        if (world == null) {
            p.sendMessage("§cWorld '" + worldName + "' not found.");
            return;
        }

        Location target = new Location(world, x + 0.5, y, z + 0.5);
        p.teleport(target);
        p.sendMessage("§aTeleported to log location in §e" + worldName + "§a.");
    }
}
