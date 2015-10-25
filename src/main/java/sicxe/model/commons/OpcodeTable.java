package sicxe.model.commons;

import java.util.HashMap;

/**
 * Created by maciek on 23.10.15.
 */
public class OpcodeTable {
    private static HashMap<String, OpcodeEnum> opcodes = null;
    private static HashMap<Integer, OpcodeEnum> reverseOpcodes = null;
    private static OpcodeTable instance = null;

    private OpcodeTable() {
        opcodes = new HashMap<>();
        reverseOpcodes = new HashMap<>();
        for (OpcodeEnum opcodeEnum : OpcodeEnum.values()) {
            opcodes.put(opcodeEnum.toString(), opcodeEnum);
            reverseOpcodes.put(opcodeEnum.opcode, opcodeEnum);
        }

    }

    public static OpcodeTable getInstance() {
        if (opcodes == null) {
            instance = new OpcodeTable();
        }
        return instance;
    }

    public boolean isValid(String opcode) {
        return opcodes.containsKey(opcode);
    }

    public boolean isValid(Integer opcode) {
        return reverseOpcodes.containsKey(opcode);
    }

    public OpcodeEnum getOpcode(String opcode) {
        return opcodes.get(opcode);
    }

    public OpcodeEnum getOpcode(Integer opcode) {
        return reverseOpcodes.get(opcode);
    }

    public static void main(String[] args) {
        System.out.println(OpcodeTable.getInstance().getOpcode("LDX").opcode);


    }


}
