package com.katzume.stats.event;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.EntityLivingBase;
import com.katzume.stats.StatsMod;
import com.katzume.stats.capability.IPlayerStats;
import com.katzume.stats.capability.PlayerStatsCapability;

@Mod.EventBusSubscriber(modid = StatsMod.MODID)
public class XPEventHandler {
    
    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        if (event.getWorld().isRemote) {
            return;
        }
        
        EntityPlayer player = event.getPlayer();
        if (player == null || !player.hasCapability(PlayerStatsCapability.PLAYER_STATS, null)) {
            return;
        }
        
        try {
            IPlayerStats stats = PlayerStatsCapability.getStats(player);
            if (stats != null) {
                int xpGain = 5; // XP por romper bloque
                stats.addStatsXP(xpGain);
            }
        } catch (Exception e) {
            System.err.println("[Stats Mod] Error in onBlockBreak: " + e.getMessage());
        }
    }
    
    @SubscribeEvent
    public static void onMobKill(LivingExperienceDropEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        
        if (entity.world.isRemote || entity instanceof EntityPlayer) {
            return;
        }
        
        try {
            // El jugador que mató al mob
            if (entity.attackingPlayer != null) {
                EntityPlayer player = entity.attackingPlayer;
                
                if (player.hasCapability(PlayerStatsCapability.PLAYER_STATS, null)) {
                    IPlayerStats stats = PlayerStatsCapability.getStats(player);
                    if (stats != null) {
                        // XP basado en el mob
                        int xpGain = event.getOriginalExperience();
                        stats.addStatsXP(xpGain);
                        
                        // Cancelar drop de XP normal
                        event.setDroppedExperience(0);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("[Stats Mod] Error in onMobKill: " + e.getMessage());
        }
    }
}
