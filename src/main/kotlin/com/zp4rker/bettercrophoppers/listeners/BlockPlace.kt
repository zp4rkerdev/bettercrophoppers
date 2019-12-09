package com.zp4rker.bettercrophoppers.listeners

import com.zp4rker.bettercrophoppers.hopperName
import org.bukkit.ChatColor
import org.bukkit.block.Hopper
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.metadata.FixedMetadataValue
import org.bukkit.plugin.java.JavaPlugin

class BlockPlace(private val plugin: JavaPlugin) : Listener {

    @EventHandler
    fun onPlace(event: BlockPlaceEvent) {
        if (event.blockPlaced.state is Hopper && event.itemInHand.itemMeta.displayName == hopperName) {

            if (event.blockPlaced.chunk.tileEntities.filterIsInstance<Hopper>().any { it.inventory.name == hopperName }) {
                event.isCancelled = true
                event.player.sendMessage("${ChatColor.RED}There can only be one $hopperName ${ChatColor.RED}per chunk!")
                return
            }

            event.blockPlaced.setMetadata("crophopper", FixedMetadataValue(plugin, true))
        }
    }

}