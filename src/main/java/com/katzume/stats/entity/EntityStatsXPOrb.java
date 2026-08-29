package com.katzume.stats.entity;

import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.world.World;
import com.katzume.stats.StatsMod;

public class EntityStatsXPOrb extends EntityXPOrb {
    public EntityStatsXPOrb(World worldIn, double x, double y, double z, int xpValue) {
        super(worldIn, x, y, z, xpValue);
    }
    
    public EntityStatsXPOrb(World worldIn) {
        super(worldIn);
    }
}
