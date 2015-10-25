package sicxe.model.machine;

import org.apache.log4j.Logger;
import sicxe.model.commons.OpcodeEnum;
import sicxe.model.commons.exceptions.InvalidAddressException;
import sicxe.model.commons.exceptions.NoSuchOpcodeException;
import sicxe.model.commons.exceptions.OutOfRange;
import sicxe.model.machine.instruction.Instruction;
import sicxe.model.machine.instruction.InstructionF2;
import sicxe.model.machine.instruction.InstructionFlags;
import sicxe.model.machine.register.IntegerRegister;

/**
 * Created by maciek on 23.10.15.
 */
public class Machine {

    private static Logger LOG = Logger.getLogger(Machine.class);
    private Registers registers;
    private Memory memory;

    public Registers getRegisters() {
        return registers;
    }

    public Memory getMemory() {
        return memory;
    }

    public Machine() {
        this.registers = new Registers();
        this.memory = new Memory();
    }

    public void resetRegisters() {
        registers.reset();
    }

    public void resetMemory() {
        memory.reset();
    }

    public void resetAll() {
        resetMemory();
        resetRegisters();
    }

    public void process() throws NoSuchOpcodeException, InvalidAddressException, OutOfRange {
        Instruction currentInstruction = new Instruction();
        currentInstruction.setByteOne(getNextByteAndIncrPC());
        if (currentInstruction.isOpcodeValidForFormatOneAndTwo()) {
            switch (currentInstruction.getOpcodeEnum().format) {
                case F1:
                    evaluateFormatOne(currentInstruction.getOpcodeEnum());
                    break;
                case F2:
                    currentInstruction.setByteTwo(getNextByteAndIncrPC());
                    evaluateFormatTwo(new InstructionF2(currentInstruction));
                    break;
                default:
                    break;
            }
        } else if (currentInstruction.isOpcodeValidForFormatThreeAndFour()) {
            currentInstruction.setByteTwo(getNextByteAndIncrPC());
            currentInstruction.setByteThree(getNextByteAndIncrPC());
            if (currentInstruction.isFormatThree()) {
                evaluateFormatThreeFour(currentInstruction.getOperandFormatThree(), new InstructionFlags(currentInstruction));
            } else {
                currentInstruction.setByteFour(getNextByteAndIncrPC());
                evaluateFormatThreeFour(currentInstruction.getOperandFormatFour(), new InstructionFlags(currentInstruction));
            }
        } else throw new NoSuchOpcodeException();
    }


    public void evaluateFormatOne(OpcodeEnum opcode) throws NoSuchOpcodeException, OutOfRange {
        switch (opcode) {
            case FLOAT:
                registers.getF().setValue(registers.getA().getValue().doubleValue());
                break;
            case FIX:
                registers.getA().setValue(registers.getF().getValue().intValue());
                break;
            case NORM:
                // TO DO
                break;
            case SIO:
                // TO DO
                break;
            case HIO:
                // TO DO
                break;
            case TIO:
                // TO DO
                break;
            default:
                throw new NoSuchOpcodeException();
        }
    }

    private void evaluateFormatTwo(InstructionF2 instructionF2) throws NoSuchOpcodeException, OutOfRange {
        IntegerRegister r2 = registers.get(instructionF2.getR2());
        IntegerRegister r1 = registers.get(instructionF2.getR1());
        switch (instructionF2.getOpcodeEnum()) {
            case ADDR:
                r2.setValue(r1.getValue() + r2.getValue());
                break;
            case CLEAR:
                r1.setValue(0);
                break;
            case DIVR:
                r2.setValue(r2.getValue() / r1.getValue());
                break;
            case MULR:
                r2.setValue(r2.getValue() * r1.getValue());
                break;
            case RMO:
                //TO DO
                break;
            case SHIFTL:
                //TO DO
                break;
            case SHIFTR:
                //TO DO
                break;
            case SUBR:
                r2.setValue(r2.getValue() - r1.getValue());
                break;
            case SVC:
                //TO DO
                break;
            case TIXR:
                registers.getX().increment();
                //TO DO
                break;
            default:
                throw new NoSuchOpcodeException();

        }
    }

    private void evaluateFormatThreeFour(Integer operand, InstructionFlags flags) throws InvalidAddressException, OutOfRange {

        switch (flags.getOpcodeEnum()) {
            case LDA:
                registers.getA().setValue(getMemoryAddress(operand, flags));
                break;
            case LDX:
                registers.getX().setValue(getMemoryAddress(operand, flags));
                break;
            case LDL:
                registers.getL().setValue(getMemoryAddress(operand, flags));
                break;
            case STA:
                memory.setWord(getMemoryAddress(registers.getA().getValue(), flags), operand);
                break;
            case STX:
                memory.setWord(getMemoryAddress(registers.getX().getValue(), flags), operand);
                break;
            case STL:
                memory.setWord(getMemoryAddress(registers.getX().getValue(), flags), operand);
                break;
            default:
                throw new InvalidAddressException();


        }

    }


    private int getNextByteAndIncrPC() throws InvalidAddressException, OutOfRange {
        int b = memory.getByte(registers.getPC().getValue());
        registers.incrementPC();
        return b;
    }

    private Integer getMemoryAddress(Integer operand, InstructionFlags flags) throws InvalidAddressException {
        Integer address = getOperandAddress(operand, flags);
        if (flags.isImmediete()) {
            return address;
        } else if (flags.isSimple()) {
            return memory.getWord(address);
        } else if (flags.isIndirect()) {
            return memory.getWord(memory.getWord(address));
        } else return null;

    }

    private Integer getOperandAddress(Integer operand, InstructionFlags flags) {
        Integer address = null;

        if (flags.isDirect()) {
            address = operand;
        } else if (flags.isBaseRelative()) {
            address = operand + registers.getB().getValue();
        } else if (flags.isPcRelative()) {
            address = operand + registers.getPC().getSignedValue();
        }

        if (flags.isIndexed()) {
            address += registers.getX().getValue();
        }
        return address;
    }

    private boolean processAndCheckForException() throws InvalidAddressException, OutOfRange {
        try {
            process();
            return false;
        } catch (NoSuchOpcodeException e) {
            LOG.error("brak opcode", e);
            return true;
        }

    }

    public static void main(String[] args) throws InvalidAddressException, OutOfRange {
        Machine machine = new Machine();

        machine.getMemory().setWord(0, (OpcodeEnum.LDA.opcode << 16 | 0x08));
        machine.getMemory().setWord(0x8, 0x012030);
        machine.processAndCheckForException();
        int val = machine.getRegisters().getA().getValue().intValue();
    }
}
