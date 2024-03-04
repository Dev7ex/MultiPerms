# MultiPerms

![Spiget Downloads](https://img.shields.io/spiget/downloads/111992)
![Spiget Rating](https://img.shields.io/spiget/rating/111992)
![GitHub License](https://img.shields.io/github/license/Dev7ex/MultiPerms)
[![CodeFactor](https://www.codefactor.io/repository/github/dev7ex/multiperms/badge)](https://www.codefactor.io/repository/github/dev7ex/multiperms)
![GitHub Release](https://img.shields.io/github/v/release/Dev7ex/MultiPerms)

## What is MultiPerms?

MultiPerms is a simple permission management system for Minecraft.

* BungeeCord & Spigot Support
* Almost 100% configurable
* Create Custom Groups
* Permission Per Player
* SubGroups
* Live Updates
* Colored Chat
* Basic Rights
* Auto Config Updater

## Links

* https://bstats.org/plugin/bukkit/MultiPerms-Bukkit/19393
* https://bstats.org/plugin/bungeecord/MultiPerms%-BungeeCord/21212
* https://github.com/Dev7ex/MultiPerms

## Commands

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
```

## Configuration

```
#  __  __       _ _   _ _____
# |  \/  |     | | | (_)  __ \
# | \  / |_   _| | |_ _| |__) |__ _ __ _ __ ___  ___
# | |\/| | | | | | __| |  ___/ _ \ '__| '_ ` _ \/ __|
# | |  | | |_| | | |_| | |  |  __/ |  | | | | | \__ \
# |_|  |_|\__,_|_|\__|_|_|   \___|_|  |_| |_| |_|___/
#
# Copyright (c) 2023 by Dev7ex
# Version: ${project.version}
config-version: ${project.version}
# General
prefix: '§8[§aMultiPerms§8]§r'
no-permission: '§cIm sorry, but you do not have permission to perform this command. Please contact the server administrators if you believe that is in error.'
no-console-command: '%prefix% §cThis command can only performed by the console'
no-player-command: '%prefix% §cThis command can only performed by a player'
no-player-found: '%prefix% §cThis player could not be found'

settings:
  chat-enabled: true
  chat-format: '%prefix% %name% §8» §7%message%'
  tablist-enabled: true
  basic-rights-enabled: false
  

messages:
  commands:
    permission:
      group:
        add:
          usage: '%prefix% §cUsage: /permission group add <Group> <Permission>'
          group-has-permission: '%prefix% §7The group %colored_group_name% §7has the permission §a%permission% §7already'
          successfully-added: '%prefix% §7You have added the permission §a%permission% §7to the group %colored_group_name%'
        create:
          usage: '%prefix% §cUsage /permission group create <Name> <Identification>'
          group-already-exists: '%prefix% §cThere is already a group with this name!'
          group-already-exists-id: '%prefix% §cThere is already a group with this Id!'
          successfully-created: '%prefix% §7The group %colored_group_name% §7was §acreated'
        delete:
          usage: '%prefix% §cUsage /permission group delete <Group>'
          successfully-deleted: '%prefix% §7The group %colored_group_name% §7has been §cdeleted'
        edit:
          usage: '%prefix% §cUsage /permission group edit <Group> <Property> <Value>'
          property-not-exists: '%prefix% §cThis group property does not exist'
          invalid-color: '%prefix% §cPlease specify a valid color'
          successfully-edited: '%prefix% §7The property §a%group_property% §7was set to §a%value%'
        list:
          usage: '%prefix% §cUsage: /permission group list'
          message: '%prefix% §7Groups: %colored_group_names%'
        remove:
          usage: '%prefix% §cUsage: /permission group remove <Group> <Permission>'
          group-has-permission-not: '%prefix% §7The group %colored_group_name% §7has the permission §a%permission% §7not'
          successfully-removed: '%prefix% §7You have removed the permission §a%permission% §7from the group %colored_group_name%!'
      help:
        message:
          - ''
          - '§f§m                    §r %prefix% §f§m                    '
          - ''
          - '§7» §7/permission §agroup §7add <Group> <Permission>'
          - '§7» §7/permission §agroup §7create <Name> <Identification>'
          - '§7» §7/permission §agroup §7delete §7<Group>'
          - '§7» §7/permission §agroup §7edit §7<Group> <Property> <Value>'
          - '§7» §7/permission §agroup §7list'
          - '§7» §7/permission §agroup §7remove <Group> <Permission>'
          - ''
          - '§7» §7/permission §areload'
          - ''
          - '§7» §7/permission §auser §7<User> §aadd §7<Group | Permission> <Value>'
          - '§7» §7/permission §auser §7<User> §aclear §7<Group | Permission>>'
          - '§7» §7/permission §auser §7<User> §ainfo'
          - '§7» §7/permission §auser §7<User> §aremove §7<Group | Permission> <Value>'
          - '§7» §7/permission §auser §7<User> §aset §7<Group>'
          - ''
          - '§f§m                    §r %prefix% §f§m                    '
          - ''
      reload:
        usage: '%prefix% §cUsage /permission reload'
        message: '%prefix% §7The configuration has been reloaded successfully!'
      user:
        add:
          usage: '%prefix% §cUsage /permission user <User> add <Group | Permission> <Value>'
          group:
            main-group: '%prefix% §cThis is the main group of %colored_user_name%'
            user-has-group: '%prefix% %colored_user_name% §7has the group %colored_group_name% §7already'
            successfully-added: '%prefix% §7You have added %colored_user_name% §7the group %colored_group_name%'
          permission:
            user-has-permission: '%prefix% %colored_user_name% §7has the permission §a%permission% §7already'
            successfully-added: '%prefix% §7You have added %colored_user_name% §7the permission §a%permission%'
        clear:
          usage: '%prefix% §cUsage /permission user <User> clear <Group | Permission>'
          group:
            user-groups-empty: '%prefix% %colored_user_name% §7has no subgroups'
            successfully-cleared: '%prefix% §7You have removed %colored_user_name% §7all subgroups'
          permission:
            user-permissions-empty: '%prefix% %colored_user_name% §7has no permissions'
            successfully-cleared: '%prefix% §7You have removed %colored_user_name% §7all permissions'
        info:
          usage: '%prefix% §cUsage /permission user <User> info'
          message:
            - ''
            - '§f§m                    §r %colored_user_name% §f§m                    '
            - ''
            - '§7» §7UUID: §a%unique_id%'
            - '§7» §7Name: §a%colored_user_name%'
            - '§7» §7Group: §a%colored_group_name%'
            - '§7» §7Permissions: §a%permissions%'
            - '§7» §7SubGroups: §a%colored_group_names%'
            - ''
            - '§f§m                    §r %colored_user_name% §f§m                    '
            - ''
        remove:
          usage: '%prefix% §cUsage /permission user <User> remove <Group | Permission> <Value>'
          group:
            main-group: '%prefix% §7This is the main group of %colored_user_name%'
            user-has-group-not: '%prefix% %colored_user_name% §7has the group %colored_group_name% §7not'
            successfully-removed: '%prefix% §7You have %colored_user_name% §7removed the %colored_group_name% §7group'
          permission:
            user-has-permission-not: '%prefix% %colored_user_name% §7has permission §a%permission% §7not'
            successfully-removed: '%prefix% §7You have %colored_user_name% §7removed the permission §a%permission%'
        set:
          usage: '%prefix% §cUsage /permission user <User> set <Group>'
          user-has-group: '%prefix% %colored_user_name% §7has the group %colored_group_name% §7already'
          successfully-set: '%prefix% §7You have %colored_user_name% §7set the group %colored_group_name%'
  general:
    group-not-exists: '%prefix% §cThis group does not exist!'
    invalid-identification: '%prefix% §cThis is not a valid Id!'
    function-not-available: '%prefix% §cThis function is not available'
```




