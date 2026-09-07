# Easily Critical

A lightweight Minecraft Fabric mod that reworks Mace combat mechanics, customizes Wind Burst enchantment triggers, enables standing critical attacks, and eliminates item and weapon cooldowns.

---

## 🚀 Features

* **Customizable Smash Attack Trigger**: Adjust the required fall distance to trigger Mace smash attacks (`1.5 Blocks`, `1.0 Block`, or `0 Blocks` to smash directly from the ground without jumping).
* **Wind Burst Launch Trigger**: Customize Wind Burst activation height (`1.5 Blocks`, `1.0 Block`, or `0 Blocks` for immediate launching on hit).
* **Wind Burst Fall Damage Immunity**: Grants complete fall damage immunity when landing after being propelled into the air by Wind Burst.
* **Easily Critical Damage**: Perform critical hits (+50% damage and critical particles) with weapons and tools while standing still on the ground without jumping.
* **Dynamic Mace Priority**: When Smash Trigger is set to `0 Blocks`, Mace attacks automatically prioritize Smash Attacks while other weapons maintain standing critical hits.
* **No Cooldowns**: Removes cooldowns across all items (Ender Pearls, Wind Charges, Chorus Fruit, Shields, etc.) and eliminates attack swing delay for instant 100% attack charge.

---

## ⚙️ Configuration

Settings are stored and can be edited in `config/easily_critical.json`:

| Option | Supported Values | Default | Description |
| :--- | :--- | :---: | :--- |
| `smashTrigger` | `"BLOCKS_1_5"`, `"BLOCKS_1_0"`, `"BLOCKS_0"` | `"BLOCKS_1_5"` | Minimum fall distance to trigger Mace smash attack. |
| `windBurstTrigger` | `"BLOCKS_1_5"`, `"BLOCKS_1_0"`, `"BLOCKS_0"` | `"BLOCKS_1_5"` | Minimum fall distance to trigger Wind Burst explosion. |
| `fallDamageMace` | `true` / `false` | `true` | Fall damage immunity after Wind Burst launch. |
| `easilyCriticalDamage` | `true` / `false` | `true` | Critical hits while standing still (no jump required). |
| `noCooldown` | `true` / `false` | `true` | Eliminates cooldowns from items and weapon swings. |


---

## 📥 Installation

1. Install **[Fabric Loader](https://fabricmc.net/)** and **[Fabric API](https://modrinth.com/mod/fabric-api)** for Minecraft 26.2 or another Fabric-compatible version.
2. Place the mod `.jar` into your `.minecraft/mods` directory.
3. *(Optional)* Install **[Mod Menu](https://modrinth.com/mod/modmenu)** for GUI configuration.
4. Launch the game!

---

## 📄 License

This project is licensed under the [MIT License](LICENSE).
