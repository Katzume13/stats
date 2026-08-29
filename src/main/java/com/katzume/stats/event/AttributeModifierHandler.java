package com.katzume.stats.event;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import java.util.UUID;
import com.katzume.stats.StatsMod;
import com.katzume.stats.capability.IPlayerStats;
import com.katzume.stats.capability.PlayerStatsCapability;

@Mod.EventBusSubscriber(modid = StatsMod.MODID)
public class AttributeModifierHandler {
    private static final UUID STRENGTH_MODIFIER = UUID.fromString("d5b64fc8-bb9f-46e1-8c4f-5a3b7c3c3c3c");
    private static final UUID DEXTERITY_MODIFIER = UUID.fromString("a1b2c3d4-e5f6-47a8-b9c0-d1e2f3a4b5c6");
    private static final UUID SPEED_MODIFIER = UUID.fromString("f4a3c2b1-0e9d-8c7b-6a5f-e4d3c2b1a0f9");
    
    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        EntityPlayer player = event.player;
        
        if (player.world.isRemote || !player.hasCapability(PlayerStatsCapability.PLAYER_STATS, null)) {
            return;
        }
        
        IPlayerStats stats = PlayerStatsCapability.getStats(player);
        applyModifiers(player, stats);
    }
    
    @SubscribeEvent
    public static void onRespawn(PlayerEvent.PlayerRespawnEvent event) {
        EntityPlayer player = event.player;
        
        if (player.world.isRemote || !player.hasCapability(PlayerStatsCapability.PLAYER_STATS, null)) {
            return;
        }
        
        IPlayerStats stats = PlayerStatsCapability.getStats(player);
        applyModifiers(player, stats);
    }
    
    private static void applyModifiers(EntityPlayer player, IPlayerStats stats) {
        // Aplicar vida máxima
        player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH)
            .setBaseValue(stats.getMaxHealth());
        
        // Aplicar velocidad de ataque (Attack Speed)
        float attackSpeed = stats.getAttackSpeed();
        var attackSpeedAttr = player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED);
        if (attackSpeedAttr.getModifier(DEXTERITY_MODIFIER) != null) {
            attackSpeedAttr.removeModifier(attackSpeedAttr.getModifier(DEXTERITY_MODIFIER));
        }
        attackSpeedAttr.applyModifier(new AttributeModifier(
            DEXTERITY_MODIFIER, "Dexterity Attack Speed", attackSpeed - 4.0f, 0
        ));
        
        // Aplicar velocidad de movimiento
        float speedBonus = (stats.getTotalDexterity() * 0.01f) + (stats.getRace().getSpeedMultiplier() - 1.0f);
        var speedAttr = player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
        if (speedAttr.getModifier(SPEED_MODIFIER) != null) {
            speedAttr.removeModifier(speedAttr.getModifier(SPEED_MODIFIER));
        }
        speedAttr.applyModifier(new AttributeModifier(
            SPEED_MODIFIER, "Race Speed Bonus", speedBonus, 1
        ));
    }
}
