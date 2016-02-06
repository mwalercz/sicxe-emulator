package sicxe.model.simulator.assembler.command;

/**
 * Created by maciek on 13/01/16.
 */
public class ResbCommand extends Command {
    private Integer absoluteOperand;

    public ResbCommand(String label, Integer absolute) {
        super(label);
        absoluteOperand = absolute;
    }

    @Override
    public String translate() {
        return "";
    }

    @Override
    public Integer assignLocation(Integer currentLocation) {
        return currentLocation + absoluteOperand;
    }
}
