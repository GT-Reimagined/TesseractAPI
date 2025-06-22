package tesseract.api.gt;

public class GTState {
    long ampsReceived;
    long ampsSent;
    long euReceived;
    long euSent;
    public final IEnergyHandler handler;

    public GTState(IEnergyHandler handler) {
        ampsReceived = 0;
        euReceived = 0;
        this.handler = handler;
    }

    public void onTick() {
        ampsReceived = 0;
        euReceived = 0;
        ampsSent = 0;
        euSent = 0;
    }

    public long extract(boolean simulate, long amps) {
        if (handler.canOutput()) {
            if (simulate) {
                return Math.min(amps, handler.getOutputAmperage() - (ampsSent));
            }
            if (ampsSent + amps > handler.getOutputAmperage()) {
                return 0;
            }
            if (!simulate) {
                ampsSent += amps;
            }
            return amps;
        }
        return 0;
    }

    public long receive(boolean simulate, long amps) {
        if (handler.canInput()) {
            if (simulate) {
                return Math.min(amps, handler.getInputAmperage() - (ampsReceived));
            }
            if (ampsReceived + amps > handler.getInputAmperage()) {
                return 0;
            }
            if (!simulate) {
                ampsReceived += amps;
            }
            return amps;
        }
        return 0;
    }

    public long getAmpsReceived() {
        return ampsReceived;
    }

    public long getAmpsSent() {
        return ampsSent;
    }
}
