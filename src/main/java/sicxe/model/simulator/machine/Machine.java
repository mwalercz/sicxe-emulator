package sicxe.model.simulator.machine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import sicxe.model.simulator.commons.OpcodeEnum;
import sicxe.model.simulator.commons.SICXE;
import sicxe.model.simulator.commons.exceptions.InvalidAddressException;
import sicxe.model.simulator.commons.exceptions.InvalidFlagsException;
import sicxe.model.simulator.commons.exceptions.NoSuchOpcodeException;
import sicxe.model.simulator.commons.exceptions.OutOfRangeException;
import sicxe.model.simulator.machine.instruction.InstructionF2;
import sicxe.model.simulator.machine.instruction.InstructionFlags;
import sicxe.model.simulator.machine.instruction.RawInstruction;
import sicxe.model.simulator.machine.register.IntegerRegister;

import static java.lang.Math.toIntExact;

/**
 * Created by maciek on 23.10.15.
 */
@Service
@Scope("session")
public class Machine {

    private static final Logger LOG = LoggerFactory.getLogger(Machine.class);

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


    public Machine(Machine m) {
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

    public RawInstruction process() throws NoSuchOpcodeException, InvalidAddressException, OutOfRangeException, InvalidFlagsException {
        RawInstruction currentInstruction = new RawInstruction();
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
                    throw new NoSuchOpcodeException();
            }
        } else if (currentInstruction.isOpcodeValidForFormatThreeAndFour()) {
            currentInstruction.setByteTwo(getNextByteAndIncrPC());
            currentInstruction.setByteThree(getNextByteAndIncrPC());
            InstructionFlags flags = new InstructionFlags(currentInstruction);
            if (!currentInstruction.isFormatFour()) {
                evaluateFormatThreeFour(currentInstruction.getOperandFormatThree(), flags);
            } else {
                currentInstruction.setByteFour(getNextByteAndIncrPC());
                evaluateFormatThreeFour(currentInstruction.getOperandFormatFour(), flags);
            }
        } else throw new NoSuchOpcodeException();
        return currentInstruction;
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
            case RSUB:
                registers.setPC(registers.getL().getValue());
                break;
            default:
                throw new NoSuchOpcodeException();
        }
    }

    private void evaluateFormatTwo(InstructionF2 instructionF2) throws NoSuchOpcodeException, OutOfRangeException {
        IntegerRegister r2 = registers.get(instructionF2.getR2().index);
        IntegerRegister r1 = registers.get(instructionF2.getR1().index);
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
            case COMPR:
                registers.setSW(r1.getValue() - r2.getValue());
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
                registers.setSW(registers.getX().getValue() - r1.getValue());
                break;
            default:
                throw new NoSuchOpcodeException();

        }
    }

    private void evaluateFormatThreeFour(Integer operand, InstructionFlags flags) throws InvalidAddressException, OutOfRangeException, InvalidFlagsException, NoSuchOpcodeException {

        switch (flags.getOpcodeEnum()) {
            //IMMEDIETE ADDRESSING NOT POSSIBLE
            case STA:
                memory.setWord(getAddress(operand, flags), registers.getA().getValue());
                break;
            case STX:
                memory.setWord(getAddress(operand, flags), registers.getX().getValue());
                break;
            case STL:
                memory.setWord(getAddress(operand, flags), registers.getL().getValue());
                break;
            case STCH:
                memory.setByte(getAddress(operand, flags), registers.getA().getValue());
                break;
            case STB:
                memory.setWord(getAddress(operand, flags), registers.getB().getValue());
                break;
            case STS:
                memory.setWord(getAddress(operand, flags), registers.getS().getValue());
                break;
            case STF:
                memory.setDoubleWord(getAddress(operand, flags), SICXE.convertFloatToLong(registers.getF().getValue()));
                break;
            case STT:
                memory.setWord(getAddress(registers.getT().getValue(), flags), operand);
                break;

            // JUMPS
            case JEQ:
                if (registers.getCC().equals("=")) {
                    registers.setPC(getAddress(operand, flags));
                }
                break;
            case JGT:
                if (registers.getCC().equals(">")) {
                    registers.setPC(getAddress(operand, flags));
                }
                break;
            case JLT:
                if (registers.getCC().equals("<")) {
                    registers.setPC(getAddress(operand, flags));
                }
                break;
            case J:
                registers.setPC(getAddress(operand, flags));
                break;
            case JSUB:
                registers.setL(registers.getPC().getValue());
                registers.setPC(getAddress(operand, flags));
                break;
            case RSUB:
                registers.setPC(registers.getL().getValue());
                break;
            // IMMEDIETE ADDRESSING POSSIBLE
            case LDA:
                registers.setA(loadWord(operand, flags));
                break;
            case LDX:
                registers.setX(loadWord(operand, flags));
                break;
            case LDL:
                registers.setL(loadWord(operand, flags));
                break;

            case ADD:
                registers.setA(registers.getA().getValue() + loadWord(operand, flags));
                break;
            case SUB:
                registers.setA(registers.getA().getValue() - loadWord(operand, flags));
                break;
            case MUL:
                registers.setA(registers.getA().getValue() * loadWord(operand, flags));
                break;
            case DIV:
                registers.setA(registers.getA().getValue() / loadWord(operand, flags));
                break;
            case COMP:
                registers.setSW(loadWord(operand, flags)
                        - registers.getA().getValue());
                break;
            case TIX:
                registers.getX().increment();
                registers.setSW(registers.getX().getValue() - SICXE.convertWordToSignedInt(loadWord(operand, flags)));
                break;

            case AND:
                registers.setA(registers.getA().getValue() & loadWord(operand, flags));
                break;
            case OR:
                registers.setA(registers.getA().getValue() | loadWord(operand, flags));
                break;

            case LDCH:
                registers.setA(((registers.getA().getValue() >>> 8) << 8) | loadByte(operand, flags));
                break;

            case COMPF:
                registers.setSW(toIntExact(loadDoubleWord(operand, flags)
                        - registers.getA().getValue()));
            case ADDF:
                registers.setF(registers.getF().getValue() + SICXE.convertLongToFloat(loadDoubleWord(operand, flags)));
                break;
            case SUBF:
                registers.setF(registers.getF().getValue() - SICXE.convertLongToFloat(loadDoubleWord(operand, flags)));
                break;
            case MULF:
                registers.setF(registers.getF().getValue() * SICXE.convertLongToFloat(loadDoubleWord(operand, flags)));
                break;
            case DIVF:
                registers.setF(registers.getF().getValue() / SICXE.convertLongToFloat(loadDoubleWord(operand, flags)));
                break;
            case LDB:
                registers.setB(loadWord(operand, flags));
                break;
            case LDS:
                registers.setS(loadWord(operand, flags));
                break;
            case LDF:
                registers.setF(loadDoubleWord(operand, flags));
                break;
            case LDT:
                registers.setT(loadWord(operand, flags));
                break;

            case TD:
                break;
            case WD:
                break;
            case RD:
                break;


            default:
                throw new NoSuchOpcodeException();


        }

    }

    private Integer getAddress(Integer operand, InstructionFlags flags) throws InvalidAddressException, InvalidFlagsException {
        Integer address = getOperandAddress(operand, flags);
        return flags.isIndirect() ? memory.getWord(address) : address;
    }



    private int getNextByteAndIncrPC() throws InvalidAddressException, OutOfRangeException {
        int b = memory.getByte(registers.getPC().getValue());
        registers.incrementPC();
        return b;
    }

    private Integer loadByte(Integer operand, InstructionFlags flags) throws InvalidAddressException, InvalidFlagsException {
        Integer address = getOperandAddress(operand, flags);
        if (flags.isImmediete()) {
            return address & 0xff;
        } else if (flags.isSimple()) {
            return memory.getByte(address);
        } else if (flags.isIndirect()) {
            return memory.getWord(memory.getByte(address));

        } else throw new InvalidFlagsException();
    }

    private Integer loadWord(Integer operand, InstructionFlags flags) throws InvalidAddressException, InvalidFlagsException {
        Integer address = getOperandAddress(operand, flags);
        if (flags.isImmediete()) {
            return address;
        } else if (flags.isSimple()) {
            return memory.getWord(address);
        } else if (flags.isIndirect()) {
            return memory.getWord(memory.getWord(address));
        } else throw new InvalidFlagsException();
    }


    private Long loadDoubleWord(Integer operand, InstructionFlags flags) throws InvalidAddressException, InvalidFlagsException {
        Integer address = getOperandAddress(operand, flags);
        if(flags.isImmediete()){
            return new Long(address);
        }
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
            Integer op = SICXE.convertToSigned12BitInt(operand);
            address = op + registers.getPC().getValue();
        }
        if (flags.isIndexed()) {
            address += registers.getX().getValue();
        }
        if (address == null) throw new InvalidFlagsException();
        return address;
    }


}
