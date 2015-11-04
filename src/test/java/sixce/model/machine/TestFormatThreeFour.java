package sixce.model.machine;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import sicxe.model.commons.OpcodeEnum;
import sicxe.model.commons.exceptions.InvalidAddressException;
import sicxe.model.commons.exceptions.InvalidFlagsException;
import sicxe.model.commons.exceptions.NoSuchOpcodeException;
import sicxe.model.commons.exceptions.OutOfRangeException;
import sicxe.model.machine.Machine;
import sicxe.model.machine.instruction.InstructionFlags;

import static org.junit.Assert.assertEquals;

/**
 * Created by maciek on 04.11.15.
 */
public class TestFormatThreeFour {

    Machine machine;
    private static Logger LOG = Logger.getLogger(TestFormatThreeFour.class);

    @Before
    public void init() {
        machine = new Machine();
    }

    @Test
    public void testLDA() throws InvalidAddressException {
        InstructionFlags flags = new InstructionFlags();
        flags.setSimple(true);
        flags.setFormatFour(true);
        flags.setOpcodeEnum(OpcodeEnum.LDA);
        Integer operand = 0x20;
        Integer operandValue = 0x010203;
        machine.getMemory().setWord(operand, operandValue);
        machine.getMemory().set32Bit(0x0, generateInstruction32bit(flags, operand));
        processAndCheckForException();
        assertEquals(operandValue, machine.getRegisters().getA().getValue());
    }

    @Test
    public void testSTA() throws InvalidAddressException, OutOfRangeException {
        int operand = 0x08;
        machine.getRegisters().setA(operand);
        InstructionFlags flags = new InstructionFlags();
        flags.setImmediete(true);
        flags.setFormatFour(true);
        flags.setOpcodeEnum(OpcodeEnum.STA);
        machine.getMemory().set32Bit(0x0, generateInstruction32bit(flags, operand));
        processAndCheckForException();

        assertEquals(operand, machine.getMemory().getWord(operand));

    }

    private int generateInstruction32bit(InstructionFlags flags, Integer operand) {
        int indirect = boolToInt(flags.isIndirect());
        int immediete = boolToInt(flags.isImmediete());
        int indexed = boolToInt(flags.isIndexed());
        int baseRelative = boolToInt(flags.isBaseRelative());
        int pcRelative = boolToInt(flags.isPcRelative());
        int formatThree = boolToInt(flags.isFormatFour());
        int intFlag = (indirect << 5) | (immediete << 4) | (indexed << 3) | (baseRelative << 2)
                | (pcRelative << 1) | (formatThree);
        int instr = (flags.getOpcodeEnum().opcode << 26) | (intFlag << 20) | operand;
        return instr;
    }

    private int boolToInt(boolean bool) {
        if (bool) return 1;
        return 0;
    }

    private boolean processAndCheckForException() {
        try {
            machine.process();
            return false;
        } catch (InvalidAddressException e) {
            LOG.error("Wrong address", e);
            return true;
        } catch (InvalidFlagsException e) {
            LOG.error("Wrong flags of instruction", e);
            return true;
        } catch (OutOfRangeException e) {
            LOG.error("Number in registers is out of range", e);
            return true;
        } catch (NoSuchOpcodeException e) {
            LOG.error("No such opcode in SIC/XE", e);
            return true;
        }

    }
}
