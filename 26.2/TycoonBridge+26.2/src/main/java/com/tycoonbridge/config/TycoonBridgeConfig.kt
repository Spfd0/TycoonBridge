package com.tycoonbridge.config

import com.tycoonbridge.TycoonBridge.logger
import java.io.File
import com.google.gson.GsonBuilder

data class Config(
    var bridgeEnabled: Boolean = true,
    var botNames: List<String> = listOf(""),
    var prefix: String = "Bridge >",
    var prefixColor: String = "#00aa00",
    var nameColor: String = "#55ffff",
    var messageColor: String = "#ffffff",
    var officerPrefix: String = "Officer Bridge >",
    var officerPrefixColor: String = "#00aaaa",
    var officerNameColor: String = "#55ffff",
    var officerMessageColor: String = "#ffffff"
)

object TycoonBridgeConfig {
    private val gson = GsonBuilder()
        .setPrettyPrinting()
        .create()
    private val file = File("config/tycoonbridge.json")

    var config = Config()

    fun save() {
        try {
            val json = gson.toJson(config)
            file.writeText(json)
        } catch (e: Exception) {
            logger.error("Encountered error whilst trying to save config JSON.", e)
        }
    }

    fun load() {
        if (!file.exists()) {
            logger.info("Config file not found, new created.")
            save()
            return
        }

        val json = file.readText()
        config = gson.fromJson(json, Config::class.java)
    }
}