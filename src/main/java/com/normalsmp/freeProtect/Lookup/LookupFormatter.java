package com.normalsmp.freeProtect.Lookup;

import com.normalsmp.freeProtect.Util.TimeUtil;
import net.md_5.bungee.api.chat.*;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class LookupFormatter {

    public static List<BaseComponent[]> formatPlayerBlockBreak(Player viewer,
                                                               String entityName,
                                                               String entityUUID,
                                                               String blockType,
                                                               int x, int y, int z,
                                                               String worldName,
                                                               long timestamp) {
        String timeAgo = TimeUtil.formatTimeAgo(timestamp);
        String fullDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date(timestamp));

        TextComponent headerPrefix = new TextComponent("§7" + timeAgo + " §7ago §c- §b");
        headerPrefix.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                new ComponentBuilder("§eExact Time: §f" + fullDate).create()));

        TextComponent entityComp = new TextComponent(entityName);
        entityComp.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                new ComponentBuilder("§eUUID: §f" + entityUUID).create()));

        TextComponent headerSuffix = new TextComponent(" §7broke §b" + blockType.toLowerCase() + ".");

        TextComponent header = new TextComponent();
        header.addExtra(headerPrefix);
        header.addExtra(entityComp);
        header.addExtra(headerSuffix);
        BaseComponent[] headerComp = new ComponentBuilder(header).create();

        String locText = "§8(x" + x + "/y" + y + "/z" + z + "/" + worldName + ")";
        TextComponent locLine = new TextComponent("   " + locText);
        locLine.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                new ComponentBuilder("§aClick to teleport").create()));
        locLine.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,
                "/fp tplog " + worldName + " " + x + " " + y + " " + z));
        BaseComponent[] locComp = new ComponentBuilder(locLine).create();

        return Arrays.asList(headerComp, locComp);
    }

    public static List<BaseComponent[]> formatPlayerBlockPlace(Player viewer,
                                                               String entityName,
                                                               String entityUUID,
                                                               String blockType,
                                                               int x, int y, int z,
                                                               String worldName,
                                                               long timestamp) {
        String timeAgo = TimeUtil.formatTimeAgo(timestamp);
        String fullDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date(timestamp));

        TextComponent headerPrefix = new TextComponent("§7" + timeAgo + " §7ago §a+ §b");
        headerPrefix.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                new ComponentBuilder("§eExact Time: §f" + fullDate).create()));

        TextComponent entityComp = new TextComponent(entityName);
        entityComp.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                new ComponentBuilder("§eUUID: §f" + entityUUID).create()));

        TextComponent headerSuffix = new TextComponent(" §7placed §b" + blockType.toLowerCase() + ".");

        TextComponent header = new TextComponent();
        header.addExtra(headerPrefix);
        header.addExtra(entityComp);
        header.addExtra(headerSuffix);
        BaseComponent[] headerComp = new ComponentBuilder(header).create();

        String locText = "§8(x" + x + "/y" + y + "/z" + z + "/" + worldName + ")";
        TextComponent locLine = new TextComponent("   " + locText);
        locLine.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                new ComponentBuilder("§aClick to teleport").create()));
        locLine.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,
                "/fp tplog " + worldName + " " + x + " " + y + " " + z));
        BaseComponent[] locComp = new ComponentBuilder(locLine).create();

        return Arrays.asList(headerComp, locComp);
    }
}
