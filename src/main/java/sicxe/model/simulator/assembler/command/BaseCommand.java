package sicxe.model.simulator.assembler.command;

import org.javatuples.Pair;
import sicxe.model.simulator.assembler.exceptions.NewBaseException;
import sicxe.model.simulator.assembler.exceptions.asm.NoLabelInSymTabException;
import sicxe.model.simulator.assembler.exceptions.asm.TooLargeOperandException;

/**
 * Created by maciek on 23/01/16.
 */
public class BaseCommand extends Command{

    private String baseName;

    public BaseCommand(String label, String baseName) {
        super(label);
        this.baseName = baseName;
    }

    @Override
    public String translate() throws NoLabelInSymTabException, TooLargeOperandException {
        return "";
    }

    @Override
    Integer assignLocation(Integer currentLocation) throws NewBaseException {
        throw new NewBaseException(baseName, currentLocation);
    }
}
