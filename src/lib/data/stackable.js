export const stackableData = {
    meta: {
        title: 'Stackable Items',
        version: 'Supports Mod for Version 26.2',
        description: 'mod that removes item stack limits and allows any item to stack up to your chosen limit',
        github: 'https://github.com/Eszuri/'
    },
    links: [
        {href: '#screenshots', label: 'Screenshots'},
        {href: '#features', label: 'Features'},
        {href: '#mechanics', label: 'Comparisons'},
        {href: '#commands', label: 'Commands'},
        {href: '#troubleshooting', label: 'Troubleshooting'},
        {href: '#installation', label: 'Installation'}
    ],
    features: [
        {title: 'Stack Everything', desc: 'Potions, totems of undying, buckets, soups, stews, saddles, armor, weapons, and tools stack normally.'},
        {title: 'Customizable Stack Limit', desc: 'Adjust the stack size to any integer from 1 up to 999,999 or more (default: 64).'},
        {title: 'Vanilla-Safe Storage', desc: 'Items saved to disk are capped to vanilla bounds while surplus is preserved in vanilla custom data, preventing data loss if the mod is removed.'},
        {title: 'Compact GUI Rendering', desc: 'Numbers adapt automatically: font scales down for 3 digits, switches to compact notation (1k, 1M) for larger values, and shows full count on hover.'},
        {title: 'Safe Tool Stacking', desc: 'Stacked damageable items decrement count upon breaking and provide the next item with fresh durability.'}
    ],
    comparisons: [
        {name: 'Bundle', vanilla: 'Caps at 64 items total (mixed item weight).', mod: 'Weight formula scales with max stack size; holds thousands of mixed items or multiple tools.'},
        {name: 'Totem of Undying', vanilla: 'Non-stackable; off-hand slot empties on trigger.', mod: 'Stacks in off-hand; triggers consume one totem while keeping remainder equipped.'},
        {name: 'Furnace / Smoker', vanilla: 'Inputs limited to 64 items per slot.', mod: 'Accepts thousands of fuel and raw materials for continuous smelting.'},
        {name: 'Dispenser / Dropper', vanilla: 'Max 576 arrows or projectiles per container.', mod: 'Can hold thousands of projectiles per slot for extended defense systems.'},
        {name: 'Soups, Stews & Milk', vanilla: 'Non-stackable (1 item per inventory slot).', mod: 'Stackable in inventory and chests like normal items.'}
    ],
    commands: [
        {tag: '/stackable set <number>', cmd: '/stackable set 1000', desc: 'Sets the maximum stack size (e.g. /stackable set 1000).'},
        {tag: '/stackable reset', cmd: '/stackable reset', desc: 'Resets maximum stack size back to the default vanilla limit (64).'},
        {tag: '/stackable reload', cmd: '/stackable reload', desc: 'Reloads config/stackable_items.json from disk without restarting.'}
    ],
    troubleshooting: [
        {title: '1. Redstone item sorters or hopper filters not functioning', cause: 'Redstone Comparators measure fullness based on stack capacity (count / maxStackSize). When stack limit is 1,000, 22 items output signal strength 0 instead of 1 or 2.', solution: 'Use /stackable reset to set the limit back to 64 when running standard sorting systems, or use water-stream / allay sorting.'},
        {title: '2. Playing in vanilla after removing the mod', cause: 'The fallback system caps visible counts to safe vanilla boundaries so vanilla does not discard oversize data.', solution: 'Leave at least 1 item in the slot. When the mod is reinstalled, your full count restores with vanilla consumption accurately subtracted.'},
        {title: '3. Manual config file edits not updating in-game', cause: 'The game caches settings in memory and does not poll the file while running.', solution: 'Run /stackable reload in chat or console to apply changes immediately without a restart.'},
        {title: '4. Stacking weapons or tools with different enchantments', cause: 'A single item slot can only store one set of components (durability, enchantments, custom names).', solution: 'Store unique or high-tier enchanted gear in separate slots. Right-click to extract items individually if combined.'},
        {title: '5. Lowering the maximum stack size (e.g. 1,000 down to 64)', cause: 'Changing the config limit does not delete existing stored items.', solution: 'Existing oversized stacks in chests remain intact. Moving portions of them will split according to the new lower limit.'},
        {title: '6. Multiplayer / Dedicated Server Desync', cause: 'Dedicated servers enforce inventory limits server-side. Client-only config changes have no effect.', solution: 'Install the mod on the server and configure the limit using /stackable set <number> on the server console.'},
        {title: '7. Splitting fallback reserve stacks in vanilla', cause: 'Vanilla duplicates item components when splitting stacks across multiple slots.', solution: 'Keep reserve stacks in their original slots while playing in vanilla until the mod is re-added.'},
        {title: '8. Auto-Crafter jamming with large stacks', cause: 'Hoppers fill the first open slot to capacity before filling other recipe slots.', solution: 'Pre-populate all required crafting recipe slots before connecting continuous hopper feeds.'},
        {title: '9. Ground item entities and the 5-minute despawn timer', cause: "While the mod groups dropped items into a single entity to prevent lag, Minecraft's 5-minute despawn timer still applies once the chunk is loaded.", solution: 'Retrieve dropped items within 5 minutes of loading the chunk.'}
    ],
    steps: [
        {index: 'Step 01', title: 'Install Fabric', desc: 'Download and install Fabric Loader and Fabric API for Minecraft 26.2 or another Fabric-compatible version.'},
        {index: 'Step 02', title: 'Download Mod', desc: 'Download the .jar file from the bottom of this page.'},
        {index: 'Step 03', title: 'Place in Mods Folder', desc: "Copy the file into .minecraft/mods (or your server's mods/ directory) and start the game."}
    ]
};
