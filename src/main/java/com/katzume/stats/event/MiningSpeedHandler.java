package com.katzume.stats.event;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.block.Block;
import com.katzume.stats.StatsMod;
import com.katzume.stats.capability.IPlayerStats;
import com.katzume.stats.capability.PlayerStatsCapability;

@Mod.EventBusSubscriber(modid = StatsMod.MODID)
public class MiningSpeedHandler {
    
    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        if (event.getWorld().isRemote) {
            return;
        }
        
        EntityPlayer player = event.getPlayer();
        if (player == null || !player.hasCapability(PlayerStatsCapability.PLAYER_STATS, null)) {
            return;
        }
        
        IPlayerStats stats = PlayerStatsCapability.getStats(player);
        float miningSpeedMultiplier = stats.getMiningSpeed();
        
        // Aplicar multiplicador de velocidad de minería
        // Esto se hace reduciendo el tiempo de ruptura
    }
}
