package sicxe.model.simulator.assembler.listing;

/**
 * Created by maciek on 23/01/16.
 */
public class ListingLine {
    private String location;
    private String sourceCode;
    private String objectCode;

    public ListingLine(Integer location, String sourceCode, String objectCode) {
        this.location = String.format("%04X",location);
        this.sourceCode = sourceCode;
        this.objectCode = objectCode;
    }

    public ListingLine() {
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public String getObjectCode() {
        return objectCode;
    }

    public void setObjectCode(String objectCode) {
        this.objectCode = objectCode;
    }
}
