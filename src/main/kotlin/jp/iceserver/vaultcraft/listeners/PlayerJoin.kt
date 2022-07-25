package jp.iceserver.vaultcraft.listeners

import jp.iceserver.vaultcraft.VaultCraft
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class PlayerJoin : Listener
{
    @EventHandler(priority = EventPriority.HIGH)
    fun onPlayerJoin(e: PlayerJoinEvent)
    {
        if (VaultCraft.economy.hasAccount(e.player)) return

        VaultCraft.economy.createPlayerAccount(e.player)
    }
}