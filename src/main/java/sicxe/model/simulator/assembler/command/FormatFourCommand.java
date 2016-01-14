package sicxe.model.simulator.assembler.command;

import sicxe.model.simulator.assembler.SymTab;
import sicxe.model.simulator.assembler.command.bits.Bits;
import sicxe.model.simulator.assembler.command.bits.FormatFourBits;
import sicxe.model.simulator.commons.OpcodeEnum;

/**
 * Created by maciek on 13/01/16.
 */
public class FormatFourCommand implements Command {
    private SymTab symTab;
    private Bits bits = new FormatFourBits();
    private OpcodeEnum opcode;
    private String operand;

    public FormatFourCommand(OpcodeEnum opcode, String operand, SymTab symTab) {
        this.opcode = opcode;
        this.operand = operand;
        this.symTab = symTab;
    }


    @Override
    public String translate() {
        if(isOperandInteger(operand))
    }
}
