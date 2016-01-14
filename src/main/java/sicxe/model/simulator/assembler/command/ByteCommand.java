package sicxe.model.simulator.assembler.command;

import org.apache.commons.codec.binary.Hex;

import java.math.BigInteger;

/**
 * Created by maciek on 13/01/16.
 */
public class ByteCommand implements Command {
    private String hex;

    public ByteCommand(String constantOperand) {
        hex = Hex.encodeHexString(constantOperand.getBytes());
    }


    @Override
    public String translate() {
        return hex;
    }
}
