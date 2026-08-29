package com.katzume.stats.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import com.katzume.stats.StatsMod;
import com.katzume.stats.capability.IPlayerStats;
import com.katzume.stats.capability.PlayerStatsCapability;

public class NetworkManager {
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(StatsMod.MODID);
    private static int packetId = 0;
    
    public static void registerMessages() {
        INSTANCE.registerMessage(SyncStatsMessage.Handler.class, SyncStatsMessage.class, packetId++, Side.CLIENT);
        INSTANCE.registerMessage(UpdateStatsMessage.Handler.class, UpdateStatsMessage.class, packetId++, Side.SERVER);
    }
    
    // Mensaje para sincronizar stats del servidor al cliente
    public static class SyncStatsMessage implements IMessage {
        public int constitution;
        public int strength;
        public int dexterity;
        public int intelligence;
        public int luck;
        public int focus;
        public int level;
        public int statsXP;
        public int skillPoints;
        public String race;
        
        public SyncStatsMessage() {}
        
        public SyncStatsMessage(IPlayerStats stats) {
            this.constitution = stats.getConstitution();
            this.strength = stats.getStrength();
            this.dexterity = stats.getDexterity();
            this.intelligence = stats.getIntelligence();
            this.luck = stats.getLuck();
            this.focus = stats.getFocus();
            this.level = stats.getLevel();
            this.statsXP = stats.getStatsXP();
            this.skillPoints = stats.getSkillPoints();
            this.race = stats.getRace().name();
        }
        
        @Override
        public void fromBytes(ByteBuf buf) {
            constitution = buf.readInt();
            strength = buf.readInt();
            dexterity = buf.readInt();
            intelligence = buf.readInt();
            luck = buf.readInt();
            focus = buf.readInt();
            level = buf.readInt();
            statsXP = buf.readInt();
            skillPoints = buf.readInt();
            int raceLen = buf.readInt();
            byte[] raceBytes = new byte[raceLen];
            buf.readBytes(raceBytes);
            race = new String(raceBytes);
        }
        
        @Override
        public void toBytes(ByteBuf buf) {
            buf.writeInt(constitution);
            buf.writeInt(strength);
            buf.writeInt(dexterity);
            buf.writeInt(intelligence);
            buf.writeInt(luck);
            buf.writeInt(focus);
            buf.writeInt(level);
            buf.writeInt(statsXP);
            buf.writeInt(skillPoints);
            byte[] raceBytes = race.getBytes();
            buf.writeInt(raceBytes.length);
            buf.writeBytes(raceBytes);
        }
        
        public static class Handler implements IMessageHandler<SyncStatsMessage, IMessage> {
            @Override
            public IMessage onMessage(SyncStatsMessage message, MessageContext ctx) {
                EntityPlayer player = ctx.getClientHandler().player;
                if (player != null && player.hasCapability(PlayerStatsCapability.PLAYER_STATS, null)) {
                    IPlayerStats stats = PlayerStatsCapability.getStats(player);
                    stats.setConstitution(message.constitution);
                    stats.setStrength(message.strength);
                    stats.setDexterity(message.dexterity);
                    stats.setIntelligence(message.intelligence);
                    stats.setLuck(message.luck);
                    stats.setFocus(message.focus);
                    stats.setLevel(message.level);
                    stats.setStatsXP(message.statsXP);
                    stats.setRace(com.katzume.stats.race.Race.fromString(message.race));
                }
                return null;
            }
        }
    }
    
    // Mensaje para actualizar stats del cliente al servidor
    public static class UpdateStatsMessage implements IMessage {
        public int constitution;
        public int strength;
        public int dexterity;
        public int intelligence;
        public int luck;
        public int focus;
        public String race;
        
        public UpdateStatsMessage() {}
        
        public UpdateStatsMessage(IPlayerStats stats) {
            this.constitution = stats.getConstitution();
            this.strength = stats.getStrength();
            this.dexterity = stats.getDexterity();
            this.intelligence = stats.getIntelligence();
            this.luck = stats.getLuck();
            this.focus = stats.getFocus();
            this.race = stats.getRace().name();
        }
        
        @Override
        public void fromBytes(ByteBuf buf) {
            constitution = buf.readInt();
            strength = buf.readInt();
            dexterity = buf.readInt();
            intelligence = buf.readInt();
            luck = buf.readInt();
            focus = buf.readInt();
            int raceLen = buf.readInt();
            byte[] raceBytes = new byte[raceLen];
            buf.readBytes(raceBytes);
            race = new String(raceBytes);
        }
        
        @Override
        public void toBytes(ByteBuf buf) {
            buf.writeInt(constitution);
            buf.writeInt(strength);
            buf.writeInt(dexterity);
            buf.writeInt(intelligence);
            buf.writeInt(luck);
            buf.writeInt(focus);
            byte[] raceBytes = race.getBytes();
            buf.writeInt(raceBytes.length);
            buf.writeBytes(raceBytes);
        }
        
        public static class Handler implements IMessageHandler<UpdateStatsMessage, IMessage> {
            @Override
            public IMessage onMessage(UpdateStatsMessage message, MessageContext ctx) {
                EntityPlayerMP player = ctx.getServerHandler().player;
                if (player != null && player.hasCapability(PlayerStatsCapability.PLAYER_STATS, null)) {
                    IPlayerStats stats = PlayerStatsCapability.getStats(player);
                    stats.setConstitution(message.constitution);
                    stats.setStrength(message.strength);
                    stats.setDexterity(message.dexterity);
                    stats.setIntelligence(message.intelligence);
                    stats.setLuck(message.luck);
                    stats.setFocus(message.focus);
                    stats.setRace(com.katzume.stats.race.Race.fromString(message.race));
                    stats.markDirty();
                    
                    // Broadcast a otros clientes
                    NetworkManager.INSTANCE.sendToAll(new SyncStatsMessage(stats));
                }
                return null;
            }
        }
    }
}
