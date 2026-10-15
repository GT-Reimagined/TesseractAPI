package org.gtreimagined.tesseract.api.neoforge;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.gtreimagined.tesseract.api.Serializable;
import org.jspecify.annotations.Nullable;

public class Provider<T> implements ICapabilityProvider, INBTSerializable<Tag> {
    private final LazyOptional<T> optional;
    private final Capability<T> capability;

    public Provider(Capability<T> capability, @Nullable NonNullSupplier<T> supplier) {
        this.optional = LazyOptional.of(supplier);
        this.capability = capability;
    }

    @Override
    public <C> LazyOptional<C> getCapability(Capability<C> cap, @Nullable Direction side) {
        return cap == capability ? optional.cast() : LazyOptional.empty();
    }

    @Override
    public Tag serializeNBT() {
        return optional.map(t -> {
            if (t instanceof INBTSerializable<?> it){
                return it.serializeNBT();
            } else if (t instanceof Serializable it){
                return it.serialize(new CompoundTag());
            }
            return new CompoundTag();
        }).orElse(new CompoundTag());
    }

    @Override
    public void deserializeNBT(Tag nbt) {
        optional.ifPresent(t -> {
            if (t instanceof INBTSerializable it){
                it.deserializeNBT(nbt);
            } else if (t instanceof Serializable it && nbt instanceof CompoundTag tag){
                it.deserialize(tag);
            }
        });
    }

}
