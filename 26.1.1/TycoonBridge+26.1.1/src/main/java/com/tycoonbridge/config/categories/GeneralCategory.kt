package com.tycoonbridge.config.categories

import com.tycoonbridge.config.TycoonBridgeConfig.config
import me.shedaniel.clothconfig2.api.ConfigCategory
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder
import net.minecraft.network.chat.Component.translatable

class GeneralCategory {
    fun build(
        category: ConfigCategory,
        entryBuilder: ConfigEntryBuilder,
    ) {
        category.addEntry(
            entryBuilder.startBooleanToggle(translatable("entry.tycoonbridge.enabled"), config.bridgeEnabled)
                .setDefaultValue(true)
                .setTooltip(translatable("tooltip.tycoonbridge.bridgeEnabled"))
                .setSaveConsumer { value -> config.bridgeEnabled = value }
                .build()
        )

        category.addEntry(
            entryBuilder.startTextField(translatable("entry.tycoonbridge.botNames"), config.botNames.joinToString(" "))
                .setTooltip(translatable("tooltip.tycoonbridge.botNames"))
                .setSaveConsumer { value ->
                    config.botNames = value.lowercase().trim().split(Regex("\\s+")).filter { it.isNotEmpty() }
                }
                .build()
        )

        val guild = entryBuilder.startSubCategory(translatable("entry.tycoonbridge.guildBot")).setExpanded(false)
        guild.add(entryBuilder.trimmedTextEntry("entry.tycoonbridge.prefix", config.prefix, "Bridge >") { config.prefix = it })
        guild.add(entryBuilder.colorEntry("entry.tycoonbridge.prefixColor", config.prefixColor, 0x00aa00) { config.prefixColor = it })
        guild.add(entryBuilder.colorEntry("entry.tycoonbridge.nameColor", config.nameColor, 0x55ffff) { config.nameColor = it })
        guild.add(entryBuilder.colorEntry("entry.tycoonbridge.messageColor", config.messageColor, 0xffffff) { config.messageColor = it })
        category.addEntry(guild.build())

        val officer = entryBuilder.startSubCategory(translatable("entry.tycoonbridge.officerBot")).setExpanded(false)
        officer.add(entryBuilder.trimmedTextEntry("entry.tycoonbridge.prefix", config.officerPrefix, "Officer Bridge >") { config.officerPrefix = it })
        officer.add(entryBuilder.colorEntry("entry.tycoonbridge.prefixColor", config.officerPrefixColor, 0x00aaaa) { config.officerPrefixColor = it })
        officer.add(entryBuilder.colorEntry("entry.tycoonbridge.nameColor", config.officerNameColor, 0x55ffff) { config.officerNameColor = it })
        officer.add(entryBuilder.colorEntry("entry.tycoonbridge.messageColor", config.officerMessageColor, 0xffffff) { config.officerMessageColor = it })
        category.addEntry(officer.build())
    }
}
