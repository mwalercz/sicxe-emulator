package sicxe.model.simulator.loader;

import org.springframework.stereotype.Service;
import sicxe.model.simulator.assembler.objectprogram.ObjectProgram;
import sicxe.model.simulator.assembler.objectprogram.TextRecord;
import sicxe.model.simulator.commons.exceptions.InvalidAddressException;
import sicxe.model.simulator.machine.Memory;

/**
 * Created by maciek on 08.11.15.
 */
public class Loader {



    public static void load(ObjectProgram objectProgram, Memory memory) throws InvalidAddressException {
        for (TextRecord textRecord : objectProgram.getTextRecords()) {
            Integer i = 0;
            while(i < textRecord.getObjectCode().length() - 1){
                String byteCode = textRecord.getObjectCode().substring(i,i+2);
                memory.setByte(textRecord.getStartingAddress() + i/2, Integer.parseInt(byteCode,16));
                i += 2;
            }
        }
    }

}
