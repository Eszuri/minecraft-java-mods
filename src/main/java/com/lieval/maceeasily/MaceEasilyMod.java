package com.lieval.maceeasily;

import com.lieval.maceeasily.config.MaceEasilyConfig;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class MaceEasilyMod implements ModInitializer {
    public static final String MOD_ID = "easily_critical";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static MaceEasilyConfig CONFIG;

    private static class WindBurstState {
        long launchTime;
        long landingTime;

        WindBurstState(long launchTime) {
            this.launchTime = launchTime;
            this.landingTime = 0;
        }
    }

    private static final Map<UUID, WindBurstState> WIND_BURST_STATES = new ConcurrentHashMap<>();

    @Override
    public void onInitialize() {
        CONFIG = MaceEasilyConfig.load();
        LOGGER.info("[Easily Critical] Mod initialized. Smash Trigger: {}, Wind Burst Trigger: {}", CONFIG.smashTrigger, CONFIG.windBurstTrigger);
    }

    public static void onWindBurstTriggered(LivingEntity living) {
        if (living != null) {
            long now = System.currentTimeMillis();
            WIND_BURST_STATES.put(living.getUUID(), new WindBurstState(now));
            living.resetFallDistance();
        }
    }

    public static boolean isWindBurstProtected(UUID uuid) {
        if (uuid == null) return false;
        if (CONFIG != null && !CONFIG.fallDamageMace) {
            return false;
        }
        WindBurstState state = WIND_BURST_STATES.get(uuid);
        if (state != null) {
            long now = System.currentTimeMillis();
            // Jika sudah landing, berikan grace period 600ms agar semua paket gerakan selesai diproses tanpa damage
            if (state.landingTime > 0) {
                if (now - state.landingTime < 600) {
                    return true;
                } else {
                    WIND_BURST_STATES.remove(uuid);
                    return false;
                }
            }
            // Perlindungan selama di udara aktif hingga 15 detik dari pemicu Wind Burst terakhir
            if (now - state.launchTime < 15000) {
                return true;
            } else {
                WIND_BURST_STATES.remove(uuid);
            }
        }
        return false;
    }

    // Alias untuk kompatibilitas
    public static boolean isWindBurstFlying(UUID uuid) {
        return isWindBurstProtected(uuid);
    }

    public static void clearWindBurstFlying(UUID uuid) {
        if (uuid != null) {
            WIND_BURST_STATES.remove(uuid);
        }
    }

    public static void onPlayerFallDamageChecked(Player player, double fallDistance) {
        if (player == null) return;
        UUID uuid = player.getUUID();
        WindBurstState state = WIND_BURST_STATES.get(uuid);
        if (state != null) {
            long now = System.currentTimeMillis();
            // Hanya tandai landing jika sudah lebih dari 400ms sejak lontaran (agar fase awal 0 blok / takeoff tidak ter-trigger)
            if (now - state.launchTime > 400) {
                if (state.landingTime == 0) {
                    state.landingTime = now;
                }
            }
        }
    }

    public static boolean hasWindBurst(ItemStack stack) {
        if (stack == null || stack.isEmpty()) return false;
        ItemEnchantments enchants = stack.get(DataComponents.ENCHANTMENTS);
        if (enchants != null && !enchants.isEmpty()) {
            for (Holder<Enchantment> holder : enchants.keySet()) {
                if (holder.is(Enchantments.WIND_BURST)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean shouldTriggerWindBurst(LivingEntity living) {
        if (CONFIG == null) {
            CONFIG = MaceEasilyConfig.load();
        }
        com.lieval.maceeasily.config.TriggerDistance mode = CONFIG.windBurstTrigger;
        if (mode == com.lieval.maceeasily.config.TriggerDistance.BLOCKS_0) {
            return !living.isInWater() && !living.onClimbable();
        } else if (mode == com.lieval.maceeasily.config.TriggerDistance.BLOCKS_1_0) {
            return living.fallDistance >= 1.0 && !living.isFallFlying();
        } else {
            return living.fallDistance > 1.5 && !living.isFallFlying();
        }
    }
}
