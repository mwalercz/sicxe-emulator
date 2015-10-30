package sixce.model.machine;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import sicxe.model.commons.exceptions.MachineException;
import sicxe.model.machine.Machine;

import static org.junit.Assert.assertEquals;

/**
 * Created by maciek on 30.10.15.
 */

public class TestIntegerRegister {
    private Machine machine;
    private static Logger LOG = Logger.getLogger(TestIntegerRegister.class);

    @Before
    public void init() {
        machine = new Machine();
    }

    @Test
    public void testSigned() throws MachineException {
        int val = (1 << 19) + 2;
        machine.getRegisters().getPC().setValue(val);
        int pcVal = machine.getRegisters().getPC().getSignedValue();
        LOG.debug("pcVal= " + pcVal);
        assertEquals("nie dziala testSigned", pcVal, -val);


    }
}
