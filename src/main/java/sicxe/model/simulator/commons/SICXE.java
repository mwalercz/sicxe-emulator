package sicxe.model.simulator.commons;

import sicxe.model.simulator.assembler.exceptions.asm.TooLargeOperandException;
import sicxe.model.simulator.commons.exceptions.DoesntFit12BitsException;
import sicxe.model.simulator.commons.exceptions.OutOfRangeException;

/**
 * Created by maciek on 24.10.15.
 */
public class SICXE {

    public static final int MAX_SIGNED_12BITS = (1 << 11) - 1;
    public static final int MIN_SIGNED_12BITS = -(1 << 11);
    public static final int MAX_UNSIGNED_12BITS = (1 << 12) - 1;
    public static final int MIN_UNSIGNED_12BITS = 0;

    public static final int MASK_OPCODE = 0b11111100;

    public static final int MASK_12BITS = 0x00000fff;
    public static final int MASK_20BITS = 0x000fffff;

    public static final int MAX_UNSIGNED = (1 << 24) - 1;
    public static final int MIN_UNSIGNED = 0;

    public static final long FRACTION_MASK = 0x000fffffffffffffL;
    public static final long EXP_MASK = 0xfff0000000000000L;

    public static final int MAX_SIGNED = (1 << 23) - 1;
    public static final int MIN_SIGNED = -(1 << 23);

    public static final int MAX_MEMORY = (1 << 20);

    public static final int MIN_ADDRESS = 0;
    public static final int MAX_ADDRESS = MAX_MEMORY - 1;

    public static Integer convertIntToUnsignedWord(int value) throws OutOfRangeException {
        if (value <= MAX_UNSIGNED && value >= MIN_UNSIGNED) {
            return value;
        } else throw new OutOfRangeException();
    }

    public static Integer convertIntToSignedWord(int value) throws OutOfRangeException {
        if (value <= MAX_SIGNED && value >= MIN_SIGNED) {
            return value;
        } else throw new OutOfRangeException();
    }

    public static Integer convertWordToSignedInt(int value) {
        if (value >= MIN_SIGNED && value <= MAX_SIGNED) {
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

    public static int convert12BitsIntoSignedInt(int a) throws TooLargeOperandException {
        if (fitsSigned12Bits(a)) {
            return a & MASK_12BITS;
        } else throw new TooLargeOperandException();

    }

    public static boolean fitsSigned12Bits(Integer a) {
        return (a <= MAX_SIGNED_12BITS && a >= MIN_SIGNED_12BITS);
    }



    public static int convert12BitsIntoInt(int a, int b) {
        return (((a & 0x0F) << 8) | (b & 0xFF));
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
        return (0b11111100 & b);
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


    public static boolean fitsUnsigned12Bits(Integer baseRelativeOperand) {
        return (baseRelativeOperand >= MIN_UNSIGNED_12BITS
                && baseRelativeOperand <= MAX_UNSIGNED_12BITS);
    }

    public static Integer convertToSigned12BitInt(Integer value) {
        if (value >= 0 && value <= MAX_SIGNED_12BITS) {
            return value;
        } else
            return value - MAX_UNSIGNED_12BITS - 1;
    }

    public static String toHex(Integer digits, Integer value){
        return String.format("%0"+digits +"X", value);
    }
    public static String toHex(Integer digits, Long value){
        return String.format("%0"+digits +"X", value);
    }

}
