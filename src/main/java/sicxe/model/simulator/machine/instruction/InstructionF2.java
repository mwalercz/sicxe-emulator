package sicxe.model.simulator.machine.instruction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import sicxe.model.simulator.commons.OpcodeEnum;
import sicxe.model.simulator.machine.register.IntegerRegister;
import sicxe.model.simulator.machine.register.RegisterEnum;

/**
 * Created by maciek on 24.10.15.
 */
public class InstructionF2 extends Instruction{
    private OpcodeEnum opcodeEnum;
    private RegisterEnum r1;
    private RegisterEnum r2;

    public InstructionF2(RawInstruction instruction) {
        this.opcodeEnum = instruction.getOpcodeEnum();
        this.r1 = RegisterEnum.get((instruction.getByteTwo() & 0xf0) >> 4);
        this.r2 = RegisterEnum.get(instruction.getByteTwo() & 0x0f);
    }

    public InstructionF2(Integer address, OpcodeEnum opcode, String rawCode, RawInstruction instruction) {
        super(address, rawCode, opcode.name());
        this.r1 = RegisterEnum.get((instruction.getByteTwo() & 0xf0) >> 4);
        this.r2 = RegisterEnum.get(instruction.getByteTwo() & 0x0f);
    }

    @JsonIgnore
    public OpcodeEnum getOpcodeEnum() {
        return opcodeEnum;
    }

    public void setOpcodeEnum(OpcodeEnum opcodeEnum) {
        this.opcodeEnum = opcodeEnum;
    }

    @JsonIgnore
    public RegisterEnum getR1() {
        return r1;
    }

    @JsonIgnore
    public RegisterEnum getR2() {
        return r2;
    }

    @Override
    public String getOperand() {
        if(r2 != null)
                return r1.name() + "," + r2.name();
        else return r1.name();
    }

    @Override
    public Integer length() {
        return 2;
    }
}
