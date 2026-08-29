package com.katzume.stats.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import com.katzume.stats.capability.IPlayerStats;
import com.katzume.stats.capability.PlayerStatsCapability;

public class StatsScreenGUI extends GuiScreen {
    private IPlayerStats stats;
    private final int GOLD = 0xFFD4AF37;
    private final int DARK_BG = 0xFF2B2B2B;
    private final int BORDER = 0xFF8B7355;
    
    @Override
    public void initGui() {
        stats = PlayerStatsCapability.getStats(this.mc.player);
    }
    
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        
        int centerX = this.width / 2;
        int centerY = this.height / 2;
        int panelWidth = 300;
        int panelHeight = 350;
        int x = centerX - panelWidth / 2;
        int y = centerY - panelHeight / 2;
        
        // Draw main border
        drawBorder(x - 2, y - 2, panelWidth + 4, panelHeight + 4, 3, GOLD);
        drawRectangle(x, y, panelWidth, panelHeight, DARK_BG);
        
        // Title
        drawCenteredString(this.fontRenderer, "STATS", centerX, y + 10, GOLD);
        
        int lineY = y + 30;
        int lineHeight = 16;
        
        // Race
        drawString(this.fontRenderer, "Raza: " + stats.getRace().name(), x + 15, lineY, GOLD);
        lineY += lineHeight + 5;
        
        // Level and XP
        drawString(this.fontRenderer, "Nivel: " + stats.getLevel(), x + 15, lineY, GOLD);
        drawString(this.fontRenderer, "XP: " + stats.getStatsXP() + "/" + (stats.getLevel() * 100), x + 150, lineY, GOLD);
        lineY += lineHeight + 10;
        
        // Separator
        drawRectangle(x + 10, lineY - 5, panelWidth - 20, 1, GOLD);
        lineY += 5;
        
        // Base Stats Header
        drawString(this.fontRenderer, "ESTADISTICAS BASE", x + 15, lineY, GOLD);
        lineY += lineHeight + 5;
        
        // Individual stats
        drawStat(this.fontRenderer, "Constitucion", stats.getConstitution(), stats.getRace().getConstitutionBonus(), x + 15, lineY);
        lineY += lineHeight;
        drawStat(this.fontRenderer, "Fuerza", stats.getStrength(), stats.getRace().getStrengthBonus(), x + 15, lineY);
        lineY += lineHeight;
        drawStat(this.fontRenderer, "Destreza", stats.getDexterity(), stats.getRace().getDexterityBonus(), x + 15, lineY);
        lineY += lineHeight;
        drawStat(this.fontRenderer, "Inteligencia", stats.getIntelligence(), stats.getRace().getIntelligenceBonus(), x + 15, lineY);
        lineY += lineHeight;
        drawStat(this.fontRenderer, "Suerte", stats.getLuck(), stats.getRace().getLuckBonus(), x + 15, lineY);
        lineY += lineHeight;
        drawStat(this.fontRenderer, "Enfoque", stats.getFocus(), stats.getRace().getFocusBonus(), x + 15, lineY);
        lineY += lineHeight + 10;
        
        // Separator
        drawRectangle(x + 10, lineY - 5, panelWidth - 20, 1, GOLD);
        lineY += 5;
        
        // Derived Stats
        drawString(this.fontRenderer, "ESTADISTICAS DERIVADAS", x + 15, lineY, GOLD);
        lineY += lineHeight + 5;
        
        drawString(this.fontRenderer, "Vida Max: " + stats.getMaxHealth() + " HP", x + 15, lineY, 0xFFFF0000);
        lineY += lineHeight;
        drawString(this.fontRenderer, "Daño: +" + String.format("%.1f", stats.getAttackDamage()), x + 15, lineY, 0xFFFF6600);
        lineY += lineHeight;
        drawString(this.fontRenderer, "Velocidad Ataque: +" + String.format("%.1f", stats.getAttackSpeed()), x + 15, lineY, 0xFFFFFF00);
        lineY += lineHeight;
        drawString(this.fontRenderer, "Velocidad Mineria: x" + String.format("%.2f", stats.getMiningSpeed()), x + 15, lineY, 0xFF00FF00);
        lineY += lineHeight + 10;
        
        // Skill Points
        drawString(this.fontRenderer, "Puntos Disponibles: " + stats.getSkillPoints(), x + 15, lineY, 0xFF00FF00);
        
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    private void drawStat(net.minecraft.client.gui.FontRenderer fontRenderer, String name, int baseValue, int bonus, int x, int y) {
        int totalValue = baseValue + bonus;
        String text = name + ": " + baseValue;
        if (bonus != 0) {
            text += " (" + (bonus > 0 ? "+" : "") + bonus + ")";
        }
        drawString(fontRenderer, text, x, y, 0xFFFFFFFF);
        drawString(fontRenderer, "= " + totalValue, x + 150, y, GOLD);
    }
    
    private void drawBorder(int x, int y, int width, int height, int thickness, int color) {
        drawRectangle(x, y, width, thickness, color);
        drawRectangle(x, y + height - thickness, width, thickness, color);
        drawRectangle(x, y, thickness, height, color);
        drawRectangle(x + width - thickness, y, thickness, height, color);
    }
    
    private void drawRectangle(int x, int y, int width, int height, int color) {
        float a = (color >> 24 & 255) / 255.0F;
        float r = (color >> 16 & 255) / 255.0F;
        float g = (color >> 8 & 255) / 255.0F;
        float b = (color & 255) / 255.0F;
        
        GlStateManager.color(r, g, b, a);
        GlStateManager.disableTexture2D();
        fillRect(x, y, x + width, y + height, color);
        GlStateManager.enableTexture2D();
    }
    
    private void fillRect(int x1, int y1, int x2, int y2, int color) {
        int temp;
        if (x1 < x2) {
            temp = x1;
            x1 = x2;
            x2 = temp;
        }
        if (y1 < y2) {
            temp = y1;
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
    
    @Override
    public boolean doesGuiPauseGame() {
        return true;
    }
    
    @Override
    public void keyTyped(char typedChar, int keyCode) {
        if (keyCode == 1 || keyCode == 19) { // ESC or I key
            this.mc.displayGuiScreen(null);
            if (this.mc.currentScreen == null) {
                this.mc.setIngameFocus();
            }
        }
    }
}
