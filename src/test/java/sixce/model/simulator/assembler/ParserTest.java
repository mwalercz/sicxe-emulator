package sixce.model.simulator.assembler;

import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import sicxe.Application;
import sicxe.model.simulator.assembler.exceptions.BadLineSyntaxException;
import sicxe.model.simulator.assembler.exceptions.CommentException;
import sicxe.model.simulator.assembler.Parser;
import sicxe.model.simulator.assembler.SourceLine;

import java.util.HashMap;

/**
 * Created by maciek on 12/01/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class ParserTest {

    @Autowired
    private Parser parser;

    @Test
    public void parseLineTest() throws BadLineSyntaxException, CommentException{
        HashMap<String, SourceLine> code = new HashMap<>();
        code.put(" Copy\tStart\t0", new SourceLine("COPY", "START", "0"));
        code.put("\tLDA\t  length", new SourceLine("LDA", "LENGTH"));
        code.put("\t RSUB \t", new SourceLine("RSUB"));
        for (String line : code.keySet()) {
            SourceLine reality = parser.parseLine(line);
            check(code.get(line), reality);
        }
    }

    private void check(SourceLine prediction, SourceLine reality){
        Assert.assertEquals(prediction.getMnemonic(), reality.getMnemonic());
        Assert.assertEquals(prediction.getLabel(), reality.getLabel());
        Assert.assertEquals(prediction.getOperand(),reality.getOperand());
    }
}
