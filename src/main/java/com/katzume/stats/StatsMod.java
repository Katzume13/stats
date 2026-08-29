package com.katzume.stats;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.common.MinecraftForge;
import com.katzume.stats.capability.PlayerStatsCapability;
import com.katzume.stats.command.CommandRegistry;
import com.katzume.stats.network.NetworkManager;

@Mod(modid = StatsMod.MODID, name = StatsMod.NAME, version = StatsMod.VERSION)
public class StatsMod {
    public static final String MODID = "stats";
    public static final String NAME = "Stats Mod";
    public static final String VERSION = "1.0.0";

    @EventHandler
    public void preinit(FMLPreInitializationEvent event) {
        PlayerStatsCapability.register();
        NetworkManager.registerMessages();
        MinecraftForge.EVENT_BUS.register(this);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new CommandRegistry());
    }
    
    @EventHandler
    public void postinit(FMLPostInitializationEvent event) {
    }
    
    @EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new com.katzume.stats.command.SetRaceCommand());
    }
}
