package jp.iceserver.vaultcraft

import hazae41.minecraft.kutils.bukkit.init
import hazae41.minecraft.kutils.bukkit.listen
import jp.iceserver.vaultcraft.account.Account
import jp.iceserver.vaultcraft.commands.*
import jp.iceserver.vaultcraft.config.MainConfig
import jp.iceserver.vaultcraft.economy.VaultEconomy
import jp.iceserver.vaultcraft.tables.*
import net.milkbowl.vault.economy.Economy
import org.bukkit.Bukkit
import org.bukkit.event.server.PluginEnableEvent
import org.bukkit.plugin.ServicePriority
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.File
import java.sql.Connection

class VaultCraft : AbstractVaultCraft()
{
    companion object
    {
        lateinit var plugin: VaultCraft
        lateinit var economy: Economy
    }

    val accounts: MutableList<Account> = mutableListOf()

    override fun onEnable()
    {
        plugin = this

        init(MainConfig)
        MainConfig.autoSave = true

        val dbFolder = File(dataFolder, "/database")
        if (!dbFolder.exists()) dbFolder.mkdirs()

        val dbFile = File(dataFolder, "/database/vaultcraft.db")
        if (!dbFile.exists()) dbFile.createNewFile()

        Database.connect("jdbc:sqlite:${dbFile.path}", "org.sqlite.JDBC")
        TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_SERIALIZABLE

        transaction {
            addLogger(StdOutSqlLogger)

            SchemaUtils.create(
                Accounts
            )

            Accounts.selectAll().forEach {
                accounts.add(Account(it[Accounts.id], Bukkit.getOfflinePlayer(it[Accounts.uniqueId]), it[Accounts.balance]))
            }
        }

        if (!(server.pluginManager.getPlugin("Vault") ?: return).isEnabled)
        {
            listen<PluginEnableEvent> {
                if (it.plugin.name != "Vault") return@listen

                hookVault()
                PluginEnableEvent.getHandlerList().unregister(this)
            }
        }
        else hookVault()

        registerCommands(
            "balance" to BalanceCommand()
        )
    }

    private fun hookVault()
    {
        economy = VaultEconomy()
        server.servicesManager.register(Economy::class.java, economy, this, ServicePriority.Normal)
    }
}