package com.katzume.stats.event;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.client.event.InputEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;
import com.katzume.stats.StatsMod;
import com.katzume.stats.client.gui.StatsScreenGUI;

@Mod.EventBusSubscriber(modid = StatsMod.MODID, value = Side.CLIENT)
public class KeyInputHandler {
    
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onKeyInput(InputEvent.KeyInputEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        
        if (mc.player == null || mc.currentScreen != null) {
            return;
        }
        
        // Tecla I (código 23)
        if (Keyboard.isKeyDown(Keyboard.KEY_I)) {
            mc.displayGuiScreen(new StatsScreenGUI());
        }
    }
}
