package com.tycoonbridge.config

import me.shedaniel.clothconfig2.api.ConfigBuilder
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.Component.translatable
import com.tycoonbridge.config.categories.GeneralCategory

object TycoonBridgeConfigManager {
    fun build(parent: Screen?): Screen {
        val builder = ConfigBuilder.create()
        builder.parentScreen = parent
        builder.title = translatable("title.tycoonbridge.config")

        val general = builder.getOrCreateCategory(translatable("category.tycoonbridge.general"))
        val entryBuilder = builder.entryBuilder()

        GeneralCategory().build(general, entryBuilder)

        builder.setSavingRunnable(TycoonBridgeConfig::save)

        return builder.build()
    }
}
