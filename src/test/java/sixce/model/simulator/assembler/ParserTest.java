package sixce.model.simulator.assembler;

import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import sicxe.Application;
import sicxe.model.simulator.assembler.SymTab;
import sicxe.model.simulator.assembler.command.Command;
import sicxe.model.simulator.assembler.exceptions.*;
import sicxe.model.simulator.assembler.exceptions.asm.AsmException;
import sicxe.model.simulator.assembler.exceptions.asm.DuplicateLabelInSymTabException;
import sicxe.model.simulator.assembler.exceptions.asm.NoLabelInSymTabException;
import sicxe.model.simulator.assembler.exceptions.asm.TooLargeOperandException;
import sicxe.model.simulator.assembler.exceptions.parse.BadLineSyntaxException;
import sicxe.model.simulator.assembler.Parser;
import sicxe.model.simulator.assembler.SourceLine;
import sicxe.model.simulator.assembler.exceptions.parse.ParseError;
import sicxe.model.simulator.commons.OpcodeEnum;

import java.util.HashMap;

/**
 * Created by maciek on 12/01/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class ParserTest {

    private SymTab symTab = new SymTab();

    @Test
    public void parseLineTest() throws BadLineSyntaxException, NotCommandLineException {
        HashMap<String, SourceLine> code = new HashMap<>();
        code.put(" Copy\tStart\t0", new SourceLine("COPY", "START", "0"));
        code.put("\tLDA\t  length", new SourceLine("LDA", "LENGTH"));
        code.put("\t RSUB \t", new SourceLine("RSUB"));
        for (String line : code.keySet()) {
            SourceLine reality = Parser.parseLine(line);
            check(code.get(line), reality);
        }
    }

    @Test
    public void produceCommandTest() throws ParseError, NotCommandLineException, AsmException, EndCommandException {
        String line = "Label\t+LDA\t#1024";
        Command command = Parser.produceCommand(line);
        command.setSymTab(symTab);
        Assert.assertEquals(command.getLabel(), "LABEL");
        command.assign(0);
        String firstByte = String.format("%02X", OpcodeEnum.LDA.opcode + 1);
        String lastWord = String.format("%06X",1024 + (1 << 20));
        String reality = command.translate();
        Assert.assertEquals(firstByte + lastWord, reality);

    }

    @Test
    public void translatePcPlus() throws DuplicateLabelInSymTabException, ParseError, NotCommandLineException, NoLabelInSymTabException, TooLargeOperandException, EndCommandException {
        symTab.store("ONE", 8);
        String line = "label\tSTA\t@ONE";
        Command command = Parser.produceCommand(line);
        command.setSymTab(symTab);
        command.assign(3);
        String reality = command.translate();
        Assert.assertEquals(reality, "0E2002");
    }

    @Test
    public void translatePcMinus() throws DuplicateLabelInSymTabException, ParseError, NotCommandLineException, NoLabelInSymTabException, TooLargeOperandException, EndCommandException {
        symTab.store("ONE", 4);
        String line = "label\tSTA\t@ONE";
        Command command = Parser.produceCommand(line);
        command.setSymTab(symTab);
        command.assign(3);
        String reality = command.translate();
        Assert.assertEquals(reality, "0E2FFE");
    }


    private void check(SourceLine prediction, SourceLine reality){
        Assert.assertEquals(prediction.getInstruction(), reality.getInstruction());
        Assert.assertEquals(prediction.getLabel(), reality.getLabel());
        Assert.assertEquals(prediction.getOperand(),reality.getOperand());
    }
}
