package sicxe.model.simulator.machine.instruction;

import sicxe.model.simulator.commons.SICXE;

/**
 * Created by maciek on 25/01/16.
 */
public class InstructionF4 extends Instruction {
    private InstructionFlags flags;
    private Integer operand;
    private String prefix = "";
    private String suffix = "";

    public InstructionF4(Integer address, String rawCode, String mnemonic, InstructionFlags flags, Integer operand) {
        super(address, rawCode, mnemonic);
        this.flags = flags;
        this.operand = operand;
        if (flags.isImmediete()) this.prefix = "#";
        if (flags.isIndirect()) this.prefix = "@";

        if (flags.isIndexed()) this.suffix = ",X";
    }

    @Override
    public String getOperand() {
        return prefix + operand + suffix;
    }

    @Override
    public Integer length() {
        return 4;
    }
}
