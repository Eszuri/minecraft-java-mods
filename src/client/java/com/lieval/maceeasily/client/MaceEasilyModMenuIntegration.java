package com.lieval.maceeasily.client;

import com.lieval.maceeasily.client.gui.MaceEasilyConfigScreen;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

public class MaceEasilyModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return MaceEasilyConfigScreen::new;
    }
}
