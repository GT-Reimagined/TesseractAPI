package org.gtreimagined.tesseract.api.capability;


import com.hypixel.hytale.builtin.hytalegenerator.patterns.SurfacePattern.Facing;

@FunctionalInterface
public interface ITransactionModifier {
    boolean modify(Object stack, Facing side, boolean input, boolean simulate);

    ITransactionModifier EMPTY = (a,b,c,d) -> false;
}
