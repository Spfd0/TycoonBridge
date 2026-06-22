package com.tycoonbridge.utils

import com.tycoonbridge.TycoonBridge.ChatChannel
import com.tycoonbridge.config.TycoonBridgeConfig.config
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import java.util.regex.Pattern

class ChatFormatter {
    companion object {
        internal val GUILD_PATTERN: Pattern = Pattern.compile(
            """^(?:§\w)?(?:G|Guild) > ((?:§\w)?\[(?:\S+?)\] )?(?:§\w)?(\w+)(?: §3\[(\S+?)\])?(?:§\w)?: ?(.+)$"""
        )
        internal val OFFICER_PATTERN: Pattern = Pattern.compile(
            """^(?:§\w)?(?:Officer) > ((?:§\w)?\[(?:\S+?)\] )?(?:§\w)?(\w+)(?: §3\[(\S+?)\])?(?:§\w)?: ?(.+)$"""
        )
        internal val BRIDGE_PATTERN: Pattern = Pattern.compile(
            """^ *((?:.+?)(?: attached an? \w+(?::|$)| replied to .+ with an? \w+(?::|$)| replied to .+?(?::|$)|:))(?:(?: (.*)?$)|$)"""
        )
    }

    private data class ParsedMessage(
        val rank: String?,
        val username: String,
        val text: String,
    )

    fun format(message: Component, channel: ChatChannel): Component {
        val messageText = message.string.substringBefore('\n')

        if (channel == ChatChannel.GUILD) {
            val parsed = parsePattern(messageText, GUILD_PATTERN) ?: return message
            val isBridgeMessage =
                config.bridgeEnabled && config.botNames.isNotEmpty() && config.botNames.contains(parsed.username.lowercase())

            if (!isBridgeMessage) return message

            val bridgeMatcher = BRIDGE_PATTERN.matcher(parsed.text)

            val (name, msg) =
                if (bridgeMatcher.find()) bridgeMatcher.group(1) to (bridgeMatcher.group(2) ?: "")
                else parsed.username to parsed.text

            return coloredPrefix(config.prefix, config.prefixColor)
                .append(
                    Component.literal(if (msg.isEmpty()) parsed.text else name.removeSuffix(":"))
                        .withColor(config.nameColor.toColor())
                )
                .append(
                    Component.literal(if (msg.isEmpty()) "" else ": ${msg.removePrefix(": ")}")
                        .withColor(config.messageColor.toColor())
                )
        }

        if (channel == ChatChannel.OFFICER) {
            val parsed = parsePattern(messageText, OFFICER_PATTERN) ?: return message
            val isBridgeMessage =
                config.bridgeEnabled && config.botNames.isNotEmpty() && config.botNames.contains(parsed.username.lowercase())

            if (!isBridgeMessage) return message

            val bridgeMatcher = BRIDGE_PATTERN.matcher(parsed.text)

            val (name, msg) =
                if (bridgeMatcher.find()) bridgeMatcher.group(1) to (bridgeMatcher.group(2) ?: "")
                else parsed.username to parsed.text

            return coloredPrefix(config.officerPrefix, config.officerPrefixColor)
                .append(
                    Component.literal(if (msg.isEmpty()) parsed.text else name.removeSuffix(":"))
                        .withColor(config.officerNameColor.toColor())
                )
                .append(
                    Component.literal(if (msg.isEmpty()) "" else ": ${msg.removePrefix(": ")}")
                        .withColor(config.officerMessageColor.toColor())
                )
        }

        return message
    }

    private fun parsePattern(messageText: String, pattern: Pattern): ParsedMessage? {
        val match = pattern.matcher(messageText)
        if (!match.matches()) return null

        return ParsedMessage(
            rank = match.group(1),
            username = match.group(2),
            text = match.group(4)
        )
    }

    private fun coloredPrefix(prefix: String, color: String): MutableComponent {
        return Component.literal("$prefix ").withColor(color.toColor())
    }
}
