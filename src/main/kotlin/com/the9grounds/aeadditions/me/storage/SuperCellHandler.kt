package com.the9grounds.aeadditions.me.storage

import appeng.api.stacks.AEKeyTypes
import appeng.api.storage.cells.ICellHandler
import appeng.api.storage.cells.ISaveProvider
import appeng.core.localization.Tooltips
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.TextComponent
import net.minecraft.world.item.ItemStack

object SuperCellHandler : ICellHandler {
    override fun isCell(`is`: ItemStack?): Boolean = SuperCellInventory.isCell(`is`)

    override fun getCellInventory(`is`: ItemStack?, host: ISaveProvider?): SuperCellInventory? {
        return SuperCellInventory.createInventory(`is`!!, host)
    }

    fun addCellInformationToTooltip(`is`: ItemStack?, lines: MutableList<Component?>) {
        val handler = getCellInventory(`is`, null) ?: return
        lines.add(Tooltips.bytesUsed(handler.getUsedBytes(), handler.getTotalBytes()))
        lines.add(Tooltips.typesUsed(handler.getStoredItemTypes(), handler.getTotalItemTypes()))
        
        for (keyType in AEKeyTypes.getAll()) {
            lines.add(TextComponent("${handler.numberOfTypesByKeyType[keyType] ?: 0} ${keyType.description.contents} Types"))
        }
    }
}