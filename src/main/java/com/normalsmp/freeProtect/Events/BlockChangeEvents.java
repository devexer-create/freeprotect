package com.normalsmp.freeProtect.Events;

import com.normalsmp.freeProtect.Enums.ACTIONTYPE;
import com.normalsmp.freeProtect.Helper;
import com.normalsmp.freeProtect.Models.ActionLog;
import com.normalsmp.freeProtect.Models.BlockSnapshot;
import com.normalsmp.freeProtect.LogQueue;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;

public class BlockChangeEvents implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player player = e.getPlayer();
        BlockSnapshot block = new BlockSnapshot(e.getBlock());
        LogQueue.enqueue(new ActionLog(ACTIONTYPE.PlayerBlockBreak, Helper.NBTFileFromLocation(block.location), block, player));
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        Player player = e.getPlayer();
        BlockSnapshot block = new BlockSnapshot(e.getBlock());
        LogQueue.enqueue(new ActionLog(ACTIONTYPE.PlayerBlockPlace, Helper.NBTFileFromLocation(block.location), block, player));
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent e) {
        for(Block eb : e.blockList()){
            BlockSnapshot block = new BlockSnapshot(eb);
            LogQueue.enqueue(new ActionLog(ACTIONTYPE.EntityExplode, Helper.NBTFileFromLocation(block.location), block, e.getEntity()));
        }
    }

    @EventHandler
    public void onPlayerBucketFill(PlayerBucketFillEvent e) {
        BlockSnapshot block = new BlockSnapshot(e.getBlock());
        LogQueue.enqueue(new ActionLog(ACTIONTYPE.PlayerBucketFill, Helper.NBTFileFromLocation(block.location), block, e.getPlayer()));
    }

    @EventHandler
    public void onPlayerBucketEmpty(PlayerBucketEmptyEvent e) {
        BlockSnapshot block = new BlockSnapshot(e.getBlock());
        LogQueue.enqueue(new ActionLog(ACTIONTYPE.PlayerBucketEmpty, Helper.NBTFileFromLocation(block.location), block, e.getPlayer()));
    }

    @EventHandler
    public void onBlockExplode(BlockExplodeEvent e) {
        for(Block eb : e.blockList()){
            BlockSnapshot block = new BlockSnapshot(eb);
            BlockSnapshot causeBlock = new BlockSnapshot(e.getExplodedBlockState().getBlock());
            LogQueue.enqueue(new ActionLog(ACTIONTYPE.BlockExplode, Helper.NBTFileFromLocation(block.location), block, causeBlock));
        }
    }

    @EventHandler
    public void onEntityChangeBlock(EntityChangeBlockEvent e){
        BlockSnapshot block = new BlockSnapshot(e.getBlock());
        LogQueue.enqueue(new ActionLog(ACTIONTYPE.EntityChangeBlock, Helper.NBTFileFromLocation(block.location), block, e.getEntity()));
    }

    @EventHandler
    public void onBlockBurn(BlockBurnEvent e){
        BlockSnapshot block = new BlockSnapshot(e.getBlock());
        BlockSnapshot causeblock = new BlockSnapshot(e.getIgnitingBlock());
        LogQueue.enqueue(new ActionLog(ACTIONTYPE.BlockBurn, Helper.NBTFileFromLocation(block.location), block, causeblock));
    }

    @EventHandler
    public void onBlockFade(BlockFadeEvent e){
        BlockSnapshot oldblock = new BlockSnapshot(e.getBlock());
        BlockSnapshot newblock = new BlockSnapshot(e.getNewState().getBlock());

        LogQueue.enqueue(new ActionLog(ACTIONTYPE.BlockFade, Helper.NBTFileFromLocation(oldblock.location), oldblock, newblock));
    }

    @EventHandler
    public void onLeavesDecay(LeavesDecayEvent e){
        BlockSnapshot block = new BlockSnapshot(e.getBlock());
        LogQueue.enqueue(new ActionLog(ACTIONTYPE.LeavesDecay, Helper.NBTFileFromLocation(block.location), block));
    }

    @EventHandler
    public void onBlockSpread(BlockSpreadEvent e){
        BlockSnapshot block = new BlockSnapshot(e.getBlock());
        BlockSnapshot sourceblock = new BlockSnapshot(e.getSource());
        LogQueue.enqueue(new ActionLog(ACTIONTYPE.BlockSpread, Helper.NBTFileFromLocation(block.location), block, sourceblock));
    }

    @EventHandler
    public void onBlockFromTo(BlockFromToEvent e){
        BlockSnapshot block = new BlockSnapshot(e.getBlock());
        BlockSnapshot causeBlock = new BlockSnapshot(e.getToBlock());
        LogQueue.enqueue(new ActionLog(ACTIONTYPE.BlockFromTo, Helper.NBTFileFromLocation(block.location), block, causeBlock));
    }
}
