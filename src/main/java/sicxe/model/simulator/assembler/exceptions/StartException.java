package sicxe.model.simulator.assembler.exceptions;

/**
 * Created by maciek on 12/01/16.
 */
public class StartException extends Throwable{
    private Integer startLocation;
    private String programName;

    public StartException(Integer startLocation, String programName) {
        this.startLocation = startLocation;
        this.programName = programName;
    }
}
