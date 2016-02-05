package sicxe.model.simulator.assembler.command;

import org.apache.commons.codec.binary.Hex;
import sicxe.model.simulator.assembler.Parser;

import java.math.BigInteger;

/**
 * Created by maciek on 13/01/16.
 */
public class ByteCommand extends Command {
    private String constantOperand;
    private String hexConstant;
    public ByteCommand(String label, String constant) {
        super(label);
        this.constantOperand = constant;
    }


    @Override
    public String translate() {
        return hexConstant;
    }

    @Override
    public Integer assignLocation(Integer currentLocation) {
        if(Parser.isNumeric(constantOperand)) hexConstant =  constantOperand;
        else hexConstant =  Hex.encodeHexString(constantOperand.getBytes());
        return currentLocation + (hexConstant.length() / 2);
    }

}
