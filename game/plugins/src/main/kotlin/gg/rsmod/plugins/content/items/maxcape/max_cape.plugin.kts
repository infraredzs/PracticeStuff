@file:Suppress("unused")

package gg.rsmod.plugins.content.items.maxcape

import gg.rsmod.plugins.content.magic.TeleportType
import gg.rsmod.plugins.content.magic.canTeleport
import gg.rsmod.plugins.content.magic.teleport
import gg.rsmod.plugins.api.InterfaceDestination
import gg.rsmod.game.model.queue.QueueTask
import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.message.impl.ResumePauseButtonMessage
import gg.rsmod.plugins.api.ext.interfaceOptions

val MAX = intArrayOf(
        Items.MAX_CAPE_13342)

private val SOUNDAREA_ID = 200
private val SOUNDAREA_RADIUS = 5
private val SOUNDAREA_VOLUME = 1
private val INTERFACE_ID = 187
private val INTERFACE_SCRIPT = 217
val TELE_INTERFACE_ID = 187

private val LOCATIONS = mapOf(
        "Warriors' Guild" to Tile(2869, 3546),
        "Crafting Guild" to Tile(2932, 3285)
)

arrayOf(Items.MAX_CAPE_13342).forEach { cape2 ->
    on_equipment_option(Items.MAX_CAPE_13342, "Other Teleports") {
        player.queue(TaskPriority.STRONG) {
            optionsMenu(this)
            when (interfaceOptions("1) Lumbridge", "2) Varrock",
            "3) Falador", "4) Camelot","5) Seers' Village","6) Ardougne", title = "Select an Option")) {
                0 -> player.teleport(Tile(3221, 3218), TeleportType.MAX)
                1 -> player.teleport(Tile(3210, 3423), TeleportType.MAX)
                2 -> player.teleport(Tile(2963, 3379), TeleportType.MAX)
                3 -> player.teleport(Tile(2756, 3476), TeleportType.MAX)
                4 -> player.teleport(Tile(3444, 3444), TeleportType.MAX) //Needs coords
                5 -> player.teleport(Tile(2662, 3307), TeleportType.MAX)
            }
            player.closeInterface(InterfaceDestination.MAIN_SCREEN)
        }
    }
}

// -- Teleports interface -- \\
arrayOf(Items.MAX_CAPE_13342).forEach { cape ->
    on_item_option(Items.MAX_CAPE_13342, "Teleports") {
            player.queue(TaskPriority.STRONG) {
                optionsMenu(this)
                when (interfaceOptions("1) Lumbridge", "2) Varrock",
                        "3) Falador", "4) Camelot", "5) Seers' Village", "6) Ardougne", title = "Select an Option")) {
                    0 -> player.teleport(Tile(3221, 3218), TeleportType.MAX)
                    1 -> player.teleport(Tile(3210, 3423), TeleportType.MAX)
                    2 -> player.teleport(Tile(2963, 3379), TeleportType.MAX)
                    3 -> player.teleport(Tile(2756, 3476), TeleportType.MAX)
                    4 -> player.teleport(Tile(3444, 3444), TeleportType.MAX) //Needs coords
                    5 -> player.teleport(Tile(2662, 3307), TeleportType.MAX)
                }
                player.closeInterface(InterfaceDestination.MAIN_SCREEN)
            }
        }
}

on_button(interfaceId = 187, component = 3) {
    player.getInteractingOption()
    player.closeInterface(InterfaceDestination.MAIN_SCREEN)
        close_tele_inter(player)
}

fun close_tele_inter(p: Player) {
    p.closeInterface(InterfaceDestination.MAIN_SCREEN)
}

suspend fun QueueTask.option(vararg options: String, title: String = "Select an Option"): Int {
    player.sendTempVarbit(5983, 1)
    player.runClientScript(2524, -1, -1)
    player.openInterface(187, InterfaceDestination.MAIN_SCREEN)
    player.runClientScript(217, title, options.joinToString("|"))
    player.setInterfaceEvents(interfaceId = 187, component = 3, range = 0..127, setting = 1)

    return (requestReturnValue as? ResumePauseButtonMessage)?.slot ?: -1
}

suspend fun optionsMenu(it: QueueTask): Int = it.option("1) Lumbridge", "2) Varrock","3) Falador",
        "4) Camelot","5) Seers' Village","6) Ardougne")
// -- End of teleports interface -- \\



// -- Features button -- \\
arrayOf(Items.MAX_CAPE_13342).forEach { cape ->
    on_item_option(Items.MAX_CAPE_13342, "Features") {
        player.queue {
            optionsMenu2(this)
        }
    }
    on_equipment_option(Items.MAX_CAPE_13342, "Features") {
        player.queue {
            optionsMenu2(this)
        }
    }
}

suspend fun QueueTask.option2(vararg options: String, title: String = "Max Cape Features"): Int {
    player.sendTempVarbit(5983, 1)
    player.runClientScript(2524, -1, -1)
    player.openInterface(187, InterfaceDestination.MAIN_SCREEN)
    player.runClientScript(217, title, options.joinToString("|"))
    player.setInterfaceEvents(interfaceId = 187, component = 3, range = 0..127, setting = 1)

    return (requestReturnValue as? ResumePauseButtonMessage)?.slot ?: -1
}

suspend fun optionsMenu2(it: QueueTask): Int = it.option2("Free access to cyclops area found upstairs in Warriors' Guide",
        "Unlimited teleports to Warriors' Guild", "Act as a permanant Ring of Life, this effect can be toggled",
        "Act as Ava's Accumulator","Act as if you are carrying a holy wrench","Can cast spellbook swap","Acts as all tiaras",
        "Teleport to any player owned house portal","2x HP restore rate","Acts as a graceful cape, including weight reduction and set bonus",
        "Nardah herbalist will create unfinished potions for you, for 200gp each. Also gives a pestle and mortar",
        "10% better chance of succeeding when pickpocketing","Unlimited teleports to the Crafting Guild",
        "Can be searched for Mithril Grapple and bronze crossbow","Slim chance of being able to persuade slayer masters to reassign your last task",
        "Unlimited teleports to red or black chinchompas","5% chance of receiving an extra ore","5% chance of not using a bar",
        "Unlimited fishing guild teleports, and 10% chance of receiving an extra fish","Impossible to burn any food",
        "Acts as a light source","10% increased chance of finding a bird's nest, also 10% chance of receiving an extra logs",
        "5% increased yield from herb patches (stacks with magical secateurs)")

MAX.forEach { max5 ->
    LOCATIONS.forEach { location, tile ->
        on_equipment_option(max5, option = location) {
            player.queue(TaskPriority.STRONG) {
                player.teleport(tile)
            }
        }
    }
}

fun Player.teleport(endTile : Tile) {
    if (canTeleport(TeleportType.MAX) && hasEquipped(EquipmentType.CAPE, *MAX)) {
        world.spawn(AreaSound(tile, SOUNDAREA_ID, SOUNDAREA_RADIUS, SOUNDAREA_VOLUME))
        teleport(endTile, TeleportType.MAX)
    }
}
