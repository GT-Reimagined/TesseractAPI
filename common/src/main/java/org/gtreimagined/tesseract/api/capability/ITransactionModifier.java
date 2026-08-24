package org.gtreimagined.tesseract.api.capability;

import net.minecraft.core.Direction;
import org.jspecify.annotations.NullMarked;

@NullMarked
@FunctionalInterface
public interface ITransactionModifier {
    boolean modify(Object stack, Direction side, boolean input, boolean simulate);

    ITransactionModifier EMPTY = (a,b,c,d) -> false;
}
