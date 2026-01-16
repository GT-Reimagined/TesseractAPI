package org.gtreimagined.tesseract.api.eu;

import com.hypixel.hytale.server.core.universe.world.World;

/**
 * Interface for handling an electric events. (Controller will handle them)
 */
public interface IEUEvent {

    /**
     * Executes when the node trying to receive higher amount of voltage than can.
     *
     * @param world   The world.
     * @param pos     The node position.
     * @param voltage The current voltage.
     */
    default void onNodeOverVoltage(World world, long pos, long voltage) {
        //NOOP
    }

    /**
     * Executes when the cable trying to transport higher amount of voltage than can.
     *
     * @param world   The world.
     * @param pos     The cable position.
     * @param voltage The current voltage.
     */
    default void onCableOverVoltage(World world, long pos, long voltage) {
        //NOOP
    }

    /**
     * Executes when the cable trying to transport higher amount of amperage than can.
     *
     * @param world    The world.
     * @param pos      The cable position.
     * @param amperage The current amperage.
     */
    default void onCableOverAmperage(World world, long pos, long amperage) {
        //NOOP
    }
}
