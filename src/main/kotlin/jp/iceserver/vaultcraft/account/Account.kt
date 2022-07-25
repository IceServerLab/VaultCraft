package jp.iceserver.vaultcraft.account

import org.bukkit.OfflinePlayer

data class Account(val id: Int, val player: OfflinePlayer, val balance: Double)