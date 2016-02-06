package sicxe.service.mappers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sicxe.model.simulator.commons.SICXE;
import sicxe.model.simulator.machine.Memory;
import sicxe.model.simulator.machine.Registers;
import sicxe.model.simulator.machine.instruction.RawInstruction;
import sicxe.model.simulator.machine.register.IntegerRegisterEnum;
import sicxe.view.simulator.RegistersDto;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by maciek on 10.11.15.
 */
public class MachineViewConverter {

    private static final Logger LOG = LoggerFactory.getLogger(MachineViewConverter.class);


    //    public static MachineDto convertMachine()
    public static Object convertMemory(Memory prevMemory, Memory currentMemory) {
        Map<Object, Object> viewMemory = new HashMap<>();
        currentMemory.getMap().forEach((key, val) -> {
                    Object prevVal = prevMemory.getMap().get(key);
                    if (prevVal == null) viewMemory.put(key, SICXE.toHex(2, (Integer) val));
                    else if (!prevMemory.getMap().get(key).equals(val))
                        viewMemory.put(key, SICXE.toHex(2, (Integer) val));
                }
        );
        return viewMemory.entrySet().toArray();
    }

    public static RegistersDto convertRegisters(Registers prevRegs, Registers currRegs) {
        RegistersDto viewRegs = new RegistersDto();
        HashMap<String, String> regs = new HashMap<>();
        for (IntegerRegisterEnum reg : IntegerRegisterEnum.values()) {
            if (!(prevRegs.get(reg.index).getValue().intValue() == currRegs.get(reg.index).getValue().intValue())) {
                regs.put(reg.toString(), SICXE.toHex(6, currRegs.get(reg.index).getValue()));
            }
        }
        viewRegs.setIntegers(regs.entrySet().toArray());
        if (!currRegs.getCC().equals(prevRegs.getCC())) {
            viewRegs.setCC(currRegs.getCC());
        }
        if (!(currRegs.getF().getValue().longValue() == prevRegs.getF().getValue().longValue())) {
            viewRegs.setF(SICXE.toHex(12, currRegs.getF().getValue().longValue()));
        }
        return viewRegs;

    }

    public static String convertNixbpe(RawInstruction instruction) {
        if(instruction.isFormatOne()){
            return convertNi(instruction.getByteOne()) + "---";
        } else {
            return convertNi(instruction.getByteOne()) +
                    convertXbpe(instruction.getByteTwo());
        }
    }

    private static String convert(int b){
        if((b & 0b1) == 0b1) return "+";
        else return "-";
    }

    private static String convertNi(int b){
        return convert(b) + convert(b >> 1);
    }

    private static String convertXbpe(int b){
        b = b >> 4;
        return convert(b) + convert(b >> 1) + convert(b >> 2) + convert(b >> 3);
    }

}
