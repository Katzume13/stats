package com.katzume.stats.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.gui.GuiButton;
import com.katzume.stats.capability.IPlayerStats;
import com.katzume.stats.capability.PlayerStatsCapability;
import com.katzume.stats.race.Race;

public class RaceSelectionGUI extends GuiScreen {
    private IPlayerStats stats;
    private final int GOLD = 0xFFD4AF37;
    private final int DARK_BG = 0xFF2B2B2B;
    private final int BORDER = 0xFF8B7355;
    private GuiButton selectedButton = null;
    
    @Override
    public void initGui() {
        stats = PlayerStatsCapability.getStats(this.mc.player);
        
        int centerX = this.width / 2;
        int centerY = this.height / 2;
        int buttonWidth = 120;
        int buttonHeight = 40;
        int spacing = 10;
        
        int y = centerY - 80;
        
        // Botones de razas
        this.addButton(new GuiButton(0, centerX - buttonWidth / 2, y, buttonWidth, buttonHeight, "HUMAN"));
        y += buttonHeight + spacing;
        this.addButton(new GuiButton(1, centerX - buttonWidth / 2, y, buttonWidth, buttonHeight, "DWARF"));
        y += buttonHeight + spacing;
        this.addButton(new GuiButton(2, centerX - buttonWidth / 2, y, buttonWidth, buttonHeight, "ELF"));
        y += buttonHeight + spacing;
        this.addButton(new GuiButton(3, centerX - buttonWidth / 2, y, buttonWidth, buttonHeight, "ORC"));
        y += buttonHeight + spacing;
        this.addButton(new GuiButton(4, centerX - buttonWidth / 2, y, buttonWidth, buttonHeight, "LIZARD"));
        y += buttonHeight + spacing + 10;
        
        // Botón de confirmar
        this.addButton(new GuiButton(5, centerX - 60, y, 120, 30, "CONFIRMAR"));
    }
    
    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.id >= 0 && button.id <= 4) {
            Race[] races = Race.values();
            stats.setRace(races[button.id]);
            
            // Actualizar vida máxima
            this.mc.player.getEntityAttribute(net.minecraft.entity.SharedMonsterAttributes.MAX_HEALTH)
                .setBaseValue(stats.getMaxHealth());
            
            selectedButton = button;
        } else if (button.id == 5) {
            // Cerrar pantalla
            this.mc.displayGuiScreen(null);
            if (this.mc.currentScreen == null) {
                this.mc.setIngameFocus();
            }
        }
    }
    
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        
        int centerX = this.width / 2;
        int centerY = this.height / 2;
        int panelWidth = 300;
        int panelHeight = 400;
        int x = centerX - panelWidth / 2;
        int y = centerY - panelHeight / 2;
        
        // Draw main border
        drawBorder(x - 2, y - 2, panelWidth + 4, panelHeight + 4, 3, GOLD);
        drawRectangle(x, y, panelWidth, panelHeight, DARK_BG);
        
        // Title
        drawCenteredString(this.fontRenderer, "SELECCIONA TU RAZA", centerX, y + 15, GOLD);
        
        super.drawScreen(mouseX, mouseY, partialTicks);
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
}
