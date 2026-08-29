package com.katzume.stats.event;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import com.katzume.stats.StatsMod;
import com.katzume.stats.capability.IPlayerStats;
import com.katzume.stats.capability.PlayerStatsCapability;
import com.katzume.stats.entity.EntityStatsXPOrb;

@Mod.EventBusSubscriber(modid = StatsMod.MODID)
public class SpecialXPEventHandler {
    
    @SubscribeEvent
    public static void onMobKill(LivingExperienceDropEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        
        if (entity.world.isRemote || entity instanceof EntityPlayer) {
            return;
        }
        
        // El jugador que mató al mob
        if (entity.attackingPlayer != null) {
            EntityPlayer player = entity.attackingPlayer;
            
            if (player.hasCapability(PlayerStatsCapability.PLAYER_STATS, null)) {
                IPlayerStats stats = PlayerStatsCapability.getStats(player);
                
                // Crear orbe XP especial (rojo) en lugar del verde normal
                int xpAmount = event.getOriginalExperience();
                EntityStatsXPOrb statsXpOrb = new EntityStatsXPOrb(entity.world, 
                    entity.posX, entity.posY, entity.posZ, xpAmount);
                entity.world.spawnEntity(statsXpOrb);
                
                // Dar XP al jugador también
                stats.addStatsXP(xpAmount);
                
                // Cancelar drop de XP normal verde
                event.setDroppedExperience(0);
            }
        }
    }
}
