# Stats Mod - 1.12.2

Un mod completo de RPG para Minecraft 1.12.2 con un sistema de estadísticas hermoso y balanceado.

## 🎮 Características

### 🧬 Sistema de Razas (5 razas disponibles)
- **HUMAN** - Estadísticas base equilibradas
- **DWARF** - Altura 80%, Constitución +2, Fuerza +1, Minería x1.2
- **ELF** - Altura 110%, Destreza +2, Inteligencia +1, Velocidad de movimiento +10%
- **ORC** - Altura 120%, Fuerza +2, Constitución +2, XP extra al minar
- **LIZARD** - Equilibrado, Constitución +1, Destreza +1

### 📊 Sistema de Estadísticas
- **Constitución** - Aumenta vida máxima (+2 vida por punto)
- **Fuerza** - Aumenta daño de ataque (+0.5 daño por punto)
- **Destreza** - Aumenta velocidad de ataque y movimiento (+0.1 velocidad por punto)
- **Inteligencia** - Aumenta resistencia al daño (-5% daño por punto)
- **Suerte** - Base para futuros encantamientos especiales
- **Enfoque** - Base para precisión y críticos

### 🎨 HUD Hermoso en Primera Persona
- ❤️ **Vida** - Barra ROJA con icono de corazón
- 🍖 **Hambre** - Barra AZUL con icono de comida
- 🛡️ **Defensa** - Barra PLATEADA con icono de escudo
- 📊 **Nivel & XP** - Mostrados en color DORADO
- 💚 **Puntos Disponibles** - En color VERDE cuando hay puntos
- Información de RAZA actual
- **Bordes DORADOS** en toda la interfaz

### 📋 Pantalla de Estadísticas (Presiona "I")
- Ver toda la información de stats en detalle
- Mostrar bonificadores de raza en DORADO
- Estadísticas derivadas (Vida, Daño, Velocidad)
- Puntos disponibles para gastar

### 👁️ Vida de Mobs al Mirar
Cuando apuntas a un mob:
- Nombre del mob en DORADO
- Barra de vida ROJA
- HP actual/máximo
- Marco con bordes DORADOS

### ⚡ Sistema XP Especial
- Ganancia de XP al romper bloques (+5 XP)
- Ganancia de XP al matar mobs (XP del mob)
- Subida de nivel cada 100 * nivel XP
- +1 Punto de Habilidad por cada nivel
- Orbes XP rojos (diferentes a los verdes normales)

### 🎯 Selección de Raza al Entrar
- Interfaz de selección al ingresar por primera vez
- Visualización de bonificadores de cada raza
- Cambio de raza con comando `/setraza [raza]`

### ⚙️ Sistema de Combate
- Daño extra basado en Fuerza
- Reducción de daño basada en Armadura + Inteligencia
- Velocidad de ataque afectada por Destreza

### 🔨 Velocidad de Minería Especial
- Dwarves minan 20% más rápido
- Ganancia de XP extra por romper bloques
- Orcs ganan XP extra al minar

## 📖 Cómo Usar

### Instalación
1. Descarga Forge 1.12.2 desde [aquí](https://files.minecraftforge.net/)
2. Instala el cliente de Forge
3. Descarga el mod desde [GitHub](https://github.com/Katzume13/stats)
4. Coloca el JAR en la carpeta `mods`
5. ¡Juega!

### Comandos
```bash
# Cambiar raza
/setraza human    # human, dwarf, elf, orc, lizard
```

### Controles
- **I** - Abrir pantalla de estadísticas
- **Click derecho** en stat - Subir estadística (si tienes puntos)

## 🛠️ Compilación

```bash
# Descargar gradle y compilar
./gradlew build

# El JAR compilado estará en build/libs/
stats-1.0.0.jar
```

## 📁 Estructura del Proyecto

```
src/main/java/com/katzume/stats/
├── StatsMod.java              # Clase principal del mod
├── capability/                # Sistema de capabilities para guardar datos
│   ├── IPlayerStats.java
│   ├── PlayerStatsImpl.java
│   ├── PlayerStatsCapability.java
│   ├── PlayerStatsProvider.java
│   └── PlayerStatsStorage.java
├── race/                      # Sistema de razas
│   └── Race.java
├── event/                     # Manejadores de eventos
│   ├── KeyInputHandler.java
│   ├── XPEventHandler.java
│   ├── CombatEventHandler.java
│   ├── AttributeModifierHandler.java
│   ├── DataSyncHandler.java
│   └── RaceSpecialEventHandler.java
├── client/                    # Código de cliente
│   ├── RenderEventHandler.java
│   └── gui/
│       ├── StatsScreenGUI.java
│       └── RaceSelectionGUI.java
├── command/                   # Comandos
│   ├── SetRaceCommand.java
│   └── CommandRegistry.java
├── config/                    # Configuración
│   └── StatsConfig.java
└── util/                      # Utilidades
    └── StatsUtil.java
```

## 🎮 Tipss de Juego

1. **Elige tu raza sabiamente** - Cada raza tiene bonificadores únicos
2. **Distribuye tus puntos** - Aumenta los stats que más uses
3. **Sube de nivel matando mobs** - El XP se gana más rápido así
4. **Usa Inteligencia para tanquear** - Reduce daño recibido
5. **Fuerza para daño** - Golpea más fuerte
6. **Destreza para velocidad** - Muévete más rápido

## 🐛 Reportar Bugs

Si encuentras algún error, crea un issue en [GitHub Issues](https://github.com/Katzume13/stats/issues)

## 📝 Licencia

MIT License - Sientete libre de usar y modificar

## 👨‍💻 Autor

**Katzume13** - Desarrollo del mod

---

¡Disfruta el mod! Si te gusta, considera dejar una ⭐ en el repositorio.
