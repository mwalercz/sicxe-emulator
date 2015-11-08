package sicxe.model.machine.instruction;

import sicxe.model.commons.*;

/**
 * Created by maciek on 24.10.15.
 */

public class Instruction {
    private OpcodeEnum opcodeEnum;
    private int byteOne;
    private int byteTwo;
    private int byteThree;
    private int byteFour;

    public Instruction() {
    }

    public int getByteOne() {
        return byteOne;
    }

    public void setByteOne(int byteOne) {
        this.byteOne = byteOne;
    }

    public int getByteTwo() {
        return byteTwo;
    }

    public void setByteTwo(int byteTwo) {
        this.byteTwo = byteTwo;
    }

    public int getByteThree() {
        return byteThree;
    }

    public void setByteThree(int byteThree) {
        this.byteThree = byteThree;
    }

    public int getByteFour() {
        return byteFour;
    }

    public void setByteFour(int byteFour) {
        this.byteFour = byteFour;
    }

    public OpcodeEnum getOpcodeEnum() {
        return opcodeEnum;
    }

    public boolean isOpcodeValidForFormatOneAndTwo() {
        if (OpcodeTable.getInstance().isValid(byteOne)) {
            OpcodeEnum tempEnum = OpcodeTable.getInstance().getOpcode(byteOne);
            if (tempEnum.format == FormatEnum.F1 || tempEnum.format == FormatEnum.F2) {
                opcodeEnum = tempEnum;
                return true;
            }
        }
        return false;
    }

    public boolean isOpcodeValidForFormatThreeAndFour() {
        int F34Opcode = SICXE.convertByteIntoF34Opcode(byteOne);
        if (OpcodeTable.getInstance().isValid(F34Opcode)) {
            opcodeEnum = OpcodeTable.getInstance().getOpcode(F34Opcode);
            return true;
        } else return false;
    }

    public boolean isFormatFour() {
        return Flags.isFormatFour(getByteTwo());
    }

    public Integer getOperandFormatFour() {
        return SICXE.convert20BitsIntoInt(byteTwo, byteThree, byteFour);

    }


    public Integer getOperandFormatThree() {
        return SICXE.convert12BitsIntoInt(byteTwo, byteThree);

    }

}
