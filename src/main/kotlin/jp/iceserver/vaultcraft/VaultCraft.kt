package jp.iceserver.vaultcraft

class VaultCraft : AbstractVaultCraft()
{
    companion object
    {
        lateinit var plugin: VaultCraft
    }

    override fun onEnable()
    {
        plugin = this
    }
}