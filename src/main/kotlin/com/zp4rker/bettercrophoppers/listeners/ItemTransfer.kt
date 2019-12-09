package com.zp4rker.bettercrophoppers.listeners

import com.zp4rker.bettercrophoppers.hopperName
import com.zp4rker.bettercrophoppers.utils.removeItems
import org.bukkit.block.Hopper
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryMoveItemEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.metadata.FixedMetadataValue
import org.bukkit.plugin.java.JavaPlugin

class ItemTransfer(private val plugin: JavaPlugin): Listener {

    @EventHandler
    fun onTransfer(event: InventoryMoveItemEvent) {
        if (event.source.holder !is Hopper) return

        val hopper = event.source.holder as Hopper
        if (hopper.inventory.name == hopperName && !hopper.hasMetadata("crophopper")) hopper.setMetadata("crophopper", FixedMetadataValue(plugin, true))

        event.isCancelled = true

        plugin.server.scheduler.scheduleSyncDelayedTask(plugin, {
            val remainder = hopper.inventory.removeItems(ItemStack(event.item.type, 64))
            event.destination.addItem(ItemStack(event.item.type, 64 - remainder))
        }, 1)
    }

}