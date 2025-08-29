package com.normalsmp.freeProtect;

import de.tr7zw.changeme.nbtapi.NBT;
import de.tr7zw.changeme.nbtapi.iface.ReadWriteNBT;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.PistonMoveReaction;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class Helper {
    public static File NBTFileFromLocation(Location loc){
        File regionNBT = new File(FreeProtect.getPlugin().getDataFolder(), loc.getWorld().getName() + "-" + loc.getWorld().getUID() + "//" + getRegionLocation(loc));
        if(regionNBT.exists()){
            return regionNBT;
        } else {
            try {
                regionNBT.createNewFile();
                // Initialize with an empty NBT compound
                ReadWriteNBT fresh = NBT.createNBTObject();
                NBT.writeFile(regionNBT, fresh);
                return regionNBT;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static List<File> AllNBTFiles() {
        File dataFolder = FreeProtect.getPlugin().getDataFolder();
        List<File> files = new java.util.ArrayList<>();

        if (!dataFolder.exists() || !dataFolder.isDirectory()) {
            return files;
        }

        // Each world is stored as <worldName>-<UUID>
        File[] worldDirs = dataFolder.listFiles(File::isDirectory);
        if (worldDirs == null) return files;

        for (File worldDir : worldDirs) {
            collectNBTFiles(worldDir, files);
        }

        return files;
    }

    private static void collectNBTFiles(File dir, List<File> out) {
        File[] children = dir.listFiles();
        if (children == null) return;

        for (File f : children) {
            if (f.isDirectory()) {
                collectNBTFiles(f, out);
            } else if (f.getName().endsWith(".mca")) {
                out.add(f);
            }
        }
    }

    public static String getRegionLocation(Location loc) {
        World world = loc.getWorld();
        int blockX = loc.getBlockX();
        int blockZ = loc.getBlockZ();

        // Convert block coordinates to chunk coordinates
        int chunkX = blockX >> 4; // 16x16 chunks
        int chunkZ = blockZ >> 4;

        // Convert chunk coords to region coords
        int regionX = chunkX >> 5; // 32 chunks per region
        int regionZ = chunkZ >> 5;

        // Path to the region folder

        // Construct region file name
        return "r." + regionX + "." + regionZ + ".mca";
    }
}
