package tesseract.api.fe;

import tesseract.api.Transaction;

import java.util.function.Consumer;

public class FETransaction extends Transaction<Integer> {
    public int rf;
    public FETransaction(int rf, Consumer<Integer> consumed) {
        super(consumed);
        this.rf = rf;
    }

    public void addData(int rf, Consumer<Integer> consumer){
        this.addData(rf);
        this.rf -= rf;
        this.onCommit(consumer);
    }

    @Override
    public boolean isValid() {
        return rf > 0;
    }

    @Override
    public boolean canContinue() {
        return rf > 0;
    }
}
