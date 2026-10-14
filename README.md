# Reflective Shield

## Description

A pure, zero-config Fabric mod for Minecraft 26.3 that reflects incoming attacks back to the attacker whenever you block with a shield.

- **360° Full Body Shield Coverage**: The shield protects your entire body from all directions (front, sides, behind, above, below). When raising your shield, you cannot be attacked from behind or flank angles.
- **Melee Counter**: When an enemy (zombie, spider, player, etc.) hits your shield with a melee attack from any angle, 100% of the damage received is dealt right back to themselves (`damage masuk == damage pembalik`). The attacker experiences standard player-equivalent knockback impulse and recoil distance away from you.
- **Projectile Deflection**: Incoming projectiles from any angle (Skeleton arrows, Drowned tridents, Blaze / Ghast fireballs, Shulker bullets, Breeze wind charges, etc.) are immediately redirected towards the original shooter with the exact same incoming velocity (`kecepatan pembalik == kecepatan masuk`), attributing the projectile's ownership to you so it damages the attacker upon impact.
- **Authentic Vanilla Sound**: Produces reliable vanilla shield impact sound on every single attack received (melee, projectile, explosion).
- **Active on Normal Block**: Counter triggers whenever your shield is raised—no complex timing or perfect parry window required.
- **Zero Configurations**: Zero configuration files and zero in-game settings. Everything works out of the box with vanilla balance and clean gameplay.

## Installation

1. Requires [Fabric Loader](https://fabricmc.net/) and [Fabric API](https://modrinth.com/mod/fabric-api).
2. Place `reflective_shield-1.0.0+26.3.jar` into your `.minecraft/mods` folder.
