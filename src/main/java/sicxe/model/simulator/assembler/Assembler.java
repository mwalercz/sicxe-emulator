package sicxe.model.simulator.assembler;

import org.javatuples.Pair;
import org.javatuples.Triplet;
import sicxe.model.simulator.assembler.command.Command;
import sicxe.model.simulator.assembler.command.StartDirective;
import sicxe.model.simulator.assembler.exceptions.asm.AsmErrors;
import sicxe.model.simulator.assembler.exceptions.parse.ParseError;
import sicxe.model.simulator.assembler.exceptions.parse.ParseErrors;
import sicxe.model.simulator.assembler.listing.ProgramListing;
import sicxe.model.simulator.assembler.objectprogram.ObjectProgram;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

/**
 * Created by maciek on 11/01/16.
 */
public class Assembler {

    public static Pair<ObjectProgram, ProgramListing> assembly(BufferedReader reader)
            throws IOException, ParseErrors, AsmErrors {

        Triplet<StartDirective, List<Command>, List<ParseError>>
                commandsAndErrors = Parser.parse(reader);
        List<ParseError> errors = commandsAndErrors.getValue2();

        if(errors.isEmpty()){
            List<Command> commands = commandsAndErrors.getValue1();
            StartDirective startDirective = commandsAndErrors.getValue0();
            SourceProgram sourceProgram = new SourceProgram(startDirective, commands);
            return sourceProgram.assembly();
        } else throw new ParseErrors(errors);


    }

}
