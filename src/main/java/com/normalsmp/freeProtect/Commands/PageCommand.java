package com.normalsmp.freeProtect.Commands;

import com.normalsmp.freeProtect.Lookup.LookupCache;
import net.md_5.bungee.api.chat.*;
import org.bukkit.entity.Player;

import java.util.List;

public class PageCommand {

    private static final int RESULTS_PER_PAGE = 5;

    public static void handle(Player p, String[] args) {
        if (args.length < 2) {
            p.sendMessage("§cUsage: /fp page <number>");
            return;
        }
        try {
            int page = Integer.parseInt(args[1]);
            show(p, page);
        } catch (NumberFormatException e) {
            p.sendMessage("§cInvalid page number!");
        }
    }

    public static void show(Player p, int page) {
        List<List<BaseComponent[]>> results = LookupCache.getResults(p.getUniqueId());
        if (results == null || results.isEmpty()) {
            p.sendMessage("§cNo lookup results found! Run /fp lookup first.");
            return;
        }

        int maxPages = (int) Math.ceil((double) results.size() / RESULTS_PER_PAGE);
        if (page < 1 || page > maxPages) {
            p.sendMessage("§cInvalid page number! (1-" + maxPages + ")");
            return;
        }

        p.sendMessage("§8----- §3FreeProtect §7| Lookup Results §8-----");

        int start = (page - 1) * RESULTS_PER_PAGE;
        int end = Math.min(start + RESULTS_PER_PAGE, results.size());

        for (int i = start; i < end; i++) {
            for (BaseComponent[] line : results.get(i)) {
                p.spigot().sendMessage(line);
            }
        }

        TextComponent footer = new TextComponent("§7Page §b" + page + "/" + maxPages + " §7(§b" + results.size() + "§7)  ");

        if (page > 1) {
            TextComponent first = new TextComponent("§a⏮ ");
            first.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/fp page 1"));
            footer.addExtra(first);

            TextComponent prev = new TextComponent("§a◀ ");
            prev.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/fp page " + (page - 1)));
            footer.addExtra(prev);
        } else {
            footer.addExtra(new TextComponent("§7⏮ ◀ "));
        }

        if (page < maxPages) {
            TextComponent next = new TextComponent("§a▶ ");
            next.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/fp page " + (page + 1)));
            footer.addExtra(next);

            TextComponent last = new TextComponent("§a⏭ ");
            last.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/fp page " + maxPages));
            footer.addExtra(last);
        } else {
            footer.addExtra(new TextComponent("§7▶ ⏭"));
        }

        p.spigot().sendMessage(footer);
    }
}
