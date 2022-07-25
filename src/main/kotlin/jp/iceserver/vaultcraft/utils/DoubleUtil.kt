package jp.iceserver.vaultcraft.utils

fun Double.toSimpleString(): String
{
    val double = this.toString()
    if (!double.contains(".")) return double
    return double.replace("." + double.split(".")[1], "")
}
