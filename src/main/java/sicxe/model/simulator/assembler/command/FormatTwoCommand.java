package sicxe.model.simulator.assembler.command;

import org.javatuples.Pair;
import sicxe.model.simulator.commons.OpcodeEnum;
import sicxe.model.simulator.machine.register.RegisterEnum;

/**
 * Created by maciek on 12/01/16.
 */
public class FormatTwoCommand extends Command {
    private String r1;
    private String r2;
    private String opcode;
    public FormatTwoCommand(OpcodeEnum opcode, String label, Pair<RegisterEnum, RegisterEnum> registers) {
        super(label);
        this.opcode = String.format("%01X",opcode.opcode);
        r1 = registers.getValue0().index.toString();
        if(registers.getValue1() == null) r2 = "0";
        else r2 = registers.getValue1().index.toString();
    }

    @Override
    public String translate() {
        return opcode+r1+r2;
    }

    @Override
    public Integer assignLocation(Integer currentLocation) {
        return currentLocation + 2;

    }
}
