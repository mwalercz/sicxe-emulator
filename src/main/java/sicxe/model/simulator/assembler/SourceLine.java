package sicxe.model.simulator.assembler;

import org.javatuples.Pair;
import sicxe.model.simulator.assembler.command.*;
import sicxe.model.simulator.assembler.exceptions.NoStartException;
import sicxe.model.simulator.assembler.exceptions.NoSuchOpcodeException;
import sicxe.model.simulator.assembler.mnemonic.Mnemonic;
import sicxe.model.simulator.machine.register.RegisterEnum;

import java.util.HashMap;

/**
 * Created by maciek on 11/01/16.
 */
public class SourceLine {
    private HashMap<String, Integer> symTab;
    private Integer location;
    private String label;
    private Mnemonic mnemonic;
    private String operand;
    private String error = "";
    private Command command;

    public Integer assignLocation(Integer currentLocationCounter) throws NoStartException, NoSuchOpcodeException {
        storeInSymTab(label, currentLocationCounter);
        location = currentLocationCounter;
        if (mnemonic.isAsmDirective()) {
            return assignLocationForDirective();
        } else if (mnemonic.isOpcode()) {
            return assignLocationForInstruction();
        } else throw new NoSuchOpcodeException();

    }

    private Integer assignLocationForInstruction() throws NoSuchOpcodeException {
        if (mnemonic.isFormatOne()) {
            command = new FormatOneCommand(mnemonic.getOpcode());
            return location + 1;
        } else if (mnemonic.isFormatTwo()) {
            Pair<RegisterEnum, RegisterEnum> registers = Parser.parseRegisters(operand);
            command = new FormatTwoCommand(mnemonic.getOpcode(), registers);
            return location + 2;
        } else if (mnemonic.isFormatThree()) {
            command = new FormatThreeCommand(mnemonic.getOpcode(), location + 3, base, operand, symTab);
            return location + 3;
        } else if (mnemonic.isFormatFour()) {
            command = new FormatFourCommand(mnemonic.getOpcode(), operand, symTab);
            return location + 4;
        } else throw new NoSuchOpcodeException();
    }

    private Integer assignLocationForDirective() throws NoSuchOpcodeException {
        if (mnemonic.isByte()) {
            String constantOperand = Parser.parseConstant(operand);
            command = new ByteCommand(constantOperand);
            return location + constantOperand.length();
        } else if (mnemonic.isWord()) {
            command = new WordCommand(Integer.parseInt(operand));
            return location + 3;
        } else if (mnemonic.isResb()) {
            Integer absoluteOperand = Parser.parseAbsolute(operand);
            command = new ResbCommand();
            return location + absoluteOperand;
        } else if (mnemonic.isResw()) {
            Integer absoluteOperand = Parser.parseAbsolute(operand);
            command = new ReswCommand();
            return location + 3 * absoluteOperand;
        } else throw new NoSuchOpcodeException();
    }


    private void storeInSymTab(String label, Integer locationCounter) {
        if (label != null) {
            if (symTab.containsKey(label)) addError("duplicate symbol");
            symTab.put(label, locationCounter);
        }
    }


    public Pair<String, Integer> getDataFromFirstLine() throws NoStartException {
        if ("START".equals(label)) {
            location = Integer.parseInt(operand);
            storeInSymTab(label, location);
            return Pair.with(label, location);
        } else throw new NoStartException();
    }

    public String translate() {
        return command.translate();
    }


    public SourceLine(String label, String mnemonic, String operand) {
        this.label = label;
        this.mnemonic = new Mnemonic(mnemonic);
        this.operand = operand;
    }

    public SourceLine(String mnemonic) {
        this.mnemonic = new Mnemonic(mnemonic);
    }

    public SourceLine(String mnemonic, String operand) {
        this.mnemonic = new Mnemonic(mnemonic);
        this.operand = operand;
    }

    public void addError(String newError) {
        error = error + newError;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Mnemonic getMnemonic() {
        return mnemonic;
    }

    public void setMnemonic(Mnemonic mnemonic) {
        this.mnemonic = mnemonic;
    }

    public String getOperand() {
        return operand;
    }

    public void setOperand(String operand) {
        this.operand = operand;
    }

    public HashMap<String, Integer> getSymTab() {
        return symTab;
    }

    public void setSymTab(HashMap<String, Integer> symTab) {
        this.symTab = symTab;
    }
}
