# No Limit Enchant

A lightweight Fabric mod that removes anvil limits and allows upgrading enchantment levels.

## Features

- **No "Too Expensive!"**: Removes the vanilla 40-level cost limit on anvils.
- **Higher Enchantment Levels**: Combine identical items/books to upgrade beyond vanilla limits (default: 255).
- **Anvil Penalty Toggle**:
  - `None`: 0 penalty; items always cost base enchantment price.
  - `Unlimited`: Vanilla exponential penalty.
- **Level Formatting**: Display levels as Roman numerals or numbers.
- **Command Support**: Allows `/enchant` to exceed vanilla max levels.
- **In-Game Config**: Configurable via Mod Menu.

## Installation

1. Requires [Fabric Loader](https://fabricmc.net/) and [Fabric API](https://modrinth.com/mod/fabric-api).
2. Place the `.jar` file into `.minecraft/mods`.
3. *(Optional)* Install [Mod Menu](https://modrinth.com/mod/modmenu) for in-game configuration.

## Configuration

Configurable in-game via **Mod Menu** or by editing `.minecraft/config/no_limit_enchant.json`:

```json
{
  "removeAnvilPenalty": true,
  "maxEnchantmentLevel": 255,
  "levelFormat": "ROMAN"
}
```

### Options

- **`removeAnvilPenalty`** (`true` / `false`):
  - `true` (*None*): 0 repair penalty; upgrading and repairing always stay cheap.
  - `false` (*Unlimited*): Vanilla exponential penalty ($2^n - 1$).
- **`maxEnchantmentLevel`** (number, default: `255`):
  - Maximum enchantment level limit when combining items in anvils and using `/enchant`.
- **`levelFormat`** (`"ROMAN"` / `"NUMBER"`):
  - `"ROMAN"`: Displays levels as Roman numerals (e.g. `Sharpness X`, `Sharpness CCLV`).
  - `"NUMBER"`: Displays levels as standard numbers (e.g. `Sharpness 10`, `Sharpness 255`).
