package jp.iceserver.vaultcraft.config

import hazae41.minecraft.kutils.bukkit.PluginConfigFile

object MainConfig : PluginConfigFile("config")
{
    var prefix by string("prefix")
    var currencyName by string("currencyName")
}