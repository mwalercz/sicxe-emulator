package sicxe.model.simulator.assembler.objectprogram;

/**
 * Created by maciek on 24/01/16.
 */
public class Instruction {
    private String objectCode;
    private Integer location;

    public Instruction(String objectCode, Integer location) {
        this.objectCode = objectCode;
        this.location = location;
    }

    public String getObjectCode() {
        return objectCode;
    }

    public void setObjectCode(String objectCode) {
        this.objectCode = objectCode;
    }

    public Integer getLocation() {
        return location;
    }

    public void setLocation(Integer location) {
        this.location = location;
    }

    public Integer length(){
        return objectCode.length()/2;
    }
}
