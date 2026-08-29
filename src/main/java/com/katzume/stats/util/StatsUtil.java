package com.katzume.stats.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import com.katzume.stats.capability.IPlayerStats;
import com.katzume.stats.capability.PlayerStatsCapability;
import com.katzume.stats.race.Race;

public class StatsUtil {
    
    public static boolean isFirstJoin(EntityPlayer player) {
        return !player.world.getWorldInfo().playerNBTtagCompound(player.getName()).hasKey("StatsInitialized");
    }
    
    public static void markPlayerInitialized(EntityPlayer player) {
        if (!player.world.isRemote) {
            var nbt = player.world.getWorldInfo().playerNBTtagCompound(player.getName());
            nbt.setBoolean("StatsInitialized", true);
        }
    }
    
    public static void addStatPoint(EntityPlayer player, String stat) {
        if (!player.hasCapability(PlayerStatsCapability.PLAYER_STATS, null)) {
            return;
        }
        
        IPlayerStats stats = PlayerStatsCapability.getStats(player);
        
        if (stats.getSkillPoints() <= 0) {
            return;
        }
        
        switch (stat.toLowerCase()) {
            case "constitution":
                stats.setConstitution(stats.getConstitution() + 1);
                player.getEntityAttribute(net.minecraft.entity.SharedMonsterAttributes.MAX_HEALTH)
                    .setBaseValue(stats.getMaxHealth());
                break;
            case "strength":
                stats.setStrength(stats.getStrength() + 1);
                break;
            case "dexterity":
                stats.setDexterity(stats.getDexterity() + 1);
                break;
            case "intelligence":
                stats.setIntelligence(stats.getIntelligence() + 1);
                break;
            case "luck":
                stats.setLuck(stats.getLuck() + 1);
                break;
            case "focus":
                stats.setFocus(stats.getFocus() + 1);
                break;
        }
        
        stats.spendSkillPoints(1);
        stats.markDirty();
    }
}
