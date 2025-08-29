package com.normalsmp.freeProtect;

import com.normalsmp.freeProtect.Commands.FPCommand;
import com.normalsmp.freeProtect.Commands.FPTabComplete;
import com.normalsmp.freeProtect.Events.BlockChangeEvents;
import de.tr7zw.changeme.nbtapi.NBT;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;


public final class FreeProtect extends JavaPlugin {
    static FreeProtect myplugin;


    @Override
    public void onEnable() {
        myplugin = this;
        if (!NBT.preloadApi()) {
            getLogger().warning("NBT-API wasn't initialized properly, some features may not work.");
            //getPluginLoader().disablePlugin(this);
            //return;
        } else {
            getLogger().info("NBT-API Initialized");
        }

        this.getServer().getPluginManager().registerEvents(new BlockChangeEvents(), this);

        for(World world : Bukkit.getWorlds()){
            this.getDataFolder().mkdirs();
            File worldNBT = new File(this.getDataFolder(), world.getName() + "-" + world.getUID());
            if(!worldNBT.exists()){
                worldNBT.mkdirs();

                // Initialize with an empty NBT compound
                //ReadWriteNBT fresh = NBT.createNBTObject();
                //NBT.writeFile(worldNBT, fresh);
            }
        }
        LogQueue.init(this);

        getCommand("fp").setExecutor(new FPCommand());
        getCommand("fp").setTabCompleter(new FPTabComplete());
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static FreeProtect getPlugin(){
        return myplugin;
    }
}
