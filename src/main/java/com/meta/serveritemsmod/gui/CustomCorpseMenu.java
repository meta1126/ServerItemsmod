package com.meta.serveritemsmod.gui;

import com.meta.serveritemsmod.gui.ModMenuTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class CustomCorpseMenu extends AbstractContainerMenu {
    private final IItemHandler inventory;
    private static final int INVENTORY_SIZE = 54; // 死体のインベントリスロット数
    private static final int PLAYER_INV_START = INVENTORY_SIZE;
    private static final int PLAYER_HOTBAR_START = PLAYER_INV_START + 27;
    private static final int PLAYER_HOTBAR_END = PLAYER_HOTBAR_START + 9;

    public CustomCorpseMenu(int id, Inventory playerInventory, IItemHandler inventory) {
        super(ModMenuTypes.CUSTOM_CORPSE_MENU.get(), id);
        this.inventory = inventory != null ? inventory : new ItemStackHandler(INVENTORY_SIZE); // `null` を防ぐ

        // 死体のインベントリスロット
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new SlotItemHandler(this.inventory, col + row * 9, 8 + col * 18, 18 + row * 18));
            }
        }

        // プレイヤーのインベントリスロット
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 140 + row * 18));
            }
        }

        // プレイヤーのホットバー
        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(playerInventory, col, 8 + col * 18, 198));
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack movedStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot != null && slot.hasItem()) {
            ItemStack stackInSlot = slot.getItem();
            movedStack = stackInSlot.copy();

            if (index < INVENTORY_SIZE) { // 死体のインベントリ → プレイヤーインベントリ
                if (!this.moveItemStackTo(stackInSlot, PLAYER_INV_START, PLAYER_HOTBAR_END, true)) {
                    return ItemStack.EMPTY;
                }
            } else { // プレイヤーインベントリ → 死体のインベントリ
                if (!this.moveItemStackTo(stackInSlot, 0, INVENTORY_SIZE, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (stackInSlot.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            slot.onTake(player, stackInSlot);
        }

        return movedStack;
    }
}
