package sicxe.model.simulator.assembler.command;

import sicxe.model.simulator.assembler.exceptions.asm.NoLabelInSymTabException;
import sicxe.model.simulator.assembler.exceptions.asm.TooLargeOperandException;

/**
 * Created by maciek on 23/01/16.
 */
public class BaseCommand extends Command{

    public BaseCommand(String label) {
        super(label);
    }

    @Override
    public String translate() throws NoLabelInSymTabException, TooLargeOperandException {
        return "";
    }

    @Override
    Integer assignLocation(Integer currentLocation) {
        return currentLocation;
    }
}
