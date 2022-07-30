package jp.iceserver.vaultcraft.commands

import hazae41.minecraft.kutils.bukkit.msg
import jp.iceserver.vaultcraft.VaultCraft
import jp.iceserver.vaultcraft.utils.toSimpleString
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class BalanceCommand : CommandExecutor
{
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean
    {
        sender as Player
        sender.msg(VaultCraft.economy.getBalance(sender).toSimpleString())
        return true
    }
}