package sicxe.model.simulator.assembler.command.bits;

/**
 * Created by maciek on 14/01/16.
 */
public class FormatFourBits extends Bits{
    private final int e = 1 << 20;
    private final int b = 0;
    private final int p = 0;
    private int x = 0;

    public int getX() {
        return x;
    }

    public void setX() {
        this.x = 1 << 23;
    }

    public void zeroX(){
        this.x = 0;
    }

    public int getE() {
        return e;
    }

    public int getB() {
        return b;
    }

    public int getP() {
        return p;
    }
}
