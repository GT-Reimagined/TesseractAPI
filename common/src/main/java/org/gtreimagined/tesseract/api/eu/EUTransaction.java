package org.gtreimagined.tesseract.api.eu;

import org.gtreimagined.tesseract.api.Transaction;

import java.util.function.Consumer;

public class EUTransaction extends Transaction<EUTransaction.TransferData> {

    public final long voltage;
    public long eu;

    public EUTransaction(long voltage, Consumer<TransferData> consumer) {
        super(consumer);
        this.voltage = voltage;
        this.eu = voltage;
    }


    @Override
    public boolean isValid() {
        return this.eu > 0;
    }

    @Override
    public boolean canContinue() {
        return eu > 0;
    }



    public TransferData addData(long eu, long consumedEu, double loss, Consumer<TransferData> data) {
        eu = Math.min(eu, this.eu);
        TransferData dat = this.addData(new TransferData(this, eu, this.voltage).setLoss(loss));
        this.eu -= Math.min(this.eu, consumedEu);
        this.onCommit(data);
        return dat;
    }

    public static class TransferData {
        private final long voltage;
        private long eu;
        private double loss;
        public final EUTransaction transaction;

        public TransferData(EUTransaction transaction, long eu, long voltage) {
            this.voltage = voltage;
            this.loss = 0;
            this.eu = eu;
            this.transaction = transaction;
        }

        public long getEnergy(long amps, boolean input) {
            return input ? (voltage - Math.round(loss)) * amps : voltage * amps;
        }

        public double getLoss() {
            return loss;
        }

        public TransferData setLoss(double loss) {
            this.loss = Math.min(this.voltage, loss);
            return this;
        }

        public long getEu() {
            return eu;
        }

        public long drainEu(long eu) {
            this.eu -= eu;
            return eu;
        }

        @Override
        public String toString() {
            return "Transmit eu: " + this.eu + "  voltage: " + this.voltage + " loss: " + this.loss;
        }

        public long getVoltage() {
            return voltage;
        }

        public EUTransaction getTransaction() {
            return transaction;
        }
    }
}
