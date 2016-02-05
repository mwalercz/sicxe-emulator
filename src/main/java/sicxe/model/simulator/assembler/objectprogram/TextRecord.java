package sicxe.model.simulator.assembler.objectprogram;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by maciek on 13/01/16.
 */
public class TextRecord {
    private final Integer maxObjectCodeLength = 0x1F;
    private Integer startingAddress;
    private String objectCode = "";

    @JsonIgnore
    public Integer getMaxObjectCodeLength() {
        return maxObjectCodeLength;
    }

    public void setStartingAddress(Integer startingAddress) {
        this.startingAddress = startingAddress;
    }

    public String getObjectCode() {
        return objectCode;
    }

    public void setObjectCode(String objectCode) {
        this.objectCode = objectCode;
    }

    public boolean hasSpaceForNewInstruction(Integer instructionLength){
        return ((getObjectCodeLength() + instructionLength) <= maxObjectCodeLength);
    }

    public void storeObjectCode(String instruction){
        objectCode = objectCode + instruction;
    }

    public Integer getObjectCodeLength(){
        return objectCode.length()/2;
    }
    public Integer getStartingAddress() {
        return startingAddress;
    }

    public TextRecord(Integer startingAddress) {
        this.startingAddress = startingAddress;
    }
}
