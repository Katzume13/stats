package com.katzume.stats.event;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import com.katzume.stats.StatsMod;
import com.katzume.stats.capability.IPlayerStats;
import com.katzume.stats.capability.PlayerStatsCapability;

@Mod.EventBusSubscriber(modid = StatsMod.MODID)
public class PlayerFirstJoinHandler {
    
    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        EntityPlayer player = event.player;
        
        if (player.world.isRemote) {
            return;
        }
        
        try {
            // Verificar si es la primera entrada
            NBTTagCompound playerData = player.getEntityData();
            if (!playerData.hasKey("StatsInitialized")) {
                playerData.setBoolean("StatsInitialized", true);
                System.out.println("[Stats Mod] First join detected for player " + player.getName());
                
                if (player.hasCapability(PlayerStatsCapability.PLAYER_STATS, null)) {
                    IPlayerStats stats = PlayerStatsCapability.getStats(player);
                    if (stats != null) {
                        System.out.println("[Stats Mod] Stats initialized for player " + player.getName());
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("[Stats Mod] Error in PlayerFirstJoinHandler: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
