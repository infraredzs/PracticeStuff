package gg.rsmod.plugins.content.objs.bankbooth

import gg.rsmod.plugins.content.inter.bank.openBank

private val BOOTHS = with(Objs) { setOf(
         BANK_BOOTH,
         BANK_BOOTH_10355,
         BANK_BOOTH_10357,
         BANK_BOOTH_10517,
         BANK_BOOTH_10583,
         BANK_BOOTH_10584,
         BANK_BOOTH_11338,
         BANK_BOOTH_12798,
         BANK_BOOTH_14367,
         BANK_BOOTH_32666,
         BANK_BOOTH_16642,
         BANK_BOOTH_16700,
         BANK_BOOTH_18491,
         BANK_BOOTH_20325,
         BANK_BOOTH_22819,
         BANK_BOOTH_24101,
         BANK_BOOTH_24347,
         BANK_BOOTH_25808,
         BANK_BOOTH_27254,
         BANK_BOOTH_27260,
         BANK_BOOTH_27263,
         BANK_BOOTH_27265,
         BANK_BOOTH_27267,
         BANK_BOOTH_27292,
         BANK_BOOTH_27718,
         BANK_BOOTH_27719,
         BANK_BOOTH_27720,
         BANK_BOOTH_27721,
         BANK_BOOTH_28430,
         BANK_BOOTH_28431,
         BANK_BOOTH_28432,
         BANK_BOOTH_28433,
         BANK_BOOTH_28546,
         BANK_BOOTH_28547,
         BANK_BOOTH_28548,
         BANK_BOOTH_28549,
         BANK_BOOTH_36559,
         //BANK_BOOTH_37959,// Has "use" option
         BANK_BOOTH_39238,
         BANK_BOOTH_42837,// has only "bank option"
) }

BOOTHS.forEach { booth ->
    on_obj_option(obj = booth, option = "bank") {
        player.openBank()
    }
    if (if_obj_has_option(booth, "Collect")) {
        on_obj_option(obj = booth, option = "Collect") {
            open_collect(player)
        }
    }
}

//Adding the platinum tokens system
//token variable is the Platinum Token Item number, don't confuse it with the tokensIn/Out vars in the code unless I remove
val token = Items.PLATINUM_TOKEN_13204
//money variable is the Coins item number.
val money = Items.COINS_995
//@TODO Finish the ItemMessageBoxes, and MAX VALUE doens't work, just takes the money and puts in no tokens.
BOOTHS.forEach { booth ->
    on_item_on_obj(obj = booth, item = money, lineOfSightDistance = -1) {
        player.queue {
            when (options("Yes", "No", title = "Exchange your coins for platinum tokens?")) {
                1 -> {
                    val maxcoins = player.inventory.getItemCount(money) //Get the amount of coins.
                    val moduloMath = maxcoins % 1000 //This get's the remainder of coins if someone has over the divisible amount
                    val mathcoins = maxcoins / 1000 //The simple formula for converting coins to platinum tokens.
                    if (player.inventory.contains(money)) { //If you have coins, great!
                        player.inventory.add(money, amount = moduloMath) //Order of operations,
                        player.inventory.remove(money, amount = maxcoins) //Remove all the coins.
                        player.inventory.add(token, amount = mathcoins) //This gives our mathed out coins from coins -> tokens to the player.
                    }
                    //Don't know how ever this would be called.
                    else player.message("Unable to complete transaction...")
                }
                2 -> {

                }
                else -> null
            }
        }
    }
}

BOOTHS.forEach { booth ->
    on_item_on_obj(obj = booth, item = token, lineOfSightDistance = -1) {
        player.queue {
            when (options("Yes", "No", title = "Exchange your platinum tokens for coins?")) {
                1 -> {
                    val maxtoken = player.inventory.getItemCount(token)
                    val ezMath = maxtoken * 1000
                    if (player.inventory.contains(token)) {
                        player.inventory.remove(token, amount = maxtoken)
                        player.inventory.add(money, amount = ezMath)
                    }
                }
                2 -> {
                    //do nothing.
                }
            }
        }
}

}

fun open_collect(p: Player) {
    p.setInterfaceUnderlay(color = -1, transparency = -1)
    p.openInterface(interfaceId = 402, dest = InterfaceDestination.MAIN_SCREEN)
}

