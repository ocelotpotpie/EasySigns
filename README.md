EasySigns
=========
Create signs with custom actions.


Features
--------
 * Any sign can store a list of actions, which are triggered by left-clicking
   or right-clicking the sign.
 * Actions include sending messages, teleporting players, editing player
   inventories, sending redstone signals and even executing arbitrary commands.
 * Sign actions can be edited and copied from one sign to another.
 * Sign actions are stored using BlockStore, and so will be preserved in
   map downloads for use past the end of a server revision.


Commands and Sign Actions
-------------------------
Actions that can be performed by signs and the commands to configure them
are documented at http://wiki.nerd.nu/wiki/EasySign.


Dependencies
------------
This plugin depends on BlockStore.


Permissions
-----------
No permissions are required to execute sign actions by clicking on a sign.

Full access to all commands is granted by the `easysigns.commands.*` permission.
That permission encompasses the following permissions:

| Permission | Description |
| :---       | :---        |
| `easysigns.commands.easy-sign` | Access to the `/easy-sign` command. |
| `easysigns.commands.easy-sign-delete` | Access to the `/easy-sign-delete` command. |
| `easysigns.commands.easy-sign-info` | Access to the `/easy-sign-info` command. |
| `easysigns.commands.easy-sign-remove` | Access to the `/easy-sign-remove` command. |
| `easysigns.commands.easy-sign-reorder` | Access to the `/easy-sign-reorder` command. |
| `easysigns.commands.easy-sign-copy` | Access to the `/easy-sign-copy` command. |
| `easysigns.commands.easy-sign-paste` | Access to the `/easy-sign-paste` command. |


