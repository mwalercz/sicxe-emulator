package sicxe.model.simulator.commons;

/**
 * Created by maciek on 25.10.15.
 */
public class Flags {
    public static final int MASK_N = 0x2;
    public static final int MASK_I = 0x1;
    public static final int MASK_X = 0x80;
    public static final int MASK_B = 0x40;
    public static final int MASK_P = 0x20;
    public static final int MASK_E = 0x10;

    public static final int MASK_BP = MASK_B + MASK_P;
    public static final int MASK_NI = MASK_N + MASK_I;
    public static final int BASE_REL = MASK_B;
    public static final int PC_REL = MASK_P;

    public static boolean isBaseRelative(int b) {
        return ((b & MASK_BP) == BASE_REL);
    }

    public static boolean isPCRelative(int b) {
        return ((b & MASK_BP) == PC_REL);
    }

    public static boolean isDirect(int b) {
        return ((b & MASK_BP) == 0x0);
    }

    public static boolean isImmediate(int b) {
        return ((b & MASK_I) == MASK_I);
    }

    public static boolean isIndirect(int b) {
        return ((b & MASK_N) == MASK_N);
    }

    public static boolean isSimple(int b) {
        return ((b & MASK_NI) == MASK_NI) || ((b & MASK_NI) == 0x0);
    }

    public static boolean isIndexed(int b) {
        return ((b & MASK_X) == MASK_X);
    }

    public static boolean isFormatFour(int b) {
        return ((b & MASK_E) == MASK_E);
    }

}
