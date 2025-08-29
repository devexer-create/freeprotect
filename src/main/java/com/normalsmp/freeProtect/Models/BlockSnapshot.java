package com.normalsmp.freeProtect.Models;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class BlockSnapshot {
    public Location location;
    public Material material;

    public BlockSnapshot(Block block){
        this.location = block.getLocation();
        this.material = block.getType();
    }
}
