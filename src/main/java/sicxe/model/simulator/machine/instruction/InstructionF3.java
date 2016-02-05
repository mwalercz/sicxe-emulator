package sicxe.model.simulator.machine.instruction;

import sicxe.model.simulator.commons.SICXE;

/**
 * Created by maciek on 25/01/16.
 */
public class InstructionF3 extends Instruction{
    private InstructionFlags flags;
    private Integer operand;
    private String prefix = "";
    private String suffix ="";

    public InstructionF3(Integer address, String rawCode, String mnemonic, InstructionFlags flags, Integer operand) {
        super(address, rawCode, mnemonic);
        this.flags = flags;
        this.operand = operand;
        if(flags.isImmediete()) this.prefix ="#";
        if(flags.isIndirect()) this.prefix = "@";

        if(flags.isIndexed()) this.suffix = ",X";
    }

    @Override
    public String getOperand() {
        if(flags.isPcRelative()){
            Integer pcOperand = SICXE.convertToSigned12BitInt(operand);
            String sign = pcOperand >= 0 ? "+" : "";
            return  prefix + "(PC)" + sign + pcOperand + suffix;
        } else if(flags.isBaseRelative()){
            return  prefix + "(BASE)+" + operand + suffix;
        } else {
            return prefix + operand + suffix;
        }



    }

    @Override
    public Integer length() {
        return 3;
    }
}
