package com.katzume.stats.event;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import com.katzume.stats.StatsMod;
import com.katzume.stats.capability.IPlayerStats;
import com.katzume.stats.capability.PlayerStatsCapability;

@Mod.EventBusSubscriber(modid = StatsMod.MODID)
public class CombatEventHandler {
    
    @SubscribeEvent
    public static void onPlayerAttack(LivingHurtEvent event) {
        if (event.getEntity() instanceof EntityPlayer) {
            return; // Solo aplicar cuando el jugador ataca a otros
        }
        
        DamageSource source = event.getSource();
        if (source.getTrueSource() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) source.getTrueSource();
            
            if (!player.hasCapability(PlayerStatsCapability.PLAYER_STATS, null)) {
                return;
            }
            
            IPlayerStats stats = PlayerStatsCapability.getStats(player);
            float extraDamage = stats.getTotalStrength() * 0.5f;
            
            // Aplicar daño extra
            event.setAmount(event.getAmount() + extraDamage);
        }
    }
    
    @SubscribeEvent
    public static void onPlayerHurt(LivingHurtEvent event) {
        if (!(event.getEntity() instanceof EntityPlayer)) {
            return;
        }
        
        EntityPlayer player = (EntityPlayer) event.getEntity();
        if (!player.hasCapability(PlayerStatsCapability.PLAYER_STATS, null)) {
            return;
        }
        
        IPlayerStats stats = PlayerStatsCapability.getStats(player);
        
        // Reducir daño por defensa (armadura + inteligencia)
        float damageReduction = (player.getTotalArmorValue() + stats.getTotalIntelligence()) * 0.05f;
        damageReduction = Math.min(damageReduction, 0.8f); // Máximo 80% reducción
        
        event.setAmount(event.getAmount() * (1 - damageReduction));
    }
}
