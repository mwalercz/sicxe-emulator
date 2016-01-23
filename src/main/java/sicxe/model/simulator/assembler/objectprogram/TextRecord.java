package sicxe.model.simulator.assembler.objectprogram;

/**
 * Created by maciek on 13/01/16.
 */
public class TextRecord {
    private final Integer maxObjectCodeLength = 31;
    private Integer startingAddress;
    private String objectCode = "";

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
        return ((objectCode.length() + instructionLength) <= maxObjectCodeLength);
    }

    public void storeInstruction(String instruction){
        objectCode = objectCode + instruction;
    }

    public Integer getObjectCodeLength(){
        return objectCode.length();
    }
    public Integer getStartingAddress() {
        return startingAddress;
    }

    public TextRecord(Integer startingAddress) {
        this.startingAddress = startingAddress;
    }
}
