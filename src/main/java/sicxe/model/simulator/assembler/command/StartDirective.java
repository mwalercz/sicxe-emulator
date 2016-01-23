package sicxe.model.simulator.assembler.command;

import org.javatuples.Pair;

/**
 * Created by maciek on 14/01/16.
 */
public class StartDirective{

    private Integer startingLocation;
    private String label;
    public StartDirective(String label, Integer operand) {
       this.label = label;
        this.startingLocation = operand;
    }

    public StartDirective() {
    }

    public Pair<String, Integer> getNameAndStartingLocation(){
        return Pair.with(label, startingLocation);
    }
}
