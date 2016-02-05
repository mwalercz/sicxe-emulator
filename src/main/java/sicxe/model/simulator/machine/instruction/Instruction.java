package sicxe.model.simulator.machine.instruction;

import sicxe.model.simulator.commons.SICXE;

/**
 * Created by maciek on 25/01/16.
 */
public abstract class Instruction {
    private String address;
    private String rawCode;
    private String mnemonic;

    public Instruction() {
    }

    public Instruction(Integer address, String rawCode, String mnemonic) {
        this.address = SICXE.toHex(5, address);
        this.rawCode = rawCode;
        this.mnemonic = mnemonic;
    }

    public abstract String getOperand();
    public abstract Integer length();

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRawCode() {
        return rawCode;
    }

    public void setRawCode(String rawCode) {
        this.rawCode = rawCode;
    }

    public String getMnemonic() {
        if (length() == 4) return "+" + mnemonic;
        else return mnemonic;
    }

    public void setMnemonic(String mnemonic) {
        this.mnemonic = mnemonic;
    }
}
