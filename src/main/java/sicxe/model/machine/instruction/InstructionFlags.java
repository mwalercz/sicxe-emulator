package sicxe.model.machine.instruction;

import sicxe.model.commons.Flags;
import sicxe.model.commons.OpcodeEnum;

/**
 * Created by maciek on 25.10.15.
 */
public class InstructionFlags {
    private OpcodeEnum opcodeEnum;
    private boolean direct;
    private boolean pcRelative;
    private boolean baseRelative;

    private boolean indexed;

    private boolean immediete;
    private boolean indirect;
    private boolean simple;


    public InstructionFlags(Instruction instruction) {
        this.opcodeEnum = instruction.getOpcodeEnum();
        this.direct = Flags.isDirect(instruction.getByteTwo());
        this.pcRelative = Flags.isPCRelative(instruction.getByteTwo());
        this.baseRelative = Flags.isBaseRelative(instruction.getByteTwo());
        this.indexed = Flags.isIndexed(instruction.getByteTwo());
        this.immediete = Flags.isImmediate(instruction.getByteOne());
        this.indirect = Flags.isIndirect(instruction.getByteOne());
        this.simple = Flags.isSimple(instruction.getByteOne());

    }

    public OpcodeEnum getOpcodeEnum() {
        return opcodeEnum;
    }

    public boolean isDirect() {
        return direct;
    }

    public boolean isPcRelative() {
        return pcRelative;
    }

    public boolean isBaseRelative() {
        return baseRelative;
    }

    public boolean isIndexed() {
        return indexed;
    }

    public boolean isImmediete() {
        return immediete;
    }

    public boolean isIndirect() {
        return indirect;
    }

    public boolean isSimple() {
        return simple;
    }


}
