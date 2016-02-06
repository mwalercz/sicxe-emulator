package sicxe.model.simulator.assembler;


/**
 * Created by maciek on 11/01/16.
 */
public class SourceLine {
    private String label;
    private String instruction;
    private String operand;


    public SourceLine(String label, String instruction, String operand) {
        this.label = label;
        this.instruction = instruction;
        this.operand = operand;
    }

    public SourceLine(String instruction) {
        this.instruction = instruction;
    }

    public SourceLine(String instruction, String operand) {
        this.instruction = instruction;
        this.operand = operand;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getOperand() {
        return operand;
    }

    public void setOperand(String operand) {
        this.operand = operand;
    }

}
