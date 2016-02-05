package sicxe.model.simulator.machine.instruction;

import sicxe.model.simulator.commons.*;

/**
 * Created by maciek on 24.10.15.
 */

public class RawInstruction {
    private OpcodeEnum opcodeEnum;
    private Integer byteOne;
    private Integer byteTwo;
    private Integer byteThree;
    private Integer byteFour;

    public RawInstruction() {
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
            OpcodeEnum tempEnum = OpcodeTable.getInstance().getOpcode(this.byteOne);
            if (tempEnum.format == FormatEnum.F1 || tempEnum.format == FormatEnum.F2) {
                opcodeEnum = tempEnum;
                return true;
            }
        }
        return false;
    }

    public boolean isFormatOne() {
        return opcodeEnum.format.equals(FormatEnum.F1);
    }

    public boolean isFormatTwo() {
        return opcodeEnum.format.equals(FormatEnum.F3);
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

    public boolean isFormatThree() {
        return !Flags.isFormatFour(getByteTwo());
    }

    public Integer getOperandFormatFour() {
        return SICXE.convert20BitsIntoInt(byteTwo, byteThree, byteFour);
    }


    public Integer getOperandFormatThree() {
        return SICXE.convert12BitsIntoInt(byteTwo, byteThree);

    }

    public String getRaw() {
        if (byteOne != null && byteTwo != null && byteThree != null && byteFour != null) {
            return SICXE.toHex(8, (byteOne << 24) | (byteTwo << 16) | (byteThree << 8) | byteFour);
        } else if (byteOne != null && byteTwo != null && byteThree != null) {
            return SICXE.toHex(6, (byteOne << 16) | (byteTwo << 8) | byteThree);
        } else if (byteOne != null && byteTwo != null){
            return SICXE.toHex(4, (byteOne << 8) | byteTwo);
        } else return SICXE.toHex(2, byteOne);

    }

}
