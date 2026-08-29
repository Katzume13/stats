package com.katzume.stats.capability;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

public class PlayerStatsProvider implements ICapabilitySerializable<NBTTagCompound> {
    private final IPlayerStats instance = new PlayerStatsImpl();

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == PlayerStatsCapability.PLAYER_STATS;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == PlayerStatsCapability.PLAYER_STATS) {
            return (T) instance;
        }
        return null;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        return ((PlayerStatsImpl) instance).serializeNBT();
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        ((PlayerStatsImpl) instance).deserializeNBT(nbt);
    }
}
