package sicxe.model.simulator.machine.instruction;

/**
 * Created by maciek on 25/01/16.
 */
public class InstructionF1 extends Instruction{

    public InstructionF1(Integer address, String rawCode, String mnemonic) {
        super(address, rawCode, mnemonic);
    }

    @Override
    public String getOperand() {
        return "";
    }

    @Override
    public Integer length() {
        return 1;
    }
}
