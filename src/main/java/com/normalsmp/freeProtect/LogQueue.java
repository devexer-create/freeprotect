package com.normalsmp.freeProtect;

import com.normalsmp.freeProtect.Enums.ACTIONTYPE;
import com.normalsmp.freeProtect.Models.ActionLog;
import com.normalsmp.freeProtect.Models.BlockSnapshot;
import de.tr7zw.changeme.nbtapi.NBT;
import de.tr7zw.changeme.nbtapi.iface.ReadWriteNBT;
import de.tr7zw.changeme.nbtapi.iface.ReadWriteNBTCompoundList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.ReentrantLock;

public class LogQueue {

    private static final ConcurrentLinkedQueue<LogEntry> queue = new ConcurrentLinkedQueue<>();
    private static final ReentrantLock lock = new ReentrantLock(); // 🔒 ensure only one flush at a time
    private static JavaPlugin plugin;

    public static void init(JavaPlugin pl) {
        plugin = pl;

        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, LogQueue::flush, 600L, 600L);
    }

    public static void enqueue(ActionLog actionLog) {
        queue.add(new LogEntry(actionLog, Instant.now().toEpochMilli()));
    }

    public static void flush() {
        if (!lock.tryLock()) {
            // another flush is already running, skip this tick
            return;
        }

        try {
            // Group entries by their file
            Map<File, List<LogEntry>> grouped = new HashMap<>();
            LogEntry entry;
            while ((entry = queue.poll()) != null) {
                File worldNBT = entry.actionLog.worldNBT;
                grouped.computeIfAbsent(worldNBT, f -> new ArrayList<>()).add(entry);
            }

            // Now process one file at a time
            for (Map.Entry<File, List<LogEntry>> fileEntry : grouped.entrySet()) {
                File worldNBT = fileEntry.getKey();
                List<LogEntry> entries = fileEntry.getValue();

                try {
                    ReadWriteNBT nbt = NBT.readFile(worldNBT);
                    for (LogEntry e : entries) {
                        switch(e.actionLog.actiontype){

                            case ACTIONTYPE.PlayerBlockBreak -> {
                                Entity entity = e.actionLog.entity;
                                BlockSnapshot block = e.actionLog.blockSnapshot;

                                ReadWriteNBTCompoundList clist = nbt.getCompoundList(String.valueOf(ACTIONTYPE.PlayerBlockBreak));
                                ReadWriteNBT record = clist.addCompound();
                                record.setString("entityUUID", entity.getUniqueId().toString());
                                record.setString("entityName", entity.getName());
                                record.setString("blockType", block.material.name());
                                record.setInteger("x", (int) block.location.getX());
                                record.setInteger("y", (int) block.location.getY());
                                record.setInteger("z", (int) block.location.getZ());
                                record.setString("worldUID", String.valueOf(block.location.getWorld().getUID()));
                                record.setString("worldName", block.location.getWorld().getName());
                                record.setLong("timestamp", e.timestamp);
                            }

                            case ACTIONTYPE.PlayerBlockPlace -> {
                                Entity entity = e.actionLog.entity;
                                BlockSnapshot block = e.actionLog.blockSnapshot;

                                ReadWriteNBTCompoundList clist = nbt.getCompoundList(String.valueOf(ACTIONTYPE.PlayerBlockPlace));
                                ReadWriteNBT record = clist.addCompound();
                                record.setString("entityUUID", entity.getUniqueId().toString());
                                record.setString("entityName", entity.getName());
                                record.setString("blockType", block.material.name());
                                record.setInteger("x", (int) block.location.getX());
                                record.setInteger("y", (int) block.location.getY());
                                record.setInteger("z", (int) block.location.getZ());
                                record.setString("worldUID", String.valueOf(block.location.getWorld().getUID()));
                                record.setString("worldName", block.location.getWorld().getName());
                                record.setLong("timestamp", e.timestamp);
                            }

                            case ACTIONTYPE.PlayerBucketFill -> {
                                Entity entity = e.actionLog.entity;
                                BlockSnapshot block = e.actionLog.blockSnapshot;

                                ReadWriteNBTCompoundList clist = nbt.getCompoundList(String.valueOf(ACTIONTYPE.PlayerBucketFill));
                                ReadWriteNBT record = clist.addCompound();
                                record.setString("entityUUID", entity.getUniqueId().toString());
                                record.setString("entityName", entity.getName());
                                record.setString("blockType", block.material.name());
                                record.setInteger("x", (int) block.location.getX());
                                record.setInteger("y", (int) block.location.getY());
                                record.setInteger("z", (int) block.location.getZ());
                                record.setString("worldUID", String.valueOf(block.location.getWorld().getUID()));
                                record.setString("worldName", block.location.getWorld().getName());
                                record.setLong("timestamp", e.timestamp);
                            }

                            case ACTIONTYPE.PlayerBucketEmpty -> {
                                Entity entity = e.actionLog.entity;
                                BlockSnapshot block = e.actionLog.blockSnapshot;

                                ReadWriteNBTCompoundList clist = nbt.getCompoundList(String.valueOf(ACTIONTYPE.PlayerBucketEmpty));
                                ReadWriteNBT record = clist.addCompound();
                                record.setString("entityUUID", entity.getUniqueId().toString());
                                record.setString("entityName", entity.getName());
                                record.setString("blockType", block.material.name());
                                record.setInteger("x", (int) block.location.getX());
                                record.setInteger("y", (int) block.location.getY());
                                record.setInteger("z", (int) block.location.getZ());
                                record.setString("worldUID", String.valueOf(block.location.getWorld().getUID()));
                                record.setString("worldName", block.location.getWorld().getName());
                                record.setLong("timestamp", e.timestamp);
                            }

                            case ACTIONTYPE.EntityChangeBlock -> {
                                Entity entity = e.actionLog.entity;
                                BlockSnapshot block = e.actionLog.blockSnapshot;

                                ReadWriteNBTCompoundList clist = nbt.getCompoundList(String.valueOf(ACTIONTYPE.EntityChangeBlock));
                                ReadWriteNBT record = clist.addCompound();
                                record.setString("entityUUID", entity.getUniqueId().toString());
                                record.setString("entityName", entity.getName());
                                record.setString("blockType", block.material.name());
                                record.setInteger("x", (int) block.location.getX());
                                record.setInteger("y", (int) block.location.getY());
                                record.setInteger("z", (int) block.location.getZ());
                                record.setString("worldUID", String.valueOf(block.location.getWorld().getUID()));
                                record.setString("worldName", block.location.getWorld().getName());
                                record.setLong("timestamp", e.timestamp);
                            }

                            case ACTIONTYPE.EntityExplode -> {
                                Entity entity = e.actionLog.entity;
                                BlockSnapshot block = e.actionLog.blockSnapshot;

                                ReadWriteNBTCompoundList clist = nbt.getCompoundList(String.valueOf(ACTIONTYPE.EntityExplode));
                                ReadWriteNBT record = clist.addCompound();
                                record.setString("entityUUID", entity.getUniqueId().toString());
                                record.setString("entityName", entity.getName());
                                record.setString("blockType", block.material.name());
                                record.setInteger("x", (int) block.location.getX());
                                record.setInteger("y", (int) block.location.getY());
                                record.setInteger("z", (int) block.location.getZ());
                                record.setString("worldUID", String.valueOf(block.location.getWorld().getUID()));
                                record.setString("worldName", block.location.getWorld().getName());
                                record.setLong("timestamp", e.timestamp);
                            }

                            case ACTIONTYPE.BlockExplode -> {
                                BlockSnapshot causeBlock = e.actionLog.blockSnapshot2;
                                BlockSnapshot block = e.actionLog.blockSnapshot;

                                ReadWriteNBTCompoundList clist = nbt.getCompoundList(String.valueOf(ACTIONTYPE.BlockExplode));
                                ReadWriteNBT record = clist.addCompound();
                                record.setString("causeBlockName", causeBlock.material.name());
                                record.setString("blockType", block.material.name());
                                record.setInteger("x", (int) block.location.getX());
                                record.setInteger("y", (int) block.location.getY());
                                record.setInteger("z", (int) block.location.getZ());
                                record.setString("worldUID", String.valueOf(block.location.getWorld().getUID()));
                                record.setString("worldName", block.location.getWorld().getName());
                                record.setLong("timestamp", e.timestamp);
                            }


                        }
                    }

                    // Write once per file
                    NBT.writeFile(worldNBT, nbt);

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            lock.unlock(); // ✅ always release lock
        }
    }

    // Record holder
    private record LogEntry(ActionLog actionLog, long timestamp) {}
}
