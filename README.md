# No Limit Enchant

## Description

A lightweight Fabric mod that removes vanilla anvil restrictions and unlocks higher enchantment levels. It eliminates the 40-level "Too Expensive!" limit, allows upgrading enchantments beyond vanilla maximums (up to level 255) by combining matching items or books, and provides options to customize anvil repair penalties and level display formats.

## Installation

1. Requires [Fabric Loader](https://fabricmc.net/) and [Fabric API](https://modrinth.com/mod/fabric-api).
2. Place the `.jar` file into `.minecraft/mods`.
3. *(Optional)* Install [Mod Menu](https://modrinth.com/mod/modmenu) for in-game configuration.

## Configuration

Configurable in-game via **Mod Menu** or by editing `.minecraft/config/no_limit_enchant.json`:

```json
{
  "anvilPenalty": true,
  "levelFormat": "ROMAN",
  "maxLevel": 255
}
```

### Options

- **`anvilPenalty`** (`true` / `false`):
  - `true` (*None*): 0 repair penalty; upgrading and repairing always stay cheap.
  - `false` (*Unlimited*): Vanilla exponential penalty ($2^n - 1$).
- **`levelFormat`** (`"ROMAN"` / `"NUMBER"`):
  - `"ROMAN"`: Displays levels as Roman numerals (e.g. `Sharpness X`, `Sharpness CCLV`).
  - `"NUMBER"`: Displays levels as standard numbers (e.g. `Sharpness 10`, `Sharpness 255`).
- **`maxLevel`** (number, default: `255`):
  - Maximum enchantment level limit when combining items in anvils and using `/enchant`.
