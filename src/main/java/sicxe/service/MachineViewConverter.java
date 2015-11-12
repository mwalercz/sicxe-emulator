package sicxe.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sicxe.model.commons.SICXE;
import sicxe.model.commons.exceptions.InvalidAddressException;
import sicxe.model.machine.Memory;
import sicxe.model.machine.Registers;
import sicxe.model.machine.register.IntegerRegisterEnum;
import sicxe.model.machine.register.RegisterEnum;
import sicxe.view.ViewMachine;
import sicxe.view.ViewRegisters;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by maciek on 10.11.15.
 */
public class MachineViewConverter {

    private static final Logger LOG = LoggerFactory.getLogger(MachineViewConverter.class);


    public static Map<Integer, Integer> convertMemory(Memory prevMemory, Memory currentMemory) throws InvalidAddressException {
        Map<Integer, Integer> viewMemory = new HashMap<>();
        for (int i = 0; i < SICXE.MAX_MEMORY; i++) {
            int curVal;
            if (!((curVal = currentMemory.getByte(i)) == prevMemory.getByte(i))) {
                viewMemory.put(i, curVal);
            }

        }
        return viewMemory;
    }

    public static ViewRegisters convertRegisters(Registers prevRegs, Registers currRegs) {
        ViewRegisters viewRegs = new ViewRegisters();
        for (IntegerRegisterEnum reg : IntegerRegisterEnum.values()) {
            if ((prevRegs.get(reg.index).getValue().intValue() != currRegs.get(reg.index).getValue().intValue())) {
                viewRegs.getIntRegisters().put(reg.toString(), currRegs.get(reg.index).getValue());
            }
        }
        if (!currRegs.getCC().equals(prevRegs.getCC())) {
            viewRegs.setCC(currRegs.getCC());
        }
        if (!(currRegs.getF().getValue().longValue() == prevRegs.getF().getValue().longValue())) {
            viewRegs.setfRegister(currRegs.getF().getValue());
        }
        return viewRegs;

    }
}
