package sicxe.model.simulator.disassembler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import sicxe.model.simulator.commons.exceptions.InvalidAddressException;
import sicxe.model.simulator.commons.exceptions.NoSuchOpcodeException;
import sicxe.model.simulator.machine.Machine;
import sicxe.model.simulator.machine.instruction.*;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by maciek on 25/01/16.
 */
@Service
@Scope("session")
public class Disassembler {

    private Machine machine;
    private Integer nextInstructionAddress;

    @Autowired
    public Disassembler(Machine machine) {
        this.machine = machine;
    }

    private Instruction disassemble(Integer address) throws InvalidAddressException, NoSuchOpcodeException {
        RawInstruction raw = new RawInstruction();
        raw.setByteOne(getByte(address));
        if(raw.isOpcodeValidForFormatOneAndTwo()){
            if(raw.isFormatOne()) {
                nextInstructionAddress = address + 1;
                return new InstructionF1(address, raw.getRaw(),
                        raw.getOpcodeEnum().name());
            }
            else {
                nextInstructionAddress = address + 2;
                raw.setByteTwo(getByte(address+1));
                return new InstructionF2(address, raw.getOpcodeEnum(),
                        raw.getRaw(), raw );
            }
        } else if(raw.isOpcodeValidForFormatThreeAndFour()){
            raw.setByteTwo(getByte(address+1));
            raw.setByteThree(getByte(address+2));
            InstructionFlags flags = new InstructionFlags(raw);
            if(raw.isFormatThree()){
                nextInstructionAddress = address + 3;
                return new InstructionF3(address, raw.getRaw(),
                        raw.getOpcodeEnum().name(), flags,
                        raw.getOperandFormatThree());
            }
            else{
                nextInstructionAddress = address + 4;
                raw.setByteFour(getByte(address+3));
                return new InstructionF4(address, raw.getRaw(),
                        raw.getOpcodeEnum().name(), flags,
                        raw.getOperandFormatFour());
            }

        } else throw new NoSuchOpcodeException();

    }

    public List<Instruction> safeDisassemble(Integer instructionNumber){
        LinkedList<Instruction> instructions = new LinkedList<>();
        nextInstructionAddress = machine.getRegisters().getPC().getValue();
        for (int i = 0; i < instructionNumber ; i++) {
            try{
                instructions.add(disassemble(nextInstructionAddress));
            } catch (InvalidAddressException e) {
                e.printStackTrace();
            } catch (NoSuchOpcodeException e) {
                instructions.add(new InstructionDirective(nextInstructionAddress));
                nextInstructionAddress++;
            }
        }
        return instructions;
    }



    private Integer getByte(Integer address) throws InvalidAddressException {
        return machine.getMemory().getByte(address);
    }



}
