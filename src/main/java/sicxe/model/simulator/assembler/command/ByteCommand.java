package sicxe.model.simulator.assembler.command;

import org.apache.commons.codec.binary.Hex;
import sicxe.model.simulator.assembler.Parser;

import java.math.BigInteger;

/**
 * Created by maciek on 13/01/16.
 */
public class ByteCommand extends Command {
    private String constantOperand;

    public ByteCommand(String label, String constant) {
        super(label);
        this.constantOperand = constant;
    }


    @Override
    public String translate() {
        if(Parser.isNumeric(constantOperand))
            return constantOperand;
        return Hex.encodeHexString(constantOperand.getBytes());
    }

    @Override
    public Integer assignLocation(Integer currentLocation) {
        return currentLocation + constantOperand.length();
    }

}
