# TycoonBridge

A client-side Fabric mod that provides fully customizable formatting for bridge bot messages in Hypixel guild chat.

TycoonBridge intercepts incoming **Guild** and **Officer** chat messages sent by your bridge bot and reformats them with custom prefixes, colors, and styling — making bridged messages clean and easy to read at a glance.

---

## Supported Versions

| Minecraft Version | Folder              |
| ----------------- | ------------------- |
| 1.21.11           | `1.21.11/`          |
| 26.1              | `26.1/`             |
| 26.1.1            | `26.1.1/`           |
| 26.1.2            | `26.1.2/`           |
| 26.2              | `26.2/`             |

---

## Dependencies

TycoonBridge requires the following mods to be installed in your `mods` folder:

| Dependency                                                                                   | Required | Purpose                              |
| -------------------------------------------------------------------------------------------- | -------- | ------------------------------------ |
| [Fabric Loader](https://fabricmc.net/use/installer/) (≥ 0.18.4)                              | ✅        | Mod loader                           |
| [Fabric API](https://modrinth.com/mod/fabric-api)                                            | ✅        | Core Fabric library                  |
| [Fabric Language Kotlin](https://modrinth.com/mod/fabric-language-kotlin)                     | ✅        | Kotlin language support              |
| [Cloth Config API](https://modrinth.com/mod/cloth-config)                                    | ✅        | Powers the in-game settings GUI      |
| [Mod Menu](https://modrinth.com/mod/modmenu)                                                 | ❌        | Optional — adds a settings button in the mod list |

> **Note:** Make sure each dependency matches your Minecraft version. For example, if you are running Minecraft `26.1`, install the `26.1` versions of Fabric API, Cloth Config, etc.

---

## Installation

1. Install [Fabric Loader](https://fabricmc.net/use/installer/) for your Minecraft version.
2. Download the required dependencies listed above and place them in your `.minecraft/mods` folder.
3. Download the TycoonBridge `.jar` for your Minecraft version from the [Repo](https://github.com/Spfd0/TycoonBridge) page.
4. Place the TycoonBridge `.jar` into your `.minecraft/mods` folder.
5. Launch the game.

---

## How It Works

TycoonBridge listens to all incoming chat messages on the client side. When a message arrives, the mod:

1. **Identifies the channel** — checks if the message starts with `Guild >`, `G >`, or `Officer >`.
2. **Checks the sender** — compares the username against your configured **Bot Names** list.
3. **Parses the bridge format** — extracts the real player name and their message from the bot's relayed text.
4. **Reformats the message** — replaces the original message with a cleanly formatted version using your custom prefix, prefix color, username color, and message color.

### Example

**Before** (raw bridge bot message):
```
Guild > BridgeBot: PlayerName: Hello everyone!
```

**After** (with default TycoonBridge formatting):
```
Bridge > PlayerName: Hello everyone!
```
Where `Bridge >` is green, `PlayerName` is aqua, and `Hello everyone!` is white.

---

## Configuration

### Opening the Settings

There are two ways to access the TycoonBridge settings GUI:

- **In-game command:** Type `/tycoonbridge` in chat
- **Mod Menu:** Click the settings icon next to TycoonBridge in the Mod Menu mod list

### Config File

Settings are saved to a JSON file at:
```
.minecraft/config/tycoonbridge.json
```

You can edit this file manually or use the in-game GUI. The config is automatically created with default values on first launch.

### Settings Overview

#### General

| Setting           | Type    | Default | Description                                                                                      |
| ----------------- | ------- | ------- | ------------------------------------------------------------------------------------------------ |
| **Bridge Enabled** | Toggle  | `true`  | Master toggle — enables or disables all bridge message formatting.                               |
| **Bot Names**      | Text    | *(empty)* | Space-separated list of bot usernames to detect as bridge bots (case-insensitive). |

> **Important:** You must enter your bridge bot's Minecraft username in the **Bot Names** field for the mod to work. If you have multiple bots, separate them with spaces (e.g., `BridgeBot MyBot`).

#### Guild Bot Messages

Settings that control how **Guild >** bridge messages are formatted:

| Setting            | Type  | Default           | Description                                 |
| ------------------ | ----- | ----------------- | ------------------------------------------- |
| **Prefix**          | Text  | `Bridge >`        | The text prefix shown before the username.  |
| **Prefix Color**    | Color | `#00aa00` (green) | The color of the prefix text.               |
| **Username Color**  | Color | `#55ffff` (aqua)  | The color of the player's name.             |
| **Message Color**   | Color | `#ffffff` (white) | The color of the message body.              |

#### Officer Bot Messages

Settings that control how **Officer >** bridge messages are formatted:

| Setting            | Type  | Default            | Description                                 |
| ------------------ | ----- | ------------------ | ------------------------------------------- |
| **Prefix**          | Text  | `Officer Bridge >` | The text prefix shown before the username.  |
| **Prefix Color**    | Color | `#00aaaa` (dark aqua) | The color of the prefix text.            |
| **Username Color**  | Color | `#55ffff` (aqua)   | The color of the player's name.             |
| **Message Color**   | Color | `#ffffff` (white)  | The color of the message body.              |

### Default Config File

When generated, `tycoonbridge.json` will look like this:

```json
{
  "bridgeEnabled": true,
  "botNames": [""],
  "prefix": "Bridge >",
  "prefixColor": "#00aa00",
  "nameColor": "#55ffff",
  "messageColor": "#ffffff",
  "officerPrefix": "Officer Bridge >",
  "officerPrefixColor": "#00aaaa",
  "officerNameColor": "#55ffff",
  "officerMessageColor": "#ffffff"
}
```

---

## Building from Source

Each Minecraft version has its own project folder. To build:

```bash
cd <version>/TycoonBridge+<version>
./gradlew build
```

The compiled `.jar` will be located in `build/libs/`.

---

## License

This project is licensed under the [MIT License](LICENSE).
