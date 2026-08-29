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
        try {
            System.out.println("[Stats Mod] Registering capabilities...");
            PlayerStatsCapability.register();
            System.out.println("[Stats Mod] Capabilities registered successfully!");
        } catch (Exception e) {
            System.err.println("[Stats Mod] Error registering capabilities: " + e.getMessage());
            e.printStackTrace();
        }
        
        MinecraftForge.EVENT_BUS.register(this);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        try {
            System.out.println("[Stats Mod] Registering network messages...");
            NetworkManager.registerMessages();
            System.out.println("[Stats Mod] Network messages registered successfully!");
        } catch (Exception e) {
            System.err.println("[Stats Mod] Error registering network: " + e.getMessage());
            e.printStackTrace();
        }
        
        MinecraftForge.EVENT_BUS.register(new CommandRegistry());
    }
    
    @EventHandler
    public void postinit(FMLPostInitializationEvent event) {
        System.out.println("[Stats Mod] v" + VERSION + " loaded successfully!");
    }
    
    @EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        try {
            event.registerServerCommand(new com.katzume.stats.command.SetRaceCommand());
        } catch (Exception e) {
            System.err.println("[Stats Mod] Error registering commands: " + e.getMessage());
        }
    }
}
