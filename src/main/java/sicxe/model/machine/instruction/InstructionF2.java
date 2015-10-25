package sicxe.model.machine.instruction;

import sicxe.model.commons.OpcodeEnum;

/**
 * Created by maciek on 24.10.15.
 */
public class InstructionF2 {
    private OpcodeEnum opcodeEnum;
    private int r1;
    private int r2;

    public InstructionF2(Instruction instruction) {
        this.opcodeEnum = instruction.getOpcodeEnum();
        this.r1 = (instruction.getByteTwo() & 0xf0) >> 4;
        this.r2 = (instruction.getByteTwo() & 0x0f);
    }

    public OpcodeEnum getOpcodeEnum() {
        return opcodeEnum;
    }

    public void setOpcodeEnum(OpcodeEnum opcodeEnum) {
        this.opcodeEnum = opcodeEnum;
    }

    public int getR1() {
        return r1;
    }

    public int getR2() {
        return r2;
    }
}
