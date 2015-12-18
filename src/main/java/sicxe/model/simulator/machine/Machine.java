package sicxe.model.simulator.machine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import sicxe.model.simulator.commons.OpcodeEnum;
import sicxe.model.simulator.commons.SICXE;
import sicxe.model.simulator.commons.exceptions.InvalidAddressException;
import sicxe.model.simulator.commons.exceptions.InvalidFlagsException;
import sicxe.model.simulator.commons.exceptions.NoSuchOpcodeException;
import sicxe.model.simulator.commons.exceptions.OutOfRangeException;
import sicxe.model.simulator.machine.instruction.Instruction;
import sicxe.model.simulator.machine.instruction.InstructionFlags;
import sicxe.model.simulator.machine.instruction.InstructionFormatTwo;
import sicxe.model.simulator.machine.register.IntegerRegister;

/**
 * Created by maciek on 23.10.15.
 */
@Service
@Scope("prototype")
public class Machine {

    private static final Logger LOG = LoggerFactory.getLogger(Machine.class);
    @Autowired
    private Registers registers;
    @Autowired
    private Memory memory;

    public Registers getRegisters() {
        return registers;
    }

    public Memory getMemory() {
        return memory;
    }

    public Machine(){}
    public Machine(Machine m){
        registers = new Registers(m.getRegisters());
        memory = new Memory(m.getMemory());
    }

    public void setRegisters(Registers registers) {
        this.registers = registers;
    }

    public void setMemory(Memory memory) {
        this.memory = memory;
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

    public void process() throws NoSuchOpcodeException, InvalidAddressException, OutOfRangeException, InvalidFlagsException {
        Instruction currentInstruction = new Instruction();
        currentInstruction.setByteOne(getNextByteAndIncrPC());
        if (currentInstruction.isOpcodeValidForFormatOneAndTwo()) {
            switch (currentInstruction.getOpcodeEnum().format) {
                case F1:
                    evaluateFormatOne(currentInstruction.getOpcodeEnum());
                    break;
                case F2:
                    currentInstruction.setByteTwo(getNextByteAndIncrPC());
                    evaluateFormatTwo(new InstructionFormatTwo(currentInstruction));
                    break;
                default:
                    throw new NoSuchOpcodeException();
            }
        } else if (currentInstruction.isOpcodeValidForFormatThreeAndFour()) {
            currentInstruction.setByteTwo(getNextByteAndIncrPC());
            currentInstruction.setByteThree(getNextByteAndIncrPC());
            if (!currentInstruction.isFormatFour()) {
                evaluateFormatThreeFour(currentInstruction.getOperandFormatThree(), new InstructionFlags(currentInstruction));
            } else {
                currentInstruction.setByteFour(getNextByteAndIncrPC());
                evaluateFormatThreeFour(currentInstruction.getOperandFormatFour(), new InstructionFlags(currentInstruction));
            }
        } else throw new NoSuchOpcodeException();
    }


    public void evaluateFormatOne(OpcodeEnum opcode) throws NoSuchOpcodeException, OutOfRangeException {
        switch (opcode) {
            case FLOAT:
                registers.setF(registers.getA().getValue().doubleValue());
                break;
            case FIX:
                registers.setA(registers.getF().getValue().intValue());
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

    private void evaluateFormatTwo(InstructionFormatTwo instructionFormatTwo) throws NoSuchOpcodeException, OutOfRangeException {
        IntegerRegister r2 = registers.get(instructionFormatTwo.getR2());
        IntegerRegister r1 = registers.get(instructionFormatTwo.getR1());
        switch (instructionFormatTwo.getOpcodeEnum()) {
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
                /**
                 * @TODO
                 */
                break;
            case SHIFTL:
                /**
                 * @TODO
                 */
                break;
            case SHIFTR:
                /**
                 * @TODO
                 */
                break;
            case SUBR:
                r2.setValue(r2.getValue() - r1.getValue());
                break;
            case SVC:
                /**
                 * @TODO
                 */
                break;
            case TIXR:
                registers.getX().increment();
                registers.setSW(registers.getX().getSignedValue() - r1.getSignedValue());
                break;
            default:
                throw new NoSuchOpcodeException();

        }
    }

    private void evaluateFormatThreeFour(Integer operand, InstructionFlags flags) throws InvalidAddressException, OutOfRangeException, InvalidFlagsException {

        switch (flags.getOpcodeEnum()) {
            case LDA:
                registers.setA(getWordFromMemory(operand, flags));
                break;
            case LDX:
                registers.setX(getWordFromMemory(operand, flags));
                break;
            case LDL:
                registers.setL(getWordFromMemory(operand, flags));
                break;
            case STA:
                memory.setWord(getWordFromMemory(operand, flags), registers.getA().getValue());
                break;
            case STX:
                memory.setWord(getWordFromMemory(operand, flags), registers.getX().getValue());
                break;
            case STL:
                memory.setWord(getWordFromMemory(operand, flags), registers.getL().getValue());
                break;
            case ADD:
                registers.setA(registers.getA().getValue() + getWordFromMemory(operand, flags));
                break;
            case SUB:
                registers.setA(registers.getA().getValue() - getWordFromMemory(operand, flags));
                break;
            case MUL:
                registers.setA(registers.getA().getValue() * getWordFromMemory(operand, flags));
                break;
            case DIV:
                registers.setA(registers.getA().getValue() / getWordFromMemory(operand, flags));
                break;
            case COMP:
                registers.setSW(getWordFromMemory(operand, flags) - registers.getA().getValue());
                break;
            case TIX:
                registers.getX().increment();
                registers.setSW(registers.getX().getSignedValue() - SICXE.convertWordToSignedInt(getWordFromMemory(operand, flags)));
                break;
            case JEQ:
                if (registers.getCC().equals("=")) {
                    registers.setPC(getWordFromMemory(operand, flags));
                }
                break;
            case JGT:
                if (registers.getCC().equals(">")) {
                    registers.setPC(getWordFromMemory(operand, flags));
                }
                break;
            case JLT:
                if (registers.getCC().equals("<")) {
                    registers.setPC(getWordFromMemory(operand, flags));
                }
                break;
            case J:
                registers.setPC(getWordFromMemory(operand, flags));
                break;
            case AND:
                registers.setA(registers.getA().getValue() & getWordFromMemory(operand, flags));
                break;
            case OR:
                registers.setA(registers.getA().getValue() | getWordFromMemory(operand, flags));
                break;
            case JSUB:
                registers.setL(registers.getPC().getValue());
                registers.setPC(getWordFromMemory(operand, flags));
                break;
            case RSUB:
                registers.setPC(registers.getL().getValue());
                break;
            case LDCH:
                registers.setA(((registers.getA().getValue() >>> 8) << 8) | getByteFromMemory(operand, flags));
                break;
            case STCH:
                memory.setByte(getWordFromMemory(operand, flags), registers.getA().getValue());
                break;
            case ADDF:
                registers.setF(registers.getF().getValue() + SICXE.convertLongToFloat(getDoubleWordFromMemory(operand, flags)));
                break;
            case SUBF:
                registers.setF(registers.getF().getValue() - SICXE.convertLongToFloat(getDoubleWordFromMemory(operand, flags)));
                break;
            case MULF:
                registers.setF(registers.getF().getValue() * SICXE.convertLongToFloat(getDoubleWordFromMemory(operand, flags)));
                break;
            case DIVF:
                registers.setF(registers.getF().getValue() / SICXE.convertLongToFloat(getDoubleWordFromMemory(operand, flags)));
                break;
            case LDB:
                registers.setB(getWordFromMemory(operand, flags));
                break;
            case LDS:
                registers.setS(getWordFromMemory(operand, flags));
                break;
            case LDF:
                registers.setF(getDoubleWordFromMemory(operand, flags));
                break;
            case LDT:
                registers.setT(getWordFromMemory(operand, flags));
                break;
            case STB:
                memory.setWord(getWordFromMemory(operand, flags), registers.getB().getValue());
                break;
            case STS:
                memory.setWord(getWordFromMemory(operand, flags), registers.getS().getValue());
                break;
            case STF:
                memory.setDoubleWord(getWordFromMemory(operand, flags), SICXE.convertFloatToLong(registers.getF().getValue()));
                break;
            case STT:
                memory.setWord(getWordFromMemory(registers.getT().getValue(), flags), operand);
                break;


            default:
                throw new InvalidAddressException();


        }

    }


    private int getNextByteAndIncrPC() throws InvalidAddressException, OutOfRangeException {
        int b = memory.getByte(registers.getPC().getValue());
        registers.incrementPC();
        return b;
    }

    private Integer getByteFromMemory(Integer operand, InstructionFlags flags) throws InvalidAddressException, InvalidFlagsException {
        Integer address = getOperandAddress(operand, flags);
        if (flags.isImmediete()) {
            return address & 0xff;
        } else if (flags.isSimple()) {
            return memory.getByte(address);
        } else if (flags.isIndirect()) {
            return memory.getWord(memory.getByte(address));

        } else throw new InvalidFlagsException();
    }

    private Integer getWordFromMemory(Integer operand, InstructionFlags flags) throws InvalidAddressException, InvalidFlagsException {
        Integer address = getOperandAddress(operand, flags);
        if (flags.isImmediete()) {
            return address;
        } else if (flags.isSimple()) {
            return memory.getWord(address);
        } else if (flags.isIndirect()) {
            return memory.getWord(memory.getWord(address));
        } else throw new InvalidFlagsException();
    }

    private Long getDoubleWordFromMemory(Integer operand, InstructionFlags flags) throws InvalidAddressException, InvalidFlagsException {
        Integer address = getOperandAddress(operand, flags);
        if (flags.isSimple()) {
            return memory.getDoubleWord(address);
        } else if (flags.isIndirect()) {
            return memory.getDoubleWord(memory.getWord(address));
        } else throw new InvalidFlagsException();

    }


    private Integer getOperandAddress(Integer operand, InstructionFlags flags) throws InvalidFlagsException {
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
        if (address == null) throw new InvalidFlagsException();
        return address;
    }

    private boolean processAndCheckForException() {
        try {
            process();
            return false;
        } catch (InvalidAddressException e) {
            LOG.error("Wrong address", e);
            return true;
        } catch (InvalidFlagsException e) {
            LOG.error("Wrong flags of instruction", e);
            return true;
        } catch (OutOfRangeException e) {
            LOG.error("Number in registers is out of range", e);
            return true;
        } catch (NoSuchOpcodeException e) {
            LOG.error("No such opcode in SIC/XE", e);
            return true;
        }

    }

}
