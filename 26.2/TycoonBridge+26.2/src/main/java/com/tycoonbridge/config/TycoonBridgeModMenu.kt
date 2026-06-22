package com.tycoonbridge.config

import com.tycoonbridge.TycoonBridge
import com.terraformersmc.modmenu.api.ConfigScreenFactory
import com.terraformersmc.modmenu.api.ModMenuApi

class TycoonBridgeModMenu : ModMenuApi {
    override fun getModConfigScreenFactory(): ConfigScreenFactory<*> {
        return ConfigScreenFactory { parent ->
            if (TycoonBridge.hasCloth()) TycoonBridgeConfigManager.build(parent) else null
        }
    }
}
