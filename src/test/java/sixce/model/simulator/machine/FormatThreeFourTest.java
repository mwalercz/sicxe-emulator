package sixce.model.simulator.machine;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import sicxe.Application;
import sicxe.model.simulator.commons.OpcodeEnum;
import sicxe.model.simulator.commons.exceptions.InvalidAddressException;
import sicxe.model.simulator.commons.exceptions.InvalidFlagsException;
import sicxe.model.simulator.commons.exceptions.NoSuchOpcodeException;
import sicxe.model.simulator.commons.exceptions.OutOfRangeException;
import sicxe.model.simulator.machine.Machine;
import sicxe.model.simulator.machine.instruction.InstructionFlags;

import static org.junit.Assert.assertEquals;

/**
 * Created by maciek on 04.11.15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class FormatThreeFourTest {

    private Machine machine;
    private static org.slf4j.Logger LOG = LoggerFactory.getLogger(ProcessTest.class);


    @Before
    public void init(){
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

//   @Test
    public void testSTA() throws InvalidAddressException, OutOfRangeException {
        int operand = 0x08;
        machine.getRegisters().setA(operand);
        InstructionFlags flags = new InstructionFlags();
        flags.setIndirect(true);
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
        int formatFour = boolToInt(flags.isFormatFour());
        int intFlag = (indirect << 5) | (immediete << 4) | (indexed << 3) | (baseRelative << 2)
                | (pcRelative << 1) | (formatFour);
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
            LOG.info("Wrong address");
            return true;
        } catch (InvalidFlagsException e) {
            LOG.info("Wrong flags of instruction");
            return true;
        } catch (OutOfRangeException e) {
            LOG.info("Number in registers is out of range");
            return true;
        } catch (NoSuchOpcodeException e) {
            LOG.info("No such opcode in SIC/XE");
            return true;
        }

    }
}
