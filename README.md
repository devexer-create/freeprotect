![About](https://cdn.modrinth.com/data/cached_images/6d848e43da1cf807faf202a97454bc609f9644b1_0.webp)

![construction](https://cdn.modrinth.com/data/cached_images/69af3985c0022083c4f45b83d89f6efea5647c99.png)

# FreeProtect — Block Logging & Protection Plugin

**FreeProtect** is a lightweight, efficient, and feature-rich Minecraft plugin designed to log player block interactions and help server owners track griefing or suspicious activities. With simple commands and powerful lookup tools, FreeProtect makes it easy to protect your world without unnecessary bloat.

---

## ✨ Features

* Logs block break/place events with **timestamps, location, player name & UUID**.
* **Radius-based lookup** to search for events around you.
* **Clickable chat components** for teleporting to logged actions.
* **Paginated results** with easy navigation buttons (⏮ ◀ ▶ ⏭).
* Fully asynchronous lookups (no lag).
* Works out of the box, no complicated setup.

---

## 📜 Commands

| Command                                    | Description                                                                      |
| ------------------------------------------ | -------------------------------------------------------------------------------- |
| `/fp lookup event:<event> radius:<blocks>` | Lookup events around you. Example: `/fp lookup event:PlayerBlockBreak radius:20` |
| `/fp page <number>`                        | Show a specific lookup results page.                                             |
| `/fp help`                                 | Show all available commands.                                                     |

---

## 🔍 Example Usage

### 1. Lookup Block Breaks

```mcfunction
/fp lookup event:PlayerBlockBreak radius:15
```

* Shows all blocks broken within 15 blocks of your position.
* Hover over the **player name** to view their UUID.
* Hover over the **location line** and click to teleport there instantly.

### 2. Navigate Pages

```mcfunction
/fp page 2
```

* Shows the second page of results if multiple pages are available.

### 3. Help Menu

```mcfunction
/fp help
```

* Displays a list of available commands.

---

## 📖 Lookup Result Example

```
3m ago - Steve broke stone.
   (x120/y64/z-300/world)
```

* Hover `Steve` → shows UUID.
* Hover `(x120/y64/z-300/world)` → tooltip: "Click to teleport".
* Click location → instantly teleport there.

---

## 🚀 Installation

1. Download the plugin JAR from [Modrinth](https://modrinth.com/).
2. Place it into your server's `/plugins/` folder.
3. Restart the server.
4. Done! 🎉

---

## 🛠️ Permissions

| Permission           | Description                           |
| -------------------- | ------------------------------------- |
| `freeprotect.lookup` | Allows player to use lookup command.  |
| `freeprotect.page`   | Allows player to change lookup pages. |
| `freeprotect.help`   | Allows access to the help menu.       |

---

## 🧭 Roadmap

* Support for more event types (placements, interactions).
* GUI-based log viewer.
* MySQL/SQLite storage backend.

---

## ❤️ Contributing

Contributions, suggestions, and PRs are welcome! Help shape FreeProtect into the best free block logging plugin for the Minecraft community.

---

## 📌 Summary

FreeProtect is your go-to lightweight **block logging plugin** for Bukkit/Spigot/Paper servers. Easy to use, no setup headaches, and powerful lookup features built with performance in mind.
