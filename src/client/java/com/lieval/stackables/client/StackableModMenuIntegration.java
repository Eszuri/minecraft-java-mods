package com.lieval.stackables.client;

import com.lieval.stackables.client.gui.StackableConfigScreen;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

public class StackableModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return StackableConfigScreen::new;
    }
}
