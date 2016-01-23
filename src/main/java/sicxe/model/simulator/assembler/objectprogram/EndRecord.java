package sicxe.model.simulator.assembler.objectprogram;

/**
 * Created by maciek on 13/01/16.
 */
public class EndRecord {
    private Integer startingAddress;

    public Integer getStartingAddress() {
        return startingAddress;
    }

    public void setStartingAddress(Integer startingAddress) {
        this.startingAddress = startingAddress;
    }

    public EndRecord(Integer startingAddress) {
        this.startingAddress = startingAddress;
    }
}
