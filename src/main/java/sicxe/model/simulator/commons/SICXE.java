package sicxe.model.simulator.commons;

import sicxe.model.simulator.commons.exceptions.OutOfRangeException;

/**
 * Created by maciek on 24.10.15.
 */
public class SICXE {

    public static final int MAX_UNSIGNED = (1 << 20) - 1;
    public static final int MIN_UNSIGNED = 0;

    public static final long FRACTION_MASK = 0x000fffffffffffffL;
    public static final long EXP_MASK = 0xfff0000000000000L;

    public static final int MAX_SIGNED = (1 << 19) - 1;
    public static final int MIN_SIGNED = -(1 << 19);

    public static final int MAX_MEMORY = (1 << 15);

    public static final int MIN_ADDRESS = 0;
    public static final int MAX_ADDRESS = MAX_MEMORY - 1;

    public static Integer convertIntToUnsignedWord(int value) throws OutOfRangeException {
        if (value <= MAX_UNSIGNED && value >= MIN_UNSIGNED) {
            return value;
        } else throw new OutOfRangeException();
    }

    public static Integer convertWordToSignedInt(int value) {
        if (value >= 0 && value <= MAX_SIGNED) {
            return value;
        } else {
            return ~value + 1;
        }
    }

    public static int convertSignedWordToInt(int value) {
        return value;
    }

    public static int convert24BitsIntoInt(byte[] b) {
        return (((b[0] & 0xFF) << 16) | ((b[1] & 0xFF) << 8) | (b[2] & 0xFF));
    }

    public static int convert12BitsIntoInt(byte[] b) {
        return (((b[0] & 0x0F) << 8) | (b[1] & 0xFF));
    }

    public static int convert12BitsIntoInt(int a, int b) {
        byte[] table = new byte[2];
        table[0] = (byte) a;
        table[1] = (byte) b;
        return convert12BitsIntoInt(table);
    }

    public static int convert20BitsIntoInt(byte[] b) {
        return ((b[0] & 0xF) << 16) | ((b[1] & 0xFF) << 8) | (b[2] & 0xFF);
    }


    public static int convert20BitsIntoInt(int a, int b, int c) {
        byte[] table = new byte[3];
        table[0] = (byte) a;
        table[1] = (byte) b;
        table[2] = (byte) c;
        return convert20BitsIntoInt(table);
    }


    public static int convertByteIntoF34Opcode(int b) {
        return (b & 0b11111100) >>> 2;
    }

    public static double convertDoubleToFloat(double value) {
        long bitVal = Double.doubleToRawLongBits(value);
        long fraction = bitVal & FRACTION_MASK;
        long shiftedFraction = (fraction >> 16) << 16;
        bitVal = bitVal & EXP_MASK;
        return Double.longBitsToDouble(bitVal | shiftedFraction);
    }

    public static double convertLongToFloat(long value) {
        Double temp = Double.longBitsToDouble(value << 16);
        return convertDoubleToFloat(temp);
    }

    public static long convertFloatToLong(Double value) {
        long bitVal = Double.doubleToRawLongBits(value);
        long fraction = bitVal & FRACTION_MASK;
        long shiftedFraction = (fraction >> 16) << 16;
        bitVal = bitVal & EXP_MASK;
        return bitVal | shiftedFraction;

    }

    public static double convertFToDouble(double value) {
        return value;
    }




}
