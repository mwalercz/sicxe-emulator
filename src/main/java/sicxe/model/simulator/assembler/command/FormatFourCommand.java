package sicxe.model.simulator.assembler.command;

import org.javatuples.Triplet;
import sicxe.model.simulator.assembler.AddressingEnum.Addressing;
import sicxe.model.simulator.assembler.AddressingEnum.Indexed;
import sicxe.model.simulator.assembler.Parser;
import sicxe.model.simulator.assembler.command.bits.FormatFourBits;
import sicxe.model.simulator.assembler.exceptions.asm.NoLabelInSymTabException;
import sicxe.model.simulator.commons.OpcodeEnum;

/**
 * Created by maciek on 13/01/16.
 */
public class FormatFourCommand extends Command {
    private FormatFourBits bits = new FormatFourBits();
    private OpcodeEnum opcode;
    private String operand;

    public FormatFourCommand(OpcodeEnum opcode, String label, Triplet<Addressing, Indexed, String> operand) {
        super(label);
        this.opcode = opcode;
        this.operand = operand.getValue2();
        if(operand.getValue1().equals(Indexed.YES)) bits.setX();
        if(operand.getValue0().equals(Addressing.IMMEDIATE)) bits.setI();
        else if(operand.getValue0().equals(Addressing.INDIRECT)) bits.setN();

    }


    @Override
    public String translate() throws NoLabelInSymTabException {
        Integer intOperand;
        if(Parser.isNumeric(operand)) intOperand = Integer.parseInt(operand);
        else intOperand = symTab.get(operand);
        String firstByte = String.format("%02X", opcode.opcode + bits.getN() + bits.getI());
        String lastWord = String.format("%06X", bits.getX() + bits.getB() + bits.getP() + bits.getE() + intOperand);
        return firstByte + lastWord;
    }

    @Override
    public Integer assignLocation(Integer currentLocation) {
        return currentLocation + 4;
    }
}
