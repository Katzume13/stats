package com.katzume.stats.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import com.katzume.stats.capability.IPlayerStats;
import com.katzume.stats.capability.PlayerStatsCapability;
import com.katzume.stats.race.Race;

public class SetRaceCommand extends CommandBase {
    
    @Override
    public String getName() {
        return "setraza";
    }
    
    @Override
    public String getUsage(ICommandSender sender) {
        return "/setraza <raza>";
    }
    
    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (!(sender instanceof EntityPlayer)) {
            sender.sendMessage(new TextComponentString("Solo jugadores pueden usar este comando"));
            return;
        }
        
        if (args.length < 1) {
            sender.sendMessage(new TextComponentString("Uso: /setraza <human|dwarf|elf|orc|lizard>"));
            return;
        }
        
        EntityPlayer player = (EntityPlayer) sender;
        
        if (!player.hasCapability(PlayerStatsCapability.PLAYER_STATS, null)) {
            sender.sendMessage(new TextComponentString("Error: No se pudo acceder a las estadísticas"));
            return;
        }
        
        try {
            Race race = Race.fromString(args[0]);
            IPlayerStats stats = PlayerStatsCapability.getStats(player);
            stats.setRace(race);
            
            // Actualizar vida máxima
            player.getEntityAttribute(net.minecraft.entity.SharedMonsterAttributes.MAX_HEALTH)
                .setBaseValue(stats.getMaxHealth());
            
            sender.sendMessage(new TextComponentString("§6Raza cambiada a: §e" + race.name()));
            sender.sendMessage(new TextComponentString("§6Bonificadores: §e"
                + "Con +" + race.getConstitutionBonus() 
                + " Fue +" + race.getStrengthBonus()
                + " Des +" + race.getDexterityBonus()));
        } catch (Exception e) {
            sender.sendMessage(new TextComponentString("§cRaza inválida: " + args[0]));
        }
    }
}
