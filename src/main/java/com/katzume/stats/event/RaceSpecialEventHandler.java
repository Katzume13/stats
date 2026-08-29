package com.katzume.stats.event;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import com.katzume.stats.StatsMod;
import com.katzume.stats.capability.IPlayerStats;
import com.katzume.stats.capability.PlayerStatsCapability;

@Mod.EventBusSubscriber(modid = StatsMod.MODID)
public class RaceSpecialEventHandler {
    
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
        
        // Dwarf: Minería más rápida (Ya aplicada por atributos)
        // Elf: Reducción de fatiga
        // Orc: Más XP por romper bloques de piedra
        // Lizard: Salto mejorado
        
        switch (stats.getRace()) {
            case ORC:
                // Los Orcs ganan XP extra al minar
                stats.addStatsXP(2);
                break;
            case DWARF:
                // Ya tiene minería más rápida por atributos
                stats.addStatsXP(3);
                break;
            default:
                stats.addStatsXP(1);
        }
    }
}
