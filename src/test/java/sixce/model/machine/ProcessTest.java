package sixce.model.machine;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import sicxe.Application;
import sicxe.model.simulator.commons.OpcodeEnum;
import sicxe.model.simulator.commons.exceptions.*;
import sicxe.model.simulator.machine.Machine;

import static org.junit.Assert.assertEquals;

/**
 * Created by maciek on 25.10.15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)

public class ProcessTest {

    @Autowired
    private Machine machine;
    private static Logger LOG = LoggerFactory.getLogger(ProcessTest.class);


    @Test
    public void formatOneFloat() throws InvalidAddressException {

        machine.getMemory().setByte(0, OpcodeEnum.FIX.opcode);
        machine.getRegisters().getF().setValue(10.0);
        processAndCheckForException();
        Integer result = machine.getRegisters().getA().getValue();
        assertEquals("wynik: " + result.toString(), 10, result.intValue());
    }

    @Test
    public void formatOneFloat2() throws InvalidAddressException {
        machine.getMemory().setByte(0, OpcodeEnum.FIX.opcode);
        machine.getRegisters().getF().setValue(1.2);
        processAndCheckForException();
        Integer result = machine.getRegisters().getA().getValue();
        assertEquals("wynik: " + result.toString(), 1, result.intValue());
    }

    @Test
    public void testAddr() throws MachineException {
        machine.getMemory().setWord(0, (OpcodeEnum.ADDR.opcode << 16) | (0x10 << 8));
        machine.getRegisters().getX().increment();
        machine.getRegisters().getA().increment();
        machine.getRegisters().getX().increment();
        processAndCheckForException();
        int val = machine.getRegisters().getA().getValue().intValue();
        assertEquals("rej a" + val, 3, val);
    }

    @Test
    public void testForException() throws MachineException {
        machine.getMemory().setWord(0, ((127) << 16 | (0x10 << 8)));
        machine.getRegisters().getX().increment();
        machine.getRegisters().getA().increment();
        machine.getRegisters().getX().increment();
        assertEquals(true, processAndCheckForException());
    }

    @Test
    public void fixTest() throws InvalidAddressException {
        machine.getMemory().setByte(0, OpcodeEnum.FIX.opcode);
        machine.getRegisters().getF().setValue(10.2);
        processAndCheckForException();
        Integer result = machine.getRegisters().getA().getValue();
        assertEquals("wynik: " + result.toString(), 10, result.intValue());
    }

    @Test
    public void floatTest2() throws InvalidAddressException {
        machine.getMemory().setByte(0, OpcodeEnum.FIX.opcode);
        machine.getRegisters().getF().setValue(1.2);
        processAndCheckForException();
        Integer result = machine.getRegisters().getA().getValue();
        assertEquals("wynik: " + result.toString(), 1, result.intValue());
    }

    @Test
    public void testLDA() throws InvalidAddressException {
        machine.getMemory().setWord(0, ((OpcodeEnum.LDA.opcode) << 16 | 0x08));
        machine.getMemory().setWord(0x8, 0x012030);
        processAndCheckForException();
        int val = machine.getRegisters().getA().getValue().intValue();
        assertEquals(0x012030, val);
    }

    @Test
    public void testForOutOfRangeException() throws InvalidAddressException {
        machine.getMemory().setWord(0, ((OpcodeEnum.LDA.opcode) << 16 | 0x08));
        machine.getMemory().setWord(0x8, 0x102030);
        assertEquals(processAndCheckForException(), true);

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
