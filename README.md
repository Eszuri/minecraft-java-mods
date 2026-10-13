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

Saved at `.minecraft/config/no_limit_enchant.json`:

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
