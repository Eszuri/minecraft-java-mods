package com.lieval.stackables;

import com.lieval.stackables.config.StackableConfig;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class StackableCommands {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(Commands.literal("stackable")
                .then(Commands.literal("set")
                    .then(Commands.argument("amount", IntegerArgumentType.integer(1, 999999))
                        .executes(context -> {
                            int amount = IntegerArgumentType.getInteger(context, "amount");
                            StackableItemsMod.CONFIG.maxStackSize = amount;
                            StackableItemsMod.CONFIG.save();
                            context.getSource().sendSuccess(() -> Component.literal("§6[Stackable] §aMax stack size set to: §e" + amount), true);
                            return 1;
                        })
                    )
                )
                .then(Commands.literal("reset")
                    .executes(context -> {
                        StackableItemsMod.CONFIG.maxStackSize = 64;
                        StackableItemsMod.CONFIG.save();
                        context.getSource().sendSuccess(() -> Component.literal("§6[Stackable] §aMax stack size reset to default: §e64"), true);
                        return 1;
                    })
                )
                .then(Commands.literal("reload")
                    .executes(context -> {
                        StackableItemsMod.CONFIG = StackableConfig.load();
                        context.getSource().sendSuccess(() -> Component.literal("§6[Stackable] §aConfiguration reloaded successfully!"), true);
                        return 1;
                    })
                )
            );
        });
    }
}
