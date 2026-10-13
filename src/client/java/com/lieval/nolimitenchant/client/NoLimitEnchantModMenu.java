package com.lieval.nolimitenchant.client;

import com.lieval.nolimitenchant.client.gui.NoLimitEnchantConfigScreen;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

public class NoLimitEnchantModMenu implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return NoLimitEnchantConfigScreen::new;
    }
}
