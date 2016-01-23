package sicxe.model.simulator.assembler.command;

/**
 * Created by maciek on 13/01/16.
 */
public class ReswCommand extends Command {
    private Integer absoluteOperand;

    public ReswCommand(String operand, Integer absoluteOperand) {
        super(operand);
        this.absoluteOperand = absoluteOperand;
    }

    @Override
    public String translate() {
        return "";
    }

    @Override
    public Integer assignLocation(Integer currentLocation) {
        return currentLocation + 3 * absoluteOperand;

    }
}
