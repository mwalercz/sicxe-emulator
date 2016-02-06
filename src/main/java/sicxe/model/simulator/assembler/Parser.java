package sicxe.model.simulator.assembler;

import org.javatuples.Pair;
import org.javatuples.Triplet;
import sicxe.model.simulator.assembler.AddressingEnum.Addressing;
import sicxe.model.simulator.assembler.AddressingEnum.Indexed;
import sicxe.model.simulator.assembler.command.*;
import sicxe.model.simulator.assembler.exceptions.EndCommandException;
import sicxe.model.simulator.assembler.exceptions.parse.BadOperandInStartDirective;
import sicxe.model.simulator.assembler.exceptions.parse.BadRegistersFormat;
import sicxe.model.simulator.assembler.exceptions.NotCommandLineException;
import sicxe.model.simulator.assembler.exceptions.parse.NoStartException;
import sicxe.model.simulator.assembler.exceptions.parse.*;
import sicxe.model.simulator.assembler.mnemonic.Mnemonic;
import sicxe.model.simulator.machine.register.RegisterEnum;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by maciek on 11/01/16.
 */
public class Parser {

    private static final String alfanumeric = "[A-Z0-9]+";

    public static Triplet<StartDirective, List<Command>, List<ParseError>>
    parse(BufferedReader reader) throws IOException {
        List<Command> commands = new LinkedList<>();
        List<ParseError> errors = new LinkedList<>();
        StartDirective startDirective = new StartDirective();

        try {
            startDirective = produceStartDirective(reader);
        } catch (ParseError e) {
            errors.add(e);
        }
        String thisLine;
        while ((thisLine = reader.readLine()) != null) {
            try {
                Command command = produceCommand(thisLine);
                command.setSourceCode(thisLine);
                commands.add(command);
            } catch (NotCommandLineException e1) {
                continue;
            } catch (EndCommandException e2) {
                break;
            } catch (ParseError e3) {
                errors.add(e3);
                continue;
            }
        }
        return Triplet.with(startDirective, commands, errors);

    }

    private static StartDirective produceStartDirective(BufferedReader reader) throws IOException, ParseError {
        String thisLine = null;
        StartDirective startDirective = new StartDirective();
        try {
            if ((thisLine = reader.readLine()) != null) {
                SourceLine sourceLine = parseLine(thisLine);
                Mnemonic mnemonic = new Mnemonic(sourceLine.getInstruction());
                if (mnemonic.isStart()) {
                    startDirective = new StartDirective(sourceLine.getLabel(), parseInteger(sourceLine.getOperand()));
                } else throw new NoStartException();
            }
        } catch (NotCommandLineException e1) {
            startDirective = produceStartDirective(reader);
        } catch (ParseException e2) {
            throw new ParseError(e2.getClass().getSimpleName(), thisLine);
        }
        return startDirective;
    }

    private static Integer parseInteger(String operand) throws BadOperandInStartDirective {
        if (operand.matches("[0-9]+")) {
            return Integer.parseInt(operand);
        } else throw new BadOperandInStartDirective();

    }

    public static void checkForNotCommand(String line) throws NotCommandLineException {
        if(line.matches("^\\s*\\.+")) throw new NotCommandLineException();
        if(line.matches("^\\s+$")) throw new NotCommandLineException();
        if(line.equals("")) throw new NotCommandLineException();
        if(line.charAt(0) == '.') throw new NotCommandLineException();
    }
    public static SourceLine parseLine(String line) throws NotCommandLineException, BadLineSyntaxException {
        checkForNotCommand(line);
        List<String> tokens = produceTokenList(line);

        if (tokens.size() == 3) {
            String label = tokens.get(0);
            String instruction = tokens.get(1);
            String operand = tokens.get(2);
            return new SourceLine(label, instruction, operand);
        } else if (tokens.size() == 2) {
            String instruction = tokens.get(0);
            String operand = tokens.get(1);
            return new SourceLine(instruction, operand);
        } else if (tokens.size() == 1) {
            String instruction = tokens.get(0);
            return new SourceLine(instruction);
        } else throw new BadLineSyntaxException(tokens.size(), line);
    }

    public static Command produceCommand(String line)
            throws NotCommandLineException, ParseError, EndCommandException {
        try {
            SourceLine sourceLine = parseLine(line.trim());
            Mnemonic mnemonic = new Mnemonic(sourceLine.getInstruction());
            if (mnemonic.isAsmDirective()) {
                return produceAsmDirective(mnemonic, sourceLine.getLabel(), sourceLine.getOperand());
            } else if (mnemonic.isOpcode()) {
                return produceInstruction(mnemonic, sourceLine);
            } else throw new NoSuchOpcodeException();
        } catch (ParseException e) {
            throw new ParseError(e.getClass().getSimpleName(), line);
        }
    }

    public static boolean isNumeric(String operand) {
        return (operand.matches("[0-9A-F]+"));
    }

    private static Command produceInstruction(Mnemonic mnemonic, SourceLine sourceLine)
            throws NoSuchOpcodeException, BadRegistersFormat, NoSuchAddressingException {
        if (mnemonic.isFormatOne()) {
            return new FormatOneCommand(mnemonic.getOpcode(), sourceLine.getLabel());
        } else if (mnemonic.isFormatTwo()) {
            Pair<RegisterEnum, RegisterEnum> registers = parseRegisters(sourceLine.getOperand());
            return new FormatTwoCommand(mnemonic.getOpcode(), sourceLine.getLabel(), registers);
        } else if (mnemonic.isFormatThree()) {
            Triplet<Addressing, Indexed, String> operand = parseMemoryOperand(sourceLine.getOperand());
            return new FormatThreeCommand(mnemonic.getOpcode(), sourceLine.getLabel(), operand);
        } else if (mnemonic.isFormatFour()) {
            Triplet<Addressing, Indexed, String> operand = parseMemoryOperand(sourceLine.getOperand());
            return new FormatFourCommand(mnemonic.getOpcode(), sourceLine.getLabel(), operand);
        } else throw new NoSuchOpcodeException();
    }

    private static Triplet<Addressing, Indexed, String> parseMemoryOperand(String operand)
            throws NoSuchAddressingException {
        if (isImmediate(operand))
            return Triplet.with(Addressing.IMMEDIATE, Indexed.NO, operand.substring(1));
        else if (isIndirect(operand))
            return Triplet.with(Addressing.INDIRECT, Indexed.NO, operand.substring(1));
        else if (isSimpleIndexed(operand))
            return Triplet.with(Addressing.SIMPLE, Indexed.YES, parseIndexed(operand));
        else if (isSimpleNotIndexed(operand))
            return Triplet.with(Addressing.SIMPLE, Indexed.NO, operand);
        else throw new NoSuchAddressingException();
    }

    private static String parseIndexed(String operand) {
        String[] split = operand.split(",");
        return split[0].trim();
    }

    private static boolean isSimpleIndexed(String operand) {
        return (operand.matches(alfanumeric + ",X"));
    }

    private static boolean isSimpleNotIndexed(String operand) {
        return (operand.matches(alfanumeric));

    }

    private static boolean isIndirect(String operand) {
        return (operand.matches("^@" + alfanumeric + "$"));
    }

    private static boolean isImmediate(String operand) {
        return (operand.matches("#" + alfanumeric));
    }


    private static Pair<RegisterEnum, RegisterEnum> parseRegisters(String operand) throws BadRegistersFormat {
        String register = "(A|X|L|PC|SW|B|S|T|F)";
        if (operand.matches(register)) {
            RegisterEnum r1enum = RegisterEnum.valueOf(operand);
            return Pair.with(r1enum, null);
        } else if (operand.matches(register + "," + register)) {
            String[] split = operand.split(",");
            String r1 = split[0].trim();
            String r2 = split[1].trim();
            RegisterEnum r1enum = RegisterEnum.valueOf(r1);
            RegisterEnum r2enum = RegisterEnum.valueOf(r2);
            return Pair.with(r1enum, r2enum);
        } else throw new BadRegistersFormat();
    }

    private static Command produceAsmDirective(Mnemonic mnemonic, String label, String operand)
            throws NoSuchOpcodeException, OperandNotAbsoluteException, OperandNotConstantException,
            NumberFormatException, OperandNotNumericException, EndCommandException, OperandNotWordException {

        if (mnemonic.isEnd()) throw new EndCommandException();
        else if (mnemonic.isBase()){
            return new BaseCommand(label, parseOperand(operand));
        }
        else if (mnemonic.isByte()) {
            return new ByteCommand(label, parseConstant(operand));
        } else if (mnemonic.isWord()) {
            return new WordCommand(label, parseWord(operand));
        } else if (mnemonic.isResb()) {
            return new ResbCommand(label, parseResx(operand));
        } else if (mnemonic.isResw()) {
            return new ReswCommand(label, parseResx(operand));
        } else throw new NoSuchOpcodeException();
    }

    private static Integer parseWord(String operand) throws OperandNotConstantException {
        if (operand.matches("X'[0-9A-Za-z]+'")) {
            String[] tokens = operand.split("'");
            return Integer.parseInt(tokens[1], 16);
        }
        else throw new OperandNotConstantException();
    }

    private static String parseOperand(String operand) throws OperandNotWordException {
        if(operand.matches("[A-Z]+")) return operand;
        else throw new OperandNotWordException();
    }

    private static Integer parseResx(String operand) throws OperandNotNumericException {
        if (isNumeric(operand)) {
            return Integer.parseInt(operand);
        }
        throw new OperandNotNumericException();
    }

    public static Pair<Boolean, String> checkForExtended(String mnemonic) {
        if (mnemonic.charAt(0) == '+') {
            String instr = mnemonic.substring(1);
            return Pair.with(true, instr);
        } else return Pair.with(false, mnemonic);
    }

    public static String parseConstant(String operand) throws OperandNotConstantException {
        if (operand.matches("C'[a-zA-z]+'")) {
            String[] tokens = operand.split("'");
            return tokens[1];
        } else if (operand.matches("X'[0-9A-Za-z]+'")) {
            String[] tokens = operand.split("'");
            return tokens[1];
        }
        throw new OperandNotConstantException();
    }

    public static Integer parseAbsolute(String operand) throws OperandNotAbsoluteException {
        if (operand.matches("#[0-9]+")) {
            return Integer.parseInt(operand.substring(1));
        }
        throw new OperandNotAbsoluteException();
    }

    private static ArrayList<String> produceTokenList(String line) {
        String delimiters = "[\\s]+";
        String[] tokens = line.toUpperCase().trim().split(delimiters);
        ArrayList<String> nonEmptyTokens = new ArrayList<>();
        for (int i = 0; i < tokens.length; i++) {
            tokens[i] = tokens[i].trim();
            if (!tokens[i].equals("")) {
                nonEmptyTokens.add(tokens[i]);
            }
        }
        return nonEmptyTokens;
    }


}
