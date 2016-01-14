package sicxe.model.simulator.assembler.mnemonic;

import org.javatuples.Pair;
import sicxe.model.simulator.assembler.Parser;
import sicxe.model.simulator.commons.AsmEnum;
import sicxe.model.simulator.commons.FormatEnum;
import sicxe.model.simulator.commons.OpcodeEnum;
import sicxe.model.simulator.commons.OpcodeTable;

/**
 * Created by maciek on 12/01/16.
 */
public class Mnemonic {
    private String rawName;
    private OpcodeEnum opcode;
    private AsmEnum asmcode;
    private boolean isExtended;

    public Mnemonic(String instruction) {
        Pair<Boolean, String> pair= Parser.checkForExtended(instruction);
        rawName = pair.getValue1();
        isExtended = pair.getValue0();
        try {
            asmcode = AsmEnum.valueOf(rawName);
        } catch (IllegalArgumentException e) {
            opcode = OpcodeTable.getInstance().getOpcode(rawName);

        }
    }

    public boolean isOpcode(){
        return (opcode != null);
    }

    public OpcodeEnum getOpcode() {
        return opcode;
    }

    public boolean isFormatOne(){
        return opcode.format.equals(FormatEnum.F1);
    }
    public boolean isFormatTwo(){
        return opcode.format.equals(FormatEnum.F2);
    }

    public boolean isFormatThree(){
        return (opcode.format.equals(FormatEnum.F34) && !isExtended);
    }
    public boolean isFormatFour(){
        return (opcode.format.equals(FormatEnum.F34) && isExtended);
    }

    public boolean isWord(){
        return asmcode.equals(AsmEnum.WORD);
    }

    public boolean isByte(){
        return asmcode.equals(AsmEnum.BYTE);
    }

    public boolean isResw(){
        return asmcode.equals(AsmEnum.RESW);
    }

    public boolean isResb(){
        return asmcode.equals(AsmEnum.RESB);
    }

    public boolean isStart(){
        return asmcode.equals(AsmEnum.START);
    }

    public boolean isEnd(){
        return asmcode.equals(AsmEnum.END);
    }




    public AsmEnum getAsmcode() {
        return asmcode;
    }


    public boolean isAsmDirective(){
        return (asmcode != null);
    }
}
