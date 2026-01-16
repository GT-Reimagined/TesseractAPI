package org.gtreimagined.tesseract.api.eu;


import org.gtreimagined.tesseract.api.Direction;

public interface IEnergyHandlerProvider {
    IEnergyHandler getEnergyHandler(Direction side);
}
