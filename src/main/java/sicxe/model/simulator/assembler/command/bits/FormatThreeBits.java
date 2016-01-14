package sicxe.model.simulator.assembler.command.bits;

/**
 * Created by maciek on 14/01/16.
 */
public class FormatThreeBits extends Bits{
    private final int e = 0;
    private int x = 0;
    private int b = 0;
    private int p = 0;

    public int getE() {
        return e;
    }

    public int getX() {
        return x;
    }

    public void setX() {
        this.x = 1 << 15;
    }


    public int getB() {
        return b;
    }

    public void setB() {
        this.b = 1 << 14;
    }

    public int getP() {
        return p;
    }

    public void setP() {
        this.p = 1 << 13;
    }
}
