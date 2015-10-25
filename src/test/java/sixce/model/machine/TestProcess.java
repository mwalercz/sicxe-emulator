package sixce.model.machine;

import com.jcabi.aspects.Loggable;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import sicxe.model.commons.OpcodeEnum;
import sicxe.model.commons.exceptions.InvalidAddressException;
import sicxe.model.commons.exceptions.NoSuchOpcodeException;
import sicxe.model.commons.exceptions.OutOfRange;
import sicxe.model.machine.Machine;

import static org.junit.Assert.assertEquals;

/**
 * Created by maciek on 25.10.15.
 */
@Loggable(Loggable.DEBUG)
public class TestProcess {
    Machine machine;
    private static Logger LOG = Logger.getLogger(TestProcess.class);

    @Before
    public void init() {
        machine = new Machine();
    }

    @After
    public void after() {
        machine = null;
    }

    @Loggable(Loggable.DEBUG)
    @Test
    public void formatOneFloat() throws InvalidAddressException {

        machine.getMemory().setByte(0, OpcodeEnum.FIX.opcode);
        machine.getRegisters().getF().setValue(10.0);
        processAndCheckForException();
        Integer result = machine.getRegisters().getA().getValue();
        assertEquals("wynik: " + result.toString(), 10, result.intValue());
    }

    @Test
    @Loggable
    public void formatOneFloat2() throws InvalidAddressException {
        machine.getMemory().setByte(0, OpcodeEnum.FIX.opcode);
        machine.getRegisters().getF().setValue(1.2);
        processAndCheckForException();
        Integer result = machine.getRegisters().getA().getValue();
        assertEquals("wynik: " + result.toString(), 1, result.intValue());
    }

    @Test
    @Loggable
    public void testAddr() throws InvalidAddressException {
        machine.getMemory().setWord(0, (OpcodeEnum.ADDR.opcode << 16) | (0x10 << 8));
        machine.getRegisters().getX().increment();
        machine.getRegisters().getA().increment();
        machine.getRegisters().getX().increment();
        processAndCheckForException();
        int val = machine.getRegisters().getA().getValue().intValue();
        assertEquals("rej a" + val, 3, val);
    }

    @Test
    public void testForException() throws InvalidAddressException {
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
        } catch (NoSuchOpcodeException e) {
            LOG.error("brak opcode", e);
            return true;
        } catch (OutOfRange e) {
            LOG.error("numer spoza zakresu", e);
            return true;
        } catch (InvalidAddressException e) {
            LOG.error("zly addres", e);
            return true;
        }

    }
}
