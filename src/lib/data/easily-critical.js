export const easilyCriticalData = {
    meta: {
        title: 'Easily Critical',
        version: 'Supports Mod for Version 26.2',
        description: 'mod for configurable Mace smash triggers, standing critical hits, fall damage protection, and zero cooldowns.',
        github: 'https://github.com/Eszuri/'
    },
    links: [
        {href: '#features', label: 'Features'},
        {href: '#screenshots', label: 'Screenshots'},
        {href: '#config', label: 'Configuration'},
        {href: '#troubleshooting', label: 'Troubleshooting'},
        {href: '#installation', label: 'Installation'}
    ],
    features: [
        {title: 'Smash Attack Trigger', desc: 'Customize the required fall distance to trigger a Mace smash attack: 1.5 blocks (default vanilla), 1.0 block (standard jump), or 0 blocks (standing on the ground without jumping).'},
        {title: 'Wind Burst Trigger', desc: 'Customize the activation height for the Wind Burst enchantment: 1.5 blocks (default), 1.0 block, or 0 blocks for immediate burst on hit from the ground.'},
        {title: 'Easily Critical Damage', desc: 'Enables critical hits (+50% damage and particles) on all weapons while standing still without jumping. In 0 Blocks mode on Mace, critical hits are automatically replaced by Smash Attacks.'},
        {title: 'No Cooldown', desc: 'Removes item cooldowns (Ender Pearl, Chorus Fruit, Shield, Wind Charge) and attack swing delays for instant full-strength hits.'},
        {title: 'Fall Damage Protection', desc: 'Grants fall damage immunity specifically when launched into the air by Wind Burst, keeping high-mobility combat safe.'},
    ],
    configParams: [
        {name: 'smashTrigger', values: '"BLOCKS_1_5", "BLOCKS_1_0", "BLOCKS_0"', def: '"BLOCKS_1_5"', desc: 'Mace smash attack trigger distance.'},
        {name: 'windBurstTrigger', values: '"BLOCKS_1_5", "BLOCKS_1_0", "BLOCKS_0"', def: '"BLOCKS_1_5"', desc: 'Wind Burst explosion trigger distance.'},
        {name: 'fallDamageMace', values: 'true / false', def: 'true', desc: 'Fall damage protection when launched by Wind Burst.'},
        {name: 'easilyCriticalDamage', values: 'true / false', def: 'true', desc: 'Critical attack without jumping.'},
        {name: 'noCooldown', values: 'true / false', def: 'true', desc: 'Removes item cooldowns and attack swing delays.'},
    ],
    troubleshooting: [
        {title: '1. Smash attacks not triggering while standing', cause: 'The configuration is currently set to the 1.5 blocks vanilla default.', solution: 'Open Mod Menu settings and select 0 Blocks, or set "smashTrigger": "BLOCKS_0" in config/easily_critical.json.'},
        {title: '2. Fall damage still occurring from high cliff drops', cause: 'Fall protection is specialized for upward launches caused by Wind Burst.', solution: 'Fall damage immunity protects you when launched into the air by Wind Burst. For ordinary high cliff falls, hit an entity with a Mace smash attack before landing.'},
        {title: '3. Attack speed or item delay feels unchanged', cause: 'The noCooldown option may be disabled or another mod is overriding weapon attack attributes.', solution: 'Ensure noCooldown is set to true in your configuration and restart the game.'},
    ],
    steps: [
        {index: 'Step 01', title: 'Install Fabric', desc: 'Download and install Fabric Loader and Fabric API for Minecraft 26.2 or another Fabric-compatible version.'},
        {index: 'Step 02', title: 'Download Mod', desc: 'Download the .jar file from the bottom of this page.'},
        {index: 'Step 03', title: 'Place in Mods Folder', desc: "Copy the file into .minecraft/mods (or your server's mods/ directory) and start the game."}
    ]
};
