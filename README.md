![Icon-Bild](multiperms-resources/src/main/resources/images/title-github.png)

![Latest Release](https://img.shields.io/github/v/release/Dev7ex/MultiPerms)
![SpigotMC Downloads](https://img.shields.io/spiget/downloads/111992?label=Downloads)
![Spiget Rating](https://img.shields.io/spiget/rating/111992?label=Rating&style=flat-square)
![Java](https://img.shields.io/badge/Java-17+-orange)
![Spigot](https://img.shields.io/badge/Spigot-1.16--1.21-red)
[![CodeFactor](https://www.codefactor.io/repository/github/dev7ex/multiperms/badge)](https://www.codefactor.io/repository/github/dev7ex/multiworld)
![Last Commit](https://img.shields.io/github/last-commit/Dev7ex/MultiPerms)
![GitHub](https://img.shields.io/github/license/dev7ex/multiperms)
![Discord](https://img.shields.io/discord/834580308543668264)
![Modrinth Followers](https://img.shields.io/modrinth/followers/multiperms)

# Contents

1. [Overview](#overview)
2. [Features](#features)
3. [Commands](#commands)
4. [Installation](#installation)
5. [Requirements](#requirements)
6. [Configuration](#configuration)
7. [Contributing](#contributing)
8. [License](#license)
9. [Contact](#contact)

# Overview

- MultiPerms is a simple and flexible permission management system for Minecraft that supports BungeeCord, Spigot, and Velocity.
With this solution, you can manage player permissions individually, create custom groups, and much more.

# Features

* BungeeCord, Spigot & Velocity Support
  MultiPerms is compatible with the major Minecraft server platforms, ensuring flexibility across different setups.
* Almost 100% Configurable
  Customize nearly every aspect of the plugin to suit your server's needs.
* Create Custom Groups
  Easily define your own permission groups with specific rules and roles.
* Permission Per Player
  Set individual permissions for players, giving you fine control over access.
* SubGroups
  Assign multiple groups to players with the ability to inherit permissions.
* Live Updates
  Make permission changes that take effect without needing a server restart.
* Colored Chat
  Add a personal touch with configurable chat colors based on permissions.
* Basic Rights
  Pre-configured basic permission sets for easy setup.
* Auto Config Updater
  Automatically updates your config to the latest version without losing custom changes.

# Commands

```
* /permission                                                       [multiperms.command.permission]
* /permission group create <Name> <Identification>                  [multiperms.command.permission.group.create]
* /permission group delete <Group>                                  [multiperms.command.permission.group.delete]
* /permission group edit <Group> <Property> <Value>                 [multiperms.command.permission.group.edit]
* /permission group remove <Permission>                             [multiperms.command.permission.group.remove]
* /permission reload                                                [multiperms.command.permission.reload]
* /permission user <User> add <Group | Permission> <Value>          [multiperms.command.permission.user.add]
* /permission user <User> clear <Group | Permission>                [multiperms.command.permission.user.clear]
* /permission user <User> info                                      [multiperms.command.permission.user.info]
* /permission user <User> remove <Group | Permission> <Value>       [multiperms.command.permission.user.remove]
* /permission user <User> set <Group>                               [multiperms.command.permission.set]
* /permission version                                               [multiperms.command.permission.version]
```

# Installation

1. Download the latest version of `MultiPerms` from [GitHub Releases](https://github.com/Dev7ex/MultiPerms/releases).
2. Download the required version of `FacilisCommon`
   from [GitHub Releases](https://github.com/Dev7ex/FacilisCommon/releases).
3. Copy the downloaded `.jar` file into the `plugins` directory of your Spigot server.
4. Restart the server to activate the plugin.

# Requirements

- Minecraft Version: 1.16 - 1.21
- Java Version: 17 or higher
- BungeeCord, Spigot or Velocity Server

# Configuration

- After installation, a configuration file will be created in the `plugins/MultiPerms` directory. Here, you can make
  various settings.

# Contributing

We welcome contributions to MultiPerms! If you'd like to contribute, please follow these guidelines:

1. Fork the repository and clone it to your local machine.
2. Create a new branch for your feature or bug fix.
3. Make your changes and ensure the code passes any existing tests.
4. Commit your changes and push them to your fork.
5. Submit a pull request, explaining the changes you've made and why they should be merged.
6. Ensure your pull request adheres to the code style and guidelines of the project.

Thank you for contributing to MultiPerms!

# License

The MultiPerms project is licensed under the GNU General Public License v3.0. See the [LICENSE](LICENSE) file for
details.

# Contact

If you have any questions or need support, you can reach out to Dev7ex via:

- Twitter: [@Dev7ex](https://twitter.com/Dev7ex)
- Discord: [Dev7ex's Discord Server](discord.dev7ex.com)


