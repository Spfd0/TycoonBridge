package com.tycoonbridge

import com.tycoonbridge.config.TycoonBridgeConfig
import com.tycoonbridge.utils.ChatFormatter
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents
import net.minecraft.network.chat.Component
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.regex.Pattern
import com.tycoonbridge.config.TycoonBridgeConfig.config
import com.tycoonbridge.utils.TycoonBridgeCommands

object TycoonBridge : ModInitializer {
    val logger: Logger = LoggerFactory.getLogger("tycoonbridge")
    private val formattingCodePattern: Pattern = Pattern.compile("§\\w")
    private val formatter = ChatFormatter()

    override fun onInitialize() {
        TycoonBridgeConfig.load()
        TycoonBridgeCommands.register()
        ClientReceiveMessageEvents.MODIFY_GAME.register(::onModify)
    }

    private fun onModify(message: Component, actionBar: Boolean): Component {
        if (actionBar) return message

        try {
            val unformatted = formattingCodePattern.matcher(message.string).replaceAll("")
            val channelToken = unformatted.substringBefore(' ')

            val channel = when (channelToken) {
                "Guild" -> ChatChannel.GUILD
                "Officer" -> ChatChannel.OFFICER
                "G" -> ChatChannel.GUILD
                else -> ChatChannel.UNKNOWN
            }

            var formatted = message

            if (channel != ChatChannel.UNKNOWN) formatted = formatter.format(formatted, channel)

            return formatted
        } catch (e: Exception) {
            logger.error("Failed to format chat message, sending original", e)
            return message
        }
    }

    fun hasCloth(): Boolean {
        try {
            Class.forName("me.shedaniel.clothconfig2.api.ConfigBuilder")
            return true
        } catch (_: Exception) {
            return false
        }
    }

    enum class ChatChannel {
        GUILD, OFFICER, UNKNOWN
    }
}
