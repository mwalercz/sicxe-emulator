package sicxe.model.simulator.assembler.command;

/**
 * Created by maciek on 13/01/16.
 */
public class WordCommand extends Command{
    private Integer constant;


    public WordCommand(String label, int constant) {
        super(label);
        this.constant = constant;
    }

    @Override
    public String translate() {
        return Integer.toHexString(constant);
    }

    @Override
    public Integer assignLocation(Integer currentLocation) {
        return currentLocation + 3;
    }
}
