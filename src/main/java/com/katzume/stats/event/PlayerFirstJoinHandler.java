package com.katzume.stats.event;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import com.katzume.stats.StatsMod;
import com.katzume.stats.capability.IPlayerStats;
import com.katzume.stats.capability.PlayerStatsCapability;
import com.katzume.stats.client.gui.RaceSelectionGUI;
import com.katzume.stats.util.StatsUtil;

@Mod.EventBusSubscriber(modid = StatsMod.MODID)
public class PlayerFirstJoinHandler {
    
    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        EntityPlayer player = event.player;
        
        if (player.world.isRemote) {
            return;
        }
        
        // Verificar si es la primera entrada
        NBTTagCompound playerData = player.getEntityData();
        if (!playerData.hasKey("StatsInitialized")) {
            playerData.setBoolean("StatsInitialized", true);
            
            // Mostrar GUI de selección de raza en el cliente
            if (player instanceof EntityPlayer) {
                // En el próximo tick del cliente, mostrar GUI
                net.minecraftforge.fml.common.FMLCommonHandler.instance()
                    .getMinecraftServerInstance()
                    .getWorld(0)
                    .scheduleBlockUpdate(player.getPosition(), null);
            }
        }
    }
}
