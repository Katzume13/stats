package com.katzume.stats.capability;

import com.katzume.stats.race.Race;

public interface IPlayerStats {
    // Race
    Race getRace();
    void setRace(Race race);
    
    // Base Stats
    int getConstitution();
    void setConstitution(int value);
    
    int getStrength();
    void setStrength(int value);
    
    int getDexterity();
    void setDexterity(int value);
    
    int getIntelligence();
    void setIntelligence(int value);
    
    int getLuck();
    void setLuck(int value);
    
    int getFocus();
    void setFocus(int value);
    
    // Total Stats (with race bonuses)
    int getTotalConstitution();
    int getTotalStrength();
    int getTotalDexterity();
    int getTotalIntelligence();
    int getTotalLuck();
    int getTotalFocus();
    
    // Derived Stats
    int getMaxHealth();
    int getArmorValue();
    float getAttackDamage();
    float getAttackSpeed();
    float getMiningSpeed();
    
    // XP System
    int getStatsXP();
    void addStatsXP(int amount);
    void setStatsXP(int amount);
    
    int getLevel();
    void setLevel(int level);
    
    int getSkillPoints();
    void addSkillPoints(int amount);
    void spendSkillPoints(int amount);
    
    void markDirty();
}
