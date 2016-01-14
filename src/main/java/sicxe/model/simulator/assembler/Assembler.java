package sicxe.model.simulator.assembler;

import sicxe.model.simulator.assembler.exceptions.BadLineSyntaxException;
import sicxe.model.simulator.assembler.objectprogram.ObjectProgram;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by maciek on 11/01/16.
 */
public class Assembler {


    public ObjectProgram assembly(InputStreamReader reader) throws IOException, BadLineSyntaxException {
        List<SourceLine> sourceLines = Parser.parse(reader);
        SourceProgram sourceProgram = new SourceProgram(sourceLines);
        return sourceProgram.assembly();

    }

}
