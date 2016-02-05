package sicxe.model.simulator.assembler.objectprogram;

/**
 * Created by maciek on 13/01/16.
 */
public class HeaderRecord {
    private String programName;
    private Integer startingAddress;
    private Integer programLength;

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public Integer getStartingAddress() {
        return startingAddress;
    }

    public void setStartingAddress(Integer startingAddress) {
        this.startingAddress = startingAddress;
    }

    public HeaderRecord(String programName, Integer startingAddress) {
        this.programName = programName;
        this.startingAddress = startingAddress;
    }
}
