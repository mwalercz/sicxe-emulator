package sicxe.model.simulator.machine.instruction;

import sicxe.model.simulator.commons.Flags;
import sicxe.model.simulator.commons.OpcodeEnum;

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

    private boolean formatFour;


    public InstructionFlags() {
    }
    public InstructionFlags(Instruction instruction) {
        this.opcodeEnum = instruction.getOpcodeEnum();
        this.direct = Flags.isDirect(instruction.getByteTwo());
        this.pcRelative = Flags.isPCRelative(instruction.getByteTwo());
        this.baseRelative = Flags.isBaseRelative(instruction.getByteTwo());
        this.indexed = Flags.isIndexed(instruction.getByteTwo());
        this.immediete = Flags.isImmediate(instruction.getByteOne());
        this.indirect = Flags.isIndirect(instruction.getByteOne());
        this.simple = Flags.isSimple(instruction.getByteOne());
        this.formatFour = Flags.isFormatFour(instruction.getByteTwo());

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

    public boolean isFormatFour() {
        return formatFour;
    }

    public void setFormatFour(boolean formatFour) {
        this.formatFour = formatFour;
    }

    public void setOpcodeEnum(OpcodeEnum opcodeEnum) {
        this.opcodeEnum = opcodeEnum;
    }

    public void setDirect(boolean direct) {
        this.direct = direct;
    }

    public void setPcRelative(boolean pcRelative) {
        this.pcRelative = pcRelative;
    }

    public void setBaseRelative(boolean baseRelative) {
        this.baseRelative = baseRelative;
    }

    public void setIndexed(boolean indexed) {
        this.indexed = indexed;
    }

    public void setImmediete(boolean immediete) {
        this.immediete = immediete;
    }

    public void setIndirect(boolean indirect) {
        this.indirect = indirect;
    }

    public void setSimple(boolean simple) {
        this.simple = simple;
    }
}
