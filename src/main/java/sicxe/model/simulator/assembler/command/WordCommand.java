package sicxe.model.simulator.assembler.command;

/**
 * Created by maciek on 13/01/16.
 */
public class WordCommand implements Command{
    private String hex;


    public WordCommand(int constant) {
        hex = Integer.toHexString(constant);
    }

    @Override
    public String translate() {
        return hex;
    }
}
