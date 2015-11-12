package sixce.model.machine;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import sicxe.Application;
import sicxe.model.commons.exceptions.MachineException;
import sicxe.model.machine.Machine;

import static org.junit.Assert.assertEquals;

/**
 * Created by maciek on 30.10.15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)

public class IntegerRegisterTest {
    @Autowired
    private Machine machine;
    private static org.slf4j.Logger LOG = LoggerFactory.getLogger(ProcessTest.class);


    @Test
    public void testSigned() throws MachineException {
        int val = (1 << 19) + 2;
        machine.getRegisters().getPC().setValue(val);
        int pcVal = machine.getRegisters().getPC().getSignedValue();
        LOG.debug("pcVal= " + pcVal);
        assertEquals("nie dziala testSigned", pcVal, -val);


    }
}
