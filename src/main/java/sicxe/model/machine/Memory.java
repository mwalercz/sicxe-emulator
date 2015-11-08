package sicxe.model.machine;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import sicxe.model.commons.SICXE;
import sicxe.model.commons.exceptions.InvalidAddressException;

/**
 * Created by maciek on 24.10.15.
 */
@Service
@Scope("prototype")
public class Memory {
    private byte[] memory = new byte[SICXE.MAX_MEMORY];

    public int getByte(int address) throws InvalidAddressException {
        if (valid(address)) {
            return Byte.toUnsignedInt(this.memory[address]);
        } else throw new InvalidAddressException();
    }

    public int getWord(int address) throws InvalidAddressException {
        if (valid(address) && valid(address + 1) && valid(address + 2)) {
            byte[] list = new byte[3];
            list[0] = this.memory[address];
            list[1] = this.memory[address + 1];
            list[2] = this.memory[address + 2];
            return SICXE.convert24BitsIntoInt(list);
        } else throw new InvalidAddressException();
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
            byte b = (byte) (value & 0xff);
            this.memory[address] = b;
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
            this.memory[i] = 0x00;
        }
    }


    private boolean valid(int address) throws InvalidAddressException {
        if (address >= SICXE.MIN_ADDRESS && address <= SICXE.MAX_ADDRESS) {
            return true;
        } else throw new InvalidAddressException();
    }


}
