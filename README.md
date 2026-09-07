#  Stackable Items

A lightweight Minecraft mod that removes item stack limits, allowing you to stack any item up to whatever number you choose!

---

## 🚀 Features

* **Stack Everything**: Stack potions, totems of undying, buckets, stews, tools, and all other items up to your desired limit.
* **Custom Max Stack Size**: Freely set your maximum stack size (e.g. `64`, `100`, `1,000`, `64,000`).
* **Vanilla-Safe Storage (Anti-Loss)**: Items are saved safely using vanilla-compatible limits so items will never disappear if the mod is removed, and will fully restore if reinstalled.
* **Delta Tracking**: If you temporarily play in vanilla and use some items from a stack, the exact amount used is accurately deducted when the mod is reinstalled.
* **Exact Count Tooltip**: Hovering over large stacks shows the exact amount formatted with commas.
* **Safe Tool Stacking**: When a stacked tool breaks, the next one in the stack is immediately ready with full durability.

---

## 🎁More Features


* **Supercharged Bundles**: Bundle capacity scales with the maximum stack size. A single bundle can hold hundreds or thousands of mixed items, and can even store multiple weapons and tools!
* **Bulk Furnace Smelting**: Put thousands of ores and coal into a single furnace for continuous unattended smelting without complex hopper setups.
* **Machine-Gun Dispensers**: Load dispensers with thousands of arrows or fireworks per slot for virtually bottomless base defense.

---

## ⚠️ Risks & Considerations

* **Redstone Item Sorters**: Redstone Comparators calculate signal strength based on the percentage of a slot's capacity (`count / maxStackSize`). Traditional vanilla hopper sorters (which rely on 22 or 41 items) will not output sufficient signal strength with large stack sizes.
* **Mass Item Loss (Lava / Void / Explosions)**: Carrying massive stacks (e.g. 5,000 diamonds in one slot) carries high risk. Dying in the Void or lava destroys the entire stack at once.
* **Enchanted Tool Merging**: Items in the same stack share identical durability and enchantment data. Avoid stacking uniquely enchanted weapons or tools.

---

## 🚫 Warnings & What NOT to Do

> [!WARNING]
> **Do NOT deplete a fallback stack to 0 in Vanilla!**
> If you uninstall this mod and open your world in vanilla Minecraft, your stacks are temporarily capped at vanilla limits (e.g. 64) while keeping your reserve safe.
> - **Safe:** As long as at least **1 item remains** in the stack, your entire reserve restores automatically when the mod is reinstalled.
> - **Danger:** If you use all items until the stack reaches **0**, vanilla deletes the empty slot and the remaining reserve will be lost.

> [!IMPORTANT]
> **Do not combine different enchanted gear into a single stack.** Stacking forces items to share the same components.

---

## ⌨️ Commands & Configuration

| Command | Description |
| :--- | :--- |
| `/stackable set <number>` | Set maximum stack size (e.g. `/stackable set 1000`) |
| `/stackable reset` | Reset maximum stack size back to default (64) |
| `/stackable reload` | Reload configuration from `config/stackable_items.json` |

---

## 💡 Troubleshooting & Common Scenarios

#### 1. Redstone Item Sorter / Hopper Filter Not Working
* **Problem**: Automatic storage systems using Redstone Comparators stop filtering items properly.
* **Cause**: Comparators measure fullness based on stack capacity (`count / maxStackSize`). With large stack limits (e.g. 1,000), 22 or 41 items output signal strength `0` instead of `1` or `2`.
* **Solution**: Either adjust comparator wiring, use `/stackable reset` to set the stack size back to 64 for sorting operations, or use allay/water-stream sorting mechanics.

#### 2. Playing the World in Vanilla (Mod Uninstalled)
* **Problem**: You removed the mod and your items appear as normal vanilla stacks (e.g. 64 dirt instead of 500).
* **Cause**: Our hybrid storage fallback protects your items by capping the visible count to vanilla limits while keeping your reserve safely stored in vanilla custom data.
* **Solution**: You can play and use items normally, but **leave at least 1 item** in the slot (do not consume it to 0). When you reinstall the mod, your full reserve will automatically restore, with any items you used in vanilla accurately deducted.

#### 3. Manual Config Edit Not Updating In-Game
* **Problem**: You modified `config/stackable_items.json` in a text editor, but the game still uses the previous stack size.
* **Cause**: The game caches settings in memory and doesn't re-read the disk file automatically while running.
* **Solution**: Type `/stackable reload` in-game or in the server console to immediately apply your file changes without restarting.

#### 4. Merging Enchanted Weapons or Tools
* **Problem**: Stacking tools merges their stats into a single shared set of components.
* **Cause**: Minecraft item slots can only store one set of durability and enchantment data per stack.
* **Solution**: Store unique, high-tier enchanted gear in individual slots or chests. If stacked, right-click to separate them.

#### 5. Lowering the Max Stack Size (e.g. from 1,000 to 64)
* **Problem**: You lower the stack size limit, and you worry existing large stacks will vanish.
* **Cause**: Lowering the limit does not retroactively delete or destroy existing items.
* **Solution**: Existing oversized stacks in chests and inventories remain completely safe. You cannot add new items to an oversized stack, and moving parts of it will split the stack according to the new lower limit.

#### 6. Multiplayer / Dedicated Server Desync
* **Problem**: Stack limits don't apply when playing on a multiplayer server, or items bounce back when dragged in inventory.
* **Cause**: In multiplayer, the dedicated server dictates inventory logic. Client-side changes have no effect if the server does not have matching settings.
* **Solution**: The server must have the mod installed. The server admin must configure the limit on the server using `/stackable set <number>` or by editing the server's `config/stackable_items.json` followed by `/stackable reload`.

#### 7. Splitting Fallback Stacks in Vanilla
* **Problem**: You uninstalled the mod, divided a 64 fallback stack into two stacks of 32 in vanilla, and then reinstalled the mod.
* **Cause**: Vanilla duplicates custom data components when splitting stacks, which may cause reserve discrepancies.
* **Solution**: While playing without the mod in vanilla, keep reserve stacks intact in their original slots rather than splitting them into multiple slots until the mod is reinstalled.

#### 8. Auto-Crafter Jamming with Large Stacks
* **Problem**: The Crafter block receives too many items of one type in a single slot, blocking multi-ingredient recipes.
* **Cause**: Hoppers feed large stacks into the first available slot until full before moving to other slots.
* **Solution**: Pre-populate all required recipe slots with placeholder items or single ingredients before enabling continuous hopper feeding.

#### 9. Dropped Items & Despawn Timer
* **Problem**: Dropping or dying with tens of thousands of items in one spot.
* **Cause**: While the mod bundles all items into a single ground entity (preventing entity lag), ground items still follow Minecraft's standard 5-minute despawn timer once the chunk is loaded.
* **Solution**: Retrieve your dropped items within 5 minutes of loading the chunk. Store precious items in Ender Chests before taking high-risk journeys.


---

## 📥 Installation

1. Install **[Fabric Loader](https://fabricmc.net/)** and **[Fabric API](https://modrinth.com/mod/fabric-api)** for Minecraft 26.2 or another Fabric-compatible version.
2. Place the mod `.jar` into your `.minecraft/mods` directory.
3. *(Optional)* Install **[Mod Menu](https://modrinth.com/mod/modmenu)** for GUI configuration.
4. Launch the game!

---

## 📄 License

This project is licensed under the [MIT License](LICENSE).
