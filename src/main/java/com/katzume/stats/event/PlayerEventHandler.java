package com.katzume.stats.event;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import com.katzume.stats.StatsMod;
import com.katzume.stats.capability.IPlayerStats;
import com.katzume.stats.capability.PlayerStatsCapability;

@Mod.EventBusSubscriber(modid = StatsMod.MODID)
public class PlayerEventHandler {
    
    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            EntityPlayer original = event.getOriginal();
            EntityPlayer clone = event.getEntityPlayer();
            
            if (original.hasCapability(PlayerStatsCapability.PLAYER_STATS, null) &&
                clone.hasCapability(PlayerStatsCapability.PLAYER_STATS, null)) {
                
                IPlayerStats originalStats = PlayerStatsCapability.getStats(original);
                IPlayerStats cloneStats = PlayerStatsCapability.getStats(clone);
                
                cloneStats.setRace(originalStats.getRace());
                cloneStats.setConstitution(originalStats.getConstitution());
                cloneStats.setStrength(originalStats.getStrength());
                cloneStats.setDexterity(originalStats.getDexterity());
                cloneStats.setIntelligence(originalStats.getIntelligence());
                cloneStats.setLuck(originalStats.getLuck());
                cloneStats.setFocus(originalStats.getFocus());
                cloneStats.setLevel(originalStats.getLevel());
                cloneStats.setStatsXP(originalStats.getStatsXP());
            }
        }
    }
    
    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        EntityPlayer player = event.player;
        if (player.hasCapability(PlayerStatsCapability.PLAYER_STATS, null)) {
            IPlayerStats stats = PlayerStatsCapability.getStats(player);
            // Set initial max health
            player.getEntityAttribute(net.minecraft.entity.SharedMonsterAttributes.MAX_HEALTH)
                .setBaseValue(stats.getMaxHealth());
        }
    }
}
