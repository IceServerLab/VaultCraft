package jp.iceserver.vaultcraft.economy

import jp.iceserver.vaultcraft.config.MainConfig
import jp.iceserver.vaultcraft.tables.Accounts
import jp.iceserver.vaultcraft.utils.UUIDFetcher
import jp.iceserver.vaultcraft.utils.toSimpleString
import net.milkbowl.vault.economy.Economy
import net.milkbowl.vault.economy.EconomyResponse
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class VaultEconomy : Economy
{
    override fun isEnabled(): Boolean = true

    override fun getName(): String = "VaultCraft"

    override fun hasBankSupport(): Boolean = true

    override fun fractionalDigits(): Int = -1

    override fun format(amount: Double): String = amount.toSimpleString()

    override fun currencyNamePlural(): String = MainConfig.currencyName.split("|")[0]

    override fun currencyNameSingular(): String = MainConfig.currencyName.split("|")[1]

    override fun hasAccount(playerName: String): Boolean = false

    override fun hasAccount(player: OfflinePlayer): Boolean {
        TODO("Not yet implemented")
    }

    override fun hasAccount(playerName: String, worldName: String): Boolean = false

    override fun hasAccount(player: OfflinePlayer, worldName: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun getBalance(playerName: String): Double = 0.0

    override fun getBalance(player: OfflinePlayer): Double {
        TODO("Not yet implemented")
    }

    override fun getBalance(playerName: String, world: String): Double = 0.0

    override fun getBalance(player: OfflinePlayer, world: String): Double
    {

    }

    override fun has(playerName: String, amount: Double): Boolean = false
    override fun has(player: OfflinePlayer, amount: Double): Boolean {
        TODO("Not yet implemented")
    }

    override fun has(playerName: String, worldName: String, amount: Double): Boolean = false

    override fun has(player: OfflinePlayer, worldName: String, amount: Double): Boolean {
        TODO("Not yet implemented")
    }

    override fun withdrawPlayer(playerName: String, amount: Double): EconomyResponse
            = EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "Deprecated.")

    override fun withdrawPlayer(player: OfflinePlayer, amount: Double): EconomyResponse {
        TODO("Not yet implemented")
    }

    override fun withdrawPlayer(playerName: String, worldName: String, amount: Double): EconomyResponse
            = EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "Deprecated.")

    override fun withdrawPlayer(player: OfflinePlayer, worldName: String, amount: Double): EconomyResponse {
        TODO("Not yet implemented")
    }

    override fun depositPlayer(playerName: String, amount: Double): EconomyResponse
            = EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "Deprecated.")

    override fun depositPlayer(player: OfflinePlayer, amount: Double): EconomyResponse
    {
        transaction {
            var balance = 0.0
            Accounts.select { Accounts.uniqueId eq player.uniqueId }.forEach { balance = it[Accounts.balance]}
            Accounts.update({ Accounts.uniqueId eq player.uniqueId }) {
                it[Accounts.balance] = balance - amount
            }
        }
    }

    override fun depositPlayer(playerName: String, worldName: String, amount: Double): EconomyResponse
            = EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "Deprecated.")

    override fun depositPlayer(player: OfflinePlayer, worldName: String, amount: Double): EconomyResponse {
        TODO("Not yet implemented")
    }

    override fun createBank(name: String, player: String): EconomyResponse
            = EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "Deprecated.")

    override fun createBank(name: String, player: OfflinePlayer): EconomyResponse {
        TODO("Not yet implemented")
    }

    override fun deleteBank(name: String): EconomyResponse {
        TODO("Not yet implemented")
    }

    override fun bankBalance(name: String): EconomyResponse {
        TODO("Not yet implemented")
    }

    override fun bankHas(name: String, amount: Double): EconomyResponse {
        TODO("Not yet implemented")
    }

    override fun bankWithdraw(name: String, amount: Double): EconomyResponse {
        TODO("Not yet implemented")
    }

    override fun bankDeposit(name: String, amount: Double): EconomyResponse {
        TODO("Not yet implemented")
    }

    override fun isBankOwner(name: String, playerName: String): EconomyResponse
            = EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "Deprecated.")

    override fun isBankOwner(name: String, player: OfflinePlayer): EconomyResponse {
        TODO("Not yet implemented")
    }

    override fun isBankMember(name: String, playerName: String): EconomyResponse
        = EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "Deprecated.")

    override fun isBankMember(name: String, player: OfflinePlayer): EconomyResponse {
        TODO("Not yet implemented")
    }

    override fun getBanks(): MutableList<String> {
        TODO("Not yet implemented")
    }

    override fun createPlayerAccount(playerName: String): Boolean = createPlayerAccount(Bukkit.getOfflinePlayer(UUIDFetcher().fetchUUID(playerName)))

    override fun createPlayerAccount(player: OfflinePlayer): Boolean
    {
        var id = -1

        transaction {
            id = Accounts.insert {
                it[uniqueId] = player.uniqueId
                it[balance] = 0.0
            }[Accounts.id]
        }

        return id != -1
    }

    override fun createPlayerAccount(playerName: String, worldName: String): Boolean = createPlayerAccount(playerName)

    override fun createPlayerAccount(player: OfflinePlayer, worldName: String): Boolean = createPlayerAccount(player)
}