package com.katzume.stats.event;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.SharedMonsterAttributes;
import com.katzume.stats.StatsMod;
import com.katzume.stats.capability.IPlayerStats;
import com.katzume.stats.capability.PlayerStatsCapability;
import com.katzume.stats.network.NetworkManager;

@Mod.EventBusSubscriber(modid = StatsMod.MODID)
public class PlayerSyncHandler {
    
    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        EntityPlayer player = event.player;
        
        if (player.world.isRemote) {
            return;
        }
        
        try {
            if (!player.hasCapability(PlayerStatsCapability.PLAYER_STATS, null)) {
                System.err.println("[Stats Mod] Player " + player.getName() + " does not have stats capability!");
                return;
            }
            
            IPlayerStats stats = PlayerStatsCapability.getStats(player);
            if (stats == null) {
                System.err.println("[Stats Mod] Could not get stats for player " + player.getName());
                return;
            }
            
            // Aplicar vida máxima
            player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH)
                .setBaseValue(stats.getMaxHealth());
            
            // Sincronizar stats al cliente
            if (player instanceof EntityPlayerMP) {
                EntityPlayerMP playerMP = (EntityPlayerMP) player;
                NetworkManager.INSTANCE.sendTo(
                    new NetworkManager.SyncStatsMessage(stats),
                    playerMP
                );
                System.out.println("[Stats Mod] Synced stats for player " + player.getName());
            }
        } catch (Exception e) {
            System.err.println("[Stats Mod] Error in onPlayerJoin: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        EntityPlayer player = event.player;
        
        if (player.world.isRemote) {
            return;
        }
        
        try {
            if (!player.hasCapability(PlayerStatsCapability.PLAYER_STATS, null)) {
                return;
            }
            
            IPlayerStats stats = PlayerStatsCapability.getStats(player);
            if (stats == null) {
                return;
            }
            
            // Restaurar vida máxima
            player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH)
                .setBaseValue(stats.getMaxHealth());
            player.setHealth(stats.getMaxHealth());
            
            // Sincronizar al cliente
            if (player instanceof EntityPlayerMP) {
                EntityPlayerMP playerMP = (EntityPlayerMP) player;
                NetworkManager.INSTANCE.sendTo(
                    new NetworkManager.SyncStatsMessage(stats),
                    playerMP
                );
            }
        } catch (Exception e) {
            System.err.println("[Stats Mod] Error in onPlayerRespawn: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
