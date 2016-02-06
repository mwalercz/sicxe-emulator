package sicxe.model.simulator.machine.instruction;

/**
 * Created by maciek on 25/01/16.
 */
public class InstructionDirective extends Instruction{

    public InstructionDirective(Integer address) {
        super(address, "XX", "BYTE");
    }

    @Override
    public String getOperand() {
        return "";
    }

    @Override
    public Integer length() {
        return 4;
    }
}
