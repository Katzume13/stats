package com.katzume.stats.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.RayTraceResult;
import com.katzume.stats.StatsMod;
import com.katzume.stats.capability.PlayerStatsCapability;
import com.katzume.stats.capability.IPlayerStats;

@Mod.EventBusSubscriber(modid = StatsMod.MODID, value = Dist.CLIENT)
public class RenderEventHandler {
    private static final int GOLD = 0xFFD4AF37;
    private static final int DARK_BG = 0x8A000000;
    
    @SubscribeEvent
    public static void onRenderGameOverlay(RenderGameOverlayEvent.Post event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.HOTBAR) {
            return;
        }

        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayer player = mc.player;
        
        if (player == null || !player.hasCapability(PlayerStatsCapability.PLAYER_STATS, null)) {
            return;
        }

        IPlayerStats stats = PlayerStatsCapability.getStats(player);
        ScaledResolution resolution = new ScaledResolution(mc);
        
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        
        // Draw main HUD
        drawMainHUD(mc, stats, player, resolution);
        
        // Draw mob health if looking at entity
        drawMobHealthBar(mc, player);
        
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    private static void drawMainHUD(Minecraft mc, IPlayerStats stats, EntityPlayer player, ScaledResolution resolution) {
        int width = resolution.getScaledWidth();
        int height = resolution.getScaledHeight();
        
        int hud_x = 10;
        int hud_y = height - 90;
        
        // Draw background border
        drawBorder(hud_x - 2, hud_y - 2, 260, 85, 2, GOLD);
        drawRectangle(hud_x, hud_y, 256, 81, DARK_BG);
        
        // Title
        mc.fontRenderer.drawString("STATS", hud_x + 10, hud_y + 5, GOLD);
        
        // Race info
        mc.fontRenderer.drawString("Raza: " + stats.getRace().name(), hud_x + 130, hud_y + 5, GOLD);
        
        int startY = hud_y + 18;
        
        // Health (Red)
        float health = player.getHealth();
        int maxHealth = stats.getMaxHealth();
        drawStatBar(mc, hud_x + 10, startY, 110, 10, health, maxHealth, 0xFFFF0000, "❤");
        mc.fontRenderer.drawString(String.format("%.1f/%.0f", health, (float)maxHealth), hud_x + 125, startY + 1, 0xFFFFFFFF);
        
        // Hunger (Blue)
        float hunger = player.getFoodStats().getFoodLevel();
        drawStatBar(mc, hud_x + 10, startY + 14, 110, 10, hunger, 20, 0xFF0066FF, "🍖");
        mc.fontRenderer.drawString(String.format("%d/20", (int)hunger), hud_x + 125, startY + 15, 0xFFFFFFFF);
        
        // Armor (Shield)
        int armor = player.getTotalArmorValue();
        drawStatBar(mc, hud_x + 10, startY + 28, 110, 10, armor, 20, 0xFFC0C0C0, "🛡");
        mc.fontRenderer.drawString(String.format("%d", armor), hud_x + 125, startY + 29, 0xFFFFFFFF);
        
        // Level and XP
        mc.fontRenderer.drawString(String.format("Lvl: %d", stats.getLevel()), hud_x + 10, startY + 45, GOLD);
        mc.fontRenderer.drawString(String.format("XP: %d/%d", stats.getStatsXP(), stats.getLevel() * 100), hud_x + 10, startY + 55, GOLD);
        
        // Skill Points
        if (stats.getSkillPoints() > 0) {
            mc.fontRenderer.drawString(String.format("Pts: +%d", stats.getSkillPoints()), hud_x + 130, startY + 45, 0xFF00FF00);
        }
    }
    
    private static void drawMobHealthBar(Minecraft mc, EntityPlayer player) {
        RayTraceResult rayTraceResult = mc.objectMouseOver;
        
        if (rayTraceResult == null || rayTraceResult.typeOfHit != RayTraceResult.Type.ENTITY) {
            return;
        }
        
        if (!(rayTraceResult.entityHit instanceof EntityLivingBase)) {
            return;
        }
        
        EntityLivingBase entity = (EntityLivingBase) rayTraceResult.entityHit;
        ScaledResolution resolution = new ScaledResolution(mc);
        
        int width = resolution.getScaledWidth();
        int height = resolution.getScaledHeight();
        int centerX = width / 2;
        int centerY = height / 2;
        
        float health = entity.getHealth();
        float maxHealth = entity.getMaxHealth();
        
        String mobName = entity.getName();
        String healthText = String.format("%.1f/%.0f", health, maxHealth);
        
        int barWidth = 150;
        int barHeight = 12;
        int barX = centerX - barWidth / 2;
        int barY = centerY + 30;
        
        // Draw background
        drawBorder(barX - 2, barY - 2, barWidth + 4, barHeight + 4, 2, GOLD);
        drawRectangle(barX, barY, barWidth, barHeight, DARK_BG);
        
        // Draw health bar
        drawStatBar(mc, barX, barY, barWidth - 2, barHeight, health, maxHealth, 0xFFFF0000, "");
        
        // Draw mob name and health text
        int nameWidth = mc.fontRenderer.getStringWidth(mobName);
        mc.fontRenderer.drawString(mobName, centerX - nameWidth / 2, barY - 12, GOLD);
        
        int healthWidth = mc.fontRenderer.getStringWidth(healthText);
        mc.fontRenderer.drawString(healthText, centerX - healthWidth / 2, barY + barHeight + 2, 0xFFFFFFFF);
    }
    
    private static void drawBorder(int x, int y, int width, int height, int thickness, int color) {
        drawRectangle(x, y, width, thickness, color);
        drawRectangle(x, y + height - thickness, width, thickness, color);
        drawRectangle(x, y, thickness, height, color);
        drawRectangle(x + width - thickness, y, thickness, height, color);
    }
    
    private static void drawStatBar(Minecraft mc, int x, int y, int width, int height, float current, float max, int color, String icon) {
        // Background
        drawRectangle(x + 15, y, width, height, 0xFF333333);
        
        // Fill
        int fillWidth = (int) ((current / max) * width);
        drawRectangle(x + 15, y, fillWidth, height, color);
        
        // Border
        drawRectangleBorder(x + 15, y, width, height, 1, GOLD);
        
        // Icon
        if (!icon.isEmpty()) {
            mc.fontRenderer.drawString(icon, x, y + 1, 0xFFFFFFFF);
        }
    }
    
    private static void drawRectangle(int x, int y, int width, int height, int color) {
        float a = (color >> 24 & 255) / 255.0F;
        float r = (color >> 16 & 255) / 255.0F;
        float g = (color >> 8 & 255) / 255.0F;
        float b = (color & 255) / 255.0F;
        
        GlStateManager.color(r, g, b, a);
        fillRect(x, y, x + width, y + height, color);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    private static void drawRectangleBorder(int x, int y, int width, int height, int thickness, int color) {
        drawRectangle(x, y, width, thickness, color);
        drawRectangle(x, y + height - thickness, width, thickness, color);
        drawRectangle(x, y, thickness, height, color);
        drawRectangle(x + width - thickness, y, thickness, height, color);
    }
    
    private static void fillRect(int x1, int y1, int x2, int y2, int color) {
        if (x1 < x2) {
            int temp = x1;
            x1 = x2;
            x2 = temp;
        }
        if (y1 < y2) {
            int temp = y1;
            y1 = y2;
            y2 = temp;
        }
        
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        GlStateManager.disableTexture2D();
        
        float a = (color >> 24 & 255) / 255.0F;
        float r = (color >> 16 & 255) / 255.0F;
        float g = (color >> 8 & 255) / 255.0F;
        float b = (color & 255) / 255.0F;
        
        GlStateManager.color(r, g, b, a);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
}
