package com.katzume.stats.capability;

import net.minecraft.nbt.NBTTagCompound;
import com.katzume.stats.race.Race;

public class PlayerStatsImpl implements IPlayerStats {
    private Race race = Race.HUMAN;
    
    private int constitution = 1;
    private int strength = 0;
    private int dexterity = 0;
    private int intelligence = 0;
    private int luck = 0;
    private int focus = 0;
    
    private int statsXP = 0;
    private int level = 1;
    private int skillPoints = 0;
    
    private boolean dirty = false;

    @Override
    public Race getRace() {
        return race;
    }

    @Override
    public void setRace(Race race) {
        this.race = race != null ? race : Race.HUMAN;
        markDirty();
    }

    @Override
    public int getConstitution() {
        return constitution;
    }

    @Override
    public void setConstitution(int value) {
        this.constitution = Math.max(1, value);
        markDirty();
    }

    @Override
    public int getStrength() {
        return strength;
    }

    @Override
    public void setStrength(int value) {
        this.strength = Math.max(0, value);
        markDirty();
    }

    @Override
    public int getDexterity() {
        return dexterity;
    }

    @Override
    public void setDexterity(int value) {
        this.dexterity = Math.max(0, value);
        markDirty();
    }

    @Override
    public int getIntelligence() {
        return intelligence;
    }

    @Override
    public void setIntelligence(int value) {
        this.intelligence = Math.max(0, value);
        markDirty();
    }

    @Override
    public int getLuck() {
        return luck;
    }

    @Override
    public void setLuck(int value) {
        this.luck = Math.max(0, value);
        markDirty();
    }

    @Override
    public int getFocus() {
        return focus;
    }

    @Override
    public void setFocus(int value) {
        this.focus = Math.max(0, value);
        markDirty();
    }

    @Override
    public int getTotalConstitution() {
        return constitution + race.getConstitutionBonus();
    }

    @Override
    public int getTotalStrength() {
        return strength + race.getStrengthBonus();
    }

    @Override
    public int getTotalDexterity() {
        return dexterity + race.getDexterityBonus();
    }

    @Override
    public int getTotalIntelligence() {
        return intelligence + race.getIntelligenceBonus();
    }

    @Override
    public int getTotalLuck() {
        return luck + race.getLuckBonus();
    }

    @Override
    public int getTotalFocus() {
        return focus + race.getFocusBonus();
    }

    @Override
    public int getMaxHealth() {
        // 2 hearts base = 4 half-hearts, +2 per constitution point
        return 4 + (getTotalConstitution() * 2);
    }

    @Override
    public int getArmorValue() {
        return 0;
    }

    @Override
    public float getAttackDamage() {
        return 1.0f + (getTotalStrength() * 0.5f);
    }

    @Override
    public float getAttackSpeed() {
        return 4.0f + (getTotalDexterity() * 0.1f);
    }

    @Override
    public float getMiningSpeed() {
        return race.getMiningSpeedBonus() + (getTotalStrength() * 0.1f);
    }

    @Override
    public int getStatsXP() {
        return statsXP;
    }

    @Override
    public void addStatsXP(int amount) {
        this.statsXP += amount;
        checkLevelUp();
        markDirty();
    }

    @Override
    public void setStatsXP(int amount) {
        this.statsXP = Math.max(0, amount);
        markDirty();
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public void setLevel(int level) {
        this.level = Math.max(1, level);
        markDirty();
    }

    @Override
    public int getSkillPoints() {
        return skillPoints;
    }

    @Override
    public void addSkillPoints(int amount) {
        this.skillPoints += amount;
        markDirty();
    }

    @Override
    public void spendSkillPoints(int amount) {
        this.skillPoints = Math.max(0, skillPoints - amount);
        markDirty();
    }

    @Override
    public void markDirty() {
        this.dirty = true;
    }

    private void checkLevelUp() {
        int xpRequired = level * 100;
        if (statsXP >= xpRequired) {
            statsXP -= xpRequired;
            level++;
            skillPoints += 1;
        }
    }

    public NBTTagCompound serializeNBT() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("race", race.name());
        tag.setInteger("constitution", constitution);
        tag.setInteger("strength", strength);
        tag.setInteger("dexterity", dexterity);
        tag.setInteger("intelligence", intelligence);
        tag.setInteger("luck", luck);
        tag.setInteger("focus", focus);
        tag.setInteger("statsXP", statsXP);
        tag.setInteger("level", level);
        tag.setInteger("skillPoints", skillPoints);
        return tag;
    }

    public void deserializeNBT(NBTTagCompound tag) {
        if (tag.hasKey("race")) {
            this.race = Race.fromString(tag.getString("race"));
        }
        this.constitution = tag.getInteger("constitution");
        this.strength = tag.getInteger("strength");
        this.dexterity = tag.getInteger("dexterity");
        this.intelligence = tag.getInteger("intelligence");
        this.luck = tag.getInteger("luck");
        this.focus = tag.getInteger("focus");
        this.statsXP = tag.getInteger("statsXP");
        this.level = tag.getInteger("level");
        this.skillPoints = tag.getInteger("skillPoints");
    }
}
