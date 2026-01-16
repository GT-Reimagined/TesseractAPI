package org.gtreimagined.tesseract.api.capability;


import org.gtreimagined.tesseract.api.Direction;

@FunctionalInterface
public interface ITransactionModifier {
    boolean modify(Object stack, Direction side, boolean input, boolean simulate);

    ITransactionModifier EMPTY = (a,b,c,d) -> false;
}
