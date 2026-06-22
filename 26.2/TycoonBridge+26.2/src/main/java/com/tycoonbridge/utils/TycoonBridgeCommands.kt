package com.tycoonbridge.utils

import com.tycoonbridge.config.TycoonBridgeConfigManager
import net.fabricmc.fabric.api.client.command.v2.ClientCommands.literal
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback
import net.minecraft.client.Minecraft

object TycoonBridgeCommands {
    fun register() {
        ClientCommandRegistrationCallback.EVENT.register { dispatcher, _ ->
            dispatcher.register(
                literal("tycoonbridge").executes {
                    val client = Minecraft.getInstance()
                    val screen = TycoonBridgeConfigManager.build(null)
                    client.execute {
                        client.setScreenAndShow(screen)
                    }
                    1
                }
            )
        }
    }
}
