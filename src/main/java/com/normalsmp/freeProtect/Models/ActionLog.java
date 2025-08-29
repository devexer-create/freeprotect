package com.normalsmp.freeProtect.Models;

import com.normalsmp.freeProtect.Enums.ACTIONTYPE;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

import java.io.File;

public class ActionLog {
    public ACTIONTYPE actiontype;
    public File worldNBT;

    public BlockSnapshot blockSnapshot;
    public BlockSnapshot blockSnapshot2;
    public Entity entity;

    public ActionLog(ACTIONTYPE actionType, File worldNBT, BlockSnapshot blockSnapshot, Entity entity){
        this.actiontype = actionType;
        this.worldNBT = worldNBT;
        this.blockSnapshot = blockSnapshot;
        this.entity = entity;
    }

    public ActionLog(ACTIONTYPE actionType, File worldNBT, BlockSnapshot blockSnapshot){
        this.actiontype = actionType;
        this.worldNBT = worldNBT;
        this.blockSnapshot = blockSnapshot;
    }

    public ActionLog(ACTIONTYPE actionType, File worldNBT, BlockSnapshot blockSnapshot, BlockSnapshot blockSnapshot2){
        this.actiontype = actionType;
        this.worldNBT = worldNBT;
        this.blockSnapshot = blockSnapshot;
        this.blockSnapshot2 = blockSnapshot2;
    }
}
