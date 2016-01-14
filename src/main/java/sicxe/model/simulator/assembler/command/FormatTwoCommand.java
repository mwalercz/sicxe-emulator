package sicxe.model.simulator.assembler.command;

import org.javatuples.Pair;
import sicxe.model.simulator.commons.OpcodeEnum;
import sicxe.model.simulator.machine.register.RegisterEnum;

/**
 * Created by maciek on 12/01/16.
 */
public class FormatTwoCommand implements Command {
    private String r1;
    private String r2;
    private String opcode;
    public FormatTwoCommand(OpcodeEnum opcode, Pair<RegisterEnum, RegisterEnum> registers) {
        this.opcode = Integer.toHexString(opcode.opcode);
        r1 = Integer.toHexString(registers.getValue0().index);
        r2 = Integer.toHexString(registers.getValue1().index);
    }

    @Override
    public String translate() {
        return opcode+r1+r2;
    }
}
