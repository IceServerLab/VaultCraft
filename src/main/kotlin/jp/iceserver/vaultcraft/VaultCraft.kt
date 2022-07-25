package jp.iceserver.vaultcraft

import hazae41.minecraft.kutils.bukkit.listen
import jp.iceserver.vaultcraft.economy.VaultEconomy
import net.milkbowl.vault.economy.Economy
import org.bukkit.event.server.PluginEnableEvent
import org.bukkit.plugin.ServicePriority

class VaultCraft : AbstractVaultCraft()
{
    companion object
    {
        lateinit var plugin: VaultCraft
        lateinit var economy: Economy
    }

    override fun onEnable()
    {
        plugin = this

        if (!(server.pluginManager.getPlugin("Vault") ?: return).isEnabled)
        {
            listen<PluginEnableEvent> {
                if (it.plugin.name != "Vault") return@listen

                hookVault()
                PluginEnableEvent.getHandlerList().unregister(this)
            }
        }
        else hookVault()
    }

    private fun hookVault()
    {
        economy = VaultEconomy()
        server.servicesManager.register(Economy::class.java, economy, this, ServicePriority.Normal)
    }
}