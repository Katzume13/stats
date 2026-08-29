package com.katzume.stats.event;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
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
        
        if (player.hasCapability(PlayerStatsCapability.PLAYER_STATS, null)) {
            IPlayerStats stats = PlayerStatsCapability.getStats(player);
            
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
            }
        }
    }
    
    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        EntityPlayer player = event.player;
        
        if (player.world.isRemote) {
            return;
        }
        
        if (player.hasCapability(PlayerStatsCapability.PLAYER_STATS, null)) {
            IPlayerStats stats = PlayerStatsCapability.getStats(player);
            
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
        }
    }
}
