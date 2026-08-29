package com.katzume.stats.command;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraft.server.MinecraftServer;
import com.katzume.stats.StatsMod;

@Mod.EventBusSubscriber(modid = StatsMod.MODID)
public class CommandRegistry {
    
    @SubscribeEvent
    public static void onServerStart(WorldEvent.Load event) {
        if (event.getWorld().isRemote) {
            return;
        }
        
        MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
        if (server != null) {
            server.getCommandManager().registerCommand(new SetRaceCommand());
        }
    }
}
