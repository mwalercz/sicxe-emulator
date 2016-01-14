package sicxe.model.simulator.machine;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import sicxe.model.simulator.commons.SICXE;
import sicxe.model.simulator.commons.exceptions.InvalidAddressException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by maciek on 24.10.15.
 */
@Service
@Scope("prototype")
public class Memory {
    private Map<Integer,Integer> memory = new HashMap<>();
    private final Integer def = new Integer(0);
    public Memory(){
    }
    public void setMemory(Map<Integer, Integer> memory) {
        this.memory = memory;
    }

    public Map<Integer,Integer> getMemory(){
        return memory;
    }
    public Memory(Memory memory){
        for(Map.Entry<Integer, Integer> entry : memory.getMemory().entrySet()) {
            this.memory.put(entry.getKey(),entry.getValue());
        }
    }

    public int getByte(int address) throws InvalidAddressException {
        if (valid(address)) {
            Integer cell = memory.get(address);
            if(cell != null) return cell;
            else return def;
        } else throw new InvalidAddressException();
    }

    public int getWord(int address) throws InvalidAddressException {
        int value = 0;
        for (int i = 0; i < 3; i++) {
            value = value | (getByte(address + i) << ((2 - i) * 8));
        }
        return value;
    }

    public long getDoubleWord(int address) throws InvalidAddressException {
        long value = 0;
        for (int i = 0; i < 6; i++) {
            value = value | (getByte(address + i) << ((5 - i) * 8));
        }
        return value;

    }

    public void setByte(int address, int value) throws InvalidAddressException {
        if (valid(address)) {
            int b = (value & 0xff);
            memory.put(address,b);
        }
    }

    public void setWord(int address, int value) throws InvalidAddressException {
        setByte(address, value >>> 16);
        setByte(address + 1, value >>> 8);
        setByte(address + 2, value);
    }

    public void setDoubleWord(int address, long value) throws InvalidAddressException {
        setByte(address, (int) (value >>> 40));
        setByte(address + 1, (int) (value >>> 32));
        setByte(address + 2, (int) (value >>> 24));
        setByte(address + 3, (int) (value >>> 16));
        setByte(address + 4, (int) (value >>> 8));
        setByte(address + 5, (int) value);
    }

    public void set32Bit(int address, int value) throws InvalidAddressException {
        setByte(address, value >>> 24);
        setByte(address + 1, value >>> 16);
        setByte(address + 2, value >>> 8);
        setByte(address + 3, value);
    }

    public void reset() {
        for (int i = 0; i < SICXE.MAX_MEMORY; i++) {
            memory.put(i,0);
        }
    }


    private boolean valid(int address) throws InvalidAddressException {
        if (address >= SICXE.MIN_ADDRESS && address <= SICXE.MAX_ADDRESS) {
            return true;
        } else throw new InvalidAddressException();
    }


}
