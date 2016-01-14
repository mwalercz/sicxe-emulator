package sicxe.model.simulator.assembler.command;

import sicxe.model.simulator.commons.OpcodeEnum;

/**
 * Created by maciek on 12/01/16.
 */
public class FormatOneCommand implements Command {
    private OpcodeEnum opcode;
    public FormatOneCommand(OpcodeEnum opcode) {
        this.opcode = opcode;
    }

    @Override
    public String translate() {
        return Integer.toHexString(opcode.opcode);
    }
}
