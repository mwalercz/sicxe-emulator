package sicxe.model.simulator.assembler.command;

import org.javatuples.Triplet;
import sicxe.model.simulator.assembler.AddressingEnum.Addressing;
import sicxe.model.simulator.assembler.AddressingEnum.Indexed;
import sicxe.model.simulator.assembler.Parser;
import sicxe.model.simulator.assembler.command.bits.FormatThreeBits;
import sicxe.model.simulator.assembler.exceptions.asm.NoLabelInSymTabException;
import sicxe.model.simulator.assembler.exceptions.asm.TooLargeOperandException;
import sicxe.model.simulator.commons.OpcodeEnum;
import sicxe.model.simulator.commons.SICXE;

/**
 * Created by maciek on 12/01/16.
 */
public class FormatThreeCommand extends Command{


    private Integer programCounter;
    private FormatThreeBits bits = new FormatThreeBits();
    private String operand;
    private OpcodeEnum opcode;

    public FormatThreeCommand(OpcodeEnum opcode, String label, Triplet<Addressing, Indexed, String> operand) {
        super(label);
        this.opcode = opcode;
        this.operand = operand.getValue2();
        if(operand.getValue1().equals(Indexed.YES)) bits.setX();
        if(operand.getValue0().equals(Addressing.IMMEDIATE)) bits.setI();
        else if(operand.getValue0().equals(Addressing.INDIRECT)) bits.setN();
        else if(operand.getValue0().equals(Addressing.SIMPLE)) { bits.setI(); bits.setN(); }
    }

    @Override
    public String translate() throws NoLabelInSymTabException, TooLargeOperandException {
        if(Parser.isNumeric(operand)) return produceCommand(Integer.parseInt(operand));
        Integer intOperand = symTab.get(operand);

        Integer pcRelativeOperand = intOperand - programCounter;
        if(SICXE.fitsSigned12Bits(pcRelativeOperand)){
            bits.setP();
            return produceCommand(pcRelativeOperand);
        } else {
            Integer baseAddress = symTab.get(baseName);
            Integer baseRelativeOperand = intOperand - baseAddress;
            if(SICXE.fitsUnsigned12Bits(baseRelativeOperand)){
                bits.setB();
                return produceCommand(baseRelativeOperand);
            } else throw new TooLargeOperandException();
        }


    }

    @Override
    public Integer assignLocation(Integer currentLocation) {
        programCounter = currentLocation + 3;
        return currentLocation + 3;

    }

    private String produceCommand(Integer actualOperand) throws TooLargeOperandException{
        String firstByte = String.format("%02X", opcode.opcode + bits.getN() + bits.getI());
        String lastWord = String.format("%04X", bits.getX() + bits.getB() + bits.getP() +
                bits.getE() + SICXE.convert12BitsIntoSignedInt(actualOperand));
        return firstByte + lastWord;
    }






}
