package sicxe.model.simulator.assembler.command;

import sicxe.model.simulator.commons.OpcodeEnum;

/**
 * Created by maciek on 12/01/16.
 */
public class FormatOneCommand extends Command {
    private OpcodeEnum opcode;
    public FormatOneCommand(OpcodeEnum opcode, String label) {
        super(label);
        this.opcode = opcode;
    }

    @Override
    public String translate() {
        return String.format("%02X",opcode.opcode);
    }

    @Override
    public Integer assignLocation(Integer currentLocation) {
        return currentLocation + 1;
    }
}
