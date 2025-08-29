package com.normalsmp.freeProtect.Lookup;

import net.md_5.bungee.api.chat.BaseComponent;

import java.util.*;

public class LookupCache {
    private static final Map<UUID, List<List<BaseComponent[]>>> CACHE = new HashMap<>();

    public static void setResults(UUID player, List<List<BaseComponent[]>> lines) {
        CACHE.put(player, lines);
    }

    public static List<List<BaseComponent[]>> getResults(UUID player) {
        return CACHE.get(player);
    }

    public static void clear(UUID player) {
        CACHE.remove(player);
    }
}
