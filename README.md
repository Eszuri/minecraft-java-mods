# 🔮 No Limit Enchant

A lightweight Minecraft Fabric mod that removes the anvil **"Too Expensive!"** limit and allows upgrading enchantment levels without limits.

---

## ✨ Features

- **🚫 No More "Too Expensive!"**: Never get blocked by the vanilla 40-level cost limit. You can always repair and add enchantments to your gear.
- **📈 Infinite Enchantment Upgrading**: Combine two items/books of the same level to upgrade beyond vanilla maximums (e.g., Efficiency V + Efficiency V = VI, VI + VI = VII, up to 255+).
- **🛡️ Unlimited Level Serialization (> 255)**: Safely saves and loads high enchantment levels in save files and NBT components without errors.
- **🛑 Zero Anvil Penalty (Configurable)**: Toggle between 0 penalty (items always stay like new) or vanilla exponential penalty without limits.
- **📖 Clean Tooltip Formatting**: Nicely renders high levels as Roman numerals (up to 3999) or clean numbers instead of raw `enchantment.level.11`.
- **⚡ Command Overleveling**: Allows `/enchant` to apply high levels directly.
- **🖥️ In-Game Configuration**: Interactive Mod Menu GUI screen.

---

## 📥 Installation

1. Install **[Fabric Loader](https://fabricmc.net/)** and **[Fabric API](https://modrinth.com/mod/fabric-api)** for Minecraft 26.3 or another Fabric-compatible version.
2. Place the mod `.jar` into your `.minecraft/mods` directory.
3. *(Optional)* Install **[Mod Menu](https://modrinth.com/mod/modmenu)** for GUI configuration.
4. Launch the game!

---

## ⚙️ Configuration

Configuration is saved in `.minecraft/config/no_limit_enchant.json`:

```json
{
  "removeTooExpensive": true,
  "removeAnvilPenalty": true,
  "allowInfiniteEnchantLevels": true,
  "maxEnchantmentLevel": 255,
  "allowCommandOverlevel": true,
  "levelFormat": "ROMAN"
}
```
