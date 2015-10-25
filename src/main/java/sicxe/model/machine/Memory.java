package sicxe.model.machine;

import sicxe.model.commons.SICXE;
import sicxe.model.commons.exceptions.InvalidAddressException;

/**
 * Created by maciek on 24.10.15.
 */
public class Memory {
    private byte[] memory = new byte[SICXE.MAX_MEMORY];

    public int getByte(int address) throws InvalidAddressException {
        if (valid(address)) {
            return Byte.toUnsignedInt(this.memory[address]);
        } else throw new InvalidAddressException();
    }

    public int getWord(int address) throws InvalidAddressException {
        if (valid(address)) {
            byte[] list = new byte[3];
            list[0] = this.memory[address];
            list[1] = this.memory[address + 1];
            list[2] = this.memory[address + 2];
            return SICXE.convert24BitsIntoInt(list);
        } else throw new InvalidAddressException();
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

    public void reset() {
        for (int i = 0; i < SICXE.MAX_MEMORY; i++) {
            this.memory[i] = 0x00;
        }
    }

    public static void main(String[] args) throws InvalidAddressException {
        Memory mem = new Memory();
        mem.setByte(0, 254);
        int byt = mem.getByte(0);
        mem.setWord(1, 0x00112233);
        int word = mem.getWord(0);


    }

    private boolean valid(int address) throws InvalidAddressException {
        if (address >= SICXE.MIN_ADDRESS && address <= SICXE.MAX_ADDRESS) {
            return true;
        } else throw new InvalidAddressException();
    }


}
