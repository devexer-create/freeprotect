package com.normalsmp.freeProtect.Lookup;

import com.normalsmp.freeProtect.Enums.ACTIONTYPE;
import com.normalsmp.freeProtect.Helper;
import de.tr7zw.changeme.nbtapi.NBT;
import de.tr7zw.changeme.nbtapi.iface.ReadWriteNBT;
import de.tr7zw.changeme.nbtapi.iface.ReadWriteNBTCompoundList;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class LookupService {

    /**
     * Loads region NBTs, filters by radius & event, builds formatted chat components,
     * sorts newest -> oldest, and returns a flattened list-of-entries, where each entry
     * is a list of lines (BaseComponent[]).
     */
    public static List<List<BaseComponent[]>> runLookup(Player p, ACTIONTYPE event, int radius, long timeFilter, String playerFilter) throws IOException {
        List<LookupResult> results = new ArrayList<>();
        Location playerLoc = p.getLocation();
        long now = System.currentTimeMillis();

        // Which files do we scan?
        List<File> nbtFiles;
        if (radius > 0) {
            // Just the file for the region player is standing in
            nbtFiles = Collections.singletonList(Helper.NBTFileFromLocation(playerLoc));
        } else {
            // All files from this world
            nbtFiles = Helper.AllNBTFiles();
        }

        for (File regionNBT : nbtFiles) {
            if (!regionNBT.exists()) continue;

            ReadWriteNBT nbt = NBT.readFile(regionNBT);
            ReadWriteNBTCompoundList actions = nbt.getCompoundList(String.valueOf(event));
            if (actions == null) continue;

            for (ReadWriteNBT action : actions) {
                switch (event) {
                    case PlayerBlockBreak -> {
                        String entityName = action.getString("entityName");
                        String entityUUID = action.getString("entityUUID");
                        String blockType  = action.getString("blockType");
                        int x = action.getInteger("x");
                        int y = action.getInteger("y");
                        int z = action.getInteger("z");
                        String worldName  = action.getString("worldName");
                        long timestamp    = action.getLong("timestamp");

                        // filter by player
                        if (playerFilter != null) {
                            boolean match = false;

                            // match by UUID
                            if (entityUUID != null && entityUUID.equalsIgnoreCase(playerFilter)) {
                                match = true;
                            }
                            // match by exact name
                            else if (entityName != null && entityName.equalsIgnoreCase(playerFilter)) {
                                match = true;
                            }

                            if (!match) continue;
                        }

                        if (timeFilter > 0 && (now - timestamp) > timeFilter) continue;

                        if (radius > 0) {
                            Location actionLoc = new Location(playerLoc.getWorld(), x, y, z);
                            if (playerLoc.distance(actionLoc) > radius) continue;
                        }

                        List<BaseComponent[]> lines = LookupFormatter.formatPlayerBlockBreak(
                                p, entityName, entityUUID, blockType, x, y, z, worldName, timestamp
                        );

                        results.add(new LookupResult(timestamp, lines));
                    }
                    case PlayerBlockPlace -> {
                        String entityName = action.getString("entityName");
                        String entityUUID = action.getString("entityUUID");
                        String blockType  = action.getString("blockType");
                        int x = action.getInteger("x");
                        int y = action.getInteger("y");
                        int z = action.getInteger("z");
                        String worldName  = action.getString("worldName");
                        long timestamp    = action.getLong("timestamp");

                        // filter by player
                        if (playerFilter != null) {
                            boolean match = false;

                            // match by UUID
                            if (entityUUID != null && entityUUID.equalsIgnoreCase(playerFilter)) {
                                match = true;
                            }
                            // match by exact name
                            else if (entityName != null && entityName.equalsIgnoreCase(playerFilter)) {
                                match = true;
                            }

                            if (!match) continue;
                        }

                        if (timeFilter > 0 && (now - timestamp) > timeFilter) continue;

                        if (radius > 0) {
                            Location actionLoc = new Location(playerLoc.getWorld(), x, y, z);
                            if (playerLoc.distance(actionLoc) > radius) continue;
                        }

                        List<BaseComponent[]> lines = LookupFormatter.formatPlayerBlockPlace(
                                p, entityName, entityUUID, blockType, x, y, z, worldName, timestamp
                        );

                        results.add(new LookupResult(timestamp, lines));
                    }
                    // Add other ACTIONTYPE cases here
                }
            }
        }

        // Sort newest -> oldest
        results.sort((a, b) -> Long.compare(b.timestamp, a.timestamp));

        // Flatten into PageCommand structure
        List<List<BaseComponent[]>> prepared = new ArrayList<>();
        for (LookupResult r : results) prepared.add(r.lines);

        return prepared;
    }
}
