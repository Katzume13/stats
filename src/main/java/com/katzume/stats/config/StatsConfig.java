package com.katzume.stats.config;

public class StatsConfig {
    // XP Configuration
    public static final int XP_PER_BLOCK_BREAK = 5;
    public static final int XP_PER_MOB_KILL = 25;
    
    // Stat Bonuses
    public static final float STRENGTH_DAMAGE_BONUS = 0.5f;      // Daño por punto de Fuerza
    public static final float DEXTERITY_SPEED_BONUS = 0.1f;      // Velocidad de ataque por punto
    public static final int CONSTITUTION_HEALTH_BONUS = 2;       // Vida por punto de Constitución
    public static final float INTELLIGENCE_DEFENSE_BONUS = 0.05f; // Reducción de daño por Inteligencia
    
    // Level Configuration
    public static final int BASE_XP_FOR_LEVEL = 100; // XP requerido para nivel 1
    public static final float XP_MULTIPLIER_PER_LEVEL = 1.0f; // Multiplicador por cada nivel
    
    // Default Health
    public static final int DEFAULT_MAX_HEALTH = 4; // 2 corazones
}
