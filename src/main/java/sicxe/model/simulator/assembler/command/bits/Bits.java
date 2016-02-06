package sicxe.model.simulator.assembler.command.bits;

/**
 * Created by maciek on 14/01/16.
 */
public class Bits {
    private int n = 0;
    private int i = 0;

    public int getN() {
        return n;
    }

    public void setN() {
        this.n = 1 << 1;
    }
    public void zeroN(){
        this.n = 0;
    }

    public int getI() {
        return i;
    }

    public void setI() {
        this.i = 1;
    }

    public void zeroI(){
        this.i = 0;
    }
}
