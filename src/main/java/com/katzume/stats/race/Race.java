package com.katzume.stats.race;

import net.minecraft.util.ResourceLocation;

public enum Race {
    HUMAN("human", 1.0f, 1.0f, 0, 0, 0, 0, 0, 0, 1.0f),
    DWARF("dwarf", 0.8f, 1.2f, 2, 1, 0, -1, 1, 0, 1.2f), // Mining speed bonus
    ELF("elf", 1.1f, 0.9f, -1, 0, 2, 1, 0, 1, 0.9f),      // Dexterity bonus
    ORC("orc", 1.2f, 1.1f, 2, 2, -1, -1, 0, 0, 1.1f),      // Strength bonus
    LIZARD("lizard", 1.0f, 1.0f, 1, 0, 1, 0, 0, 0, 1.0f);   // Has tail
    
    private String id;
    private float heightMultiplier;      // Height scale
    private float speedMultiplier;       // Movement speed
    private int constitutionBonus;
    private int strengthBonus;
    private int dexterityBonus;
    private int intelligenceBonus;
    private int luckBonus;
    private int focusBonus;
    private float miningSpeedBonus;      // Mining speed multiplier
    
    Race(String id, float height, float speed, int con, int str, int dex, int intel, int luck, int focus, float mining) {
        this.id = id;
        this.heightMultiplier = height;
        this.speedMultiplier = speed;
        this.constitutionBonus = con;
        this.strengthBonus = str;
        this.dexterityBonus = dex;
        this.intelligenceBonus = intel;
        this.luckBonus = luck;
        this.focusBonus = focus;
        this.miningSpeedBonus = mining;
    }
    
    public String getId() {
        return id;
    }
    
    public float getHeightMultiplier() {
        return heightMultiplier;
    }
    
    public float getSpeedMultiplier() {
        return speedMultiplier;
    }
    
    public int getConstitutionBonus() {
        return constitutionBonus;
    }
    
    public int getStrengthBonus() {
        return strengthBonus;
    }
    
    public int getDexterityBonus() {
        return dexterityBonus;
    }
    
    public int getIntelligenceBonus() {
        return intelligenceBonus;
    }
    
    public int getLuckBonus() {
        return luckBonus;
    }
    
    public int getFocusBonus() {
        return focusBonus;
    }
    
    public float getMiningSpeedBonus() {
        return miningSpeedBonus;
    }
    
    public static Race fromString(String name) {
        try {
            return Race.valueOf(name.toUpperCase());
        } catch (IllegalArgumentException e) {
            return HUMAN;
        }
    }
}
