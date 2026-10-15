package org.gtreimagined.tesseract.api.fabric.wrapper;

import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.gtreimagined.tesseract.api.context.TesseractItemContext;

public class ContainerItemContextWrapper implements TesseractItemContext {
    private final ContainerItemContext context;
    public ContainerItemContextWrapper(ContainerItemContext context){
        this.context = context;

    }


    @Override
    public DataComponentMap getComponents() {
        return context.getItemVariant().getComponentMap();
    }

    @Override
    public Item getItem() {
        return context.getItemVariant().getItem();
    }

    @Override
    public int getCount() {
        return (int) context.getAmount();
    }

    @Override
    public void setItemStack(ItemStack stack) {
        try(Transaction transaction = Transaction.openOuter()) {
            context.exchange(ItemVariant.of(stack), stack.getCount(), transaction);
            transaction.commit();
        }
    }
}
