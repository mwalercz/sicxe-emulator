package sicxe.model.simulator.assembler;

import org.javatuples.Pair;
import sicxe.model.simulator.assembler.command.Command;
import sicxe.model.simulator.assembler.command.StartDirective;
import sicxe.model.simulator.assembler.exceptions.asm.AsmError;
import sicxe.model.simulator.assembler.exceptions.asm.AsmErrors;
import sicxe.model.simulator.assembler.exceptions.asm.AsmException;
import sicxe.model.simulator.assembler.listing.ListingLine;
import sicxe.model.simulator.assembler.listing.ProgramListing;
import sicxe.model.simulator.assembler.objectprogram.ObjectProgram;
import sicxe.model.simulator.commons.AsmEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maciek on 11/01/16.
 */
public class SourceProgram {

    private Integer startingLocation;
    private Integer currentLocation;
    private List<Command> commands;
    private String programName;
    private SymTab symTab = new SymTab();
    private List<AsmError> errors = new ArrayList<>();


    public SourceProgram(StartDirective startDirective, List<Command> commands) {
        for (Command command : commands) {
            command.setSymTab(symTab);
        }
        this.commands = commands;

        Pair<String, Integer> pair =
                startDirective.getNameAndStartingLocation();
        this.startingLocation = pair.getValue1();
        this.currentLocation = startingLocation;
        this.programName = pair.getValue0();
    }

    public Pair<ObjectProgram, ProgramListing> assembly() throws AsmErrors {
        /* 1st phase */
        for (Command command : commands) {
            try {
                currentLocation = command.assign(currentLocation);
                if (AsmEnum.END.toString().equals(command.getLabel())) break;
            } catch (AsmException e) {
                errors.add(new AsmError(e.getClass().getSimpleName(), command));
            }
        }
        if(!errors.isEmpty()) throw new AsmErrors(errors);

        /* 2nd phase */
        ObjectProgram objectProgram = new ObjectProgram(startingLocation, programName);
        ProgramListing programListing = new ProgramListing();
        for (Command command : commands) {
            try {
                String objectCode = command.translate();
                programListing.add(new ListingLine(command.getLocation(), command.getSourceCode(), objectCode));
                objectProgram.storeInstruction(objectCode);
            } catch (AsmException e) {
               errors.add(new AsmError(e.getClass().getSimpleName(), command));
            }
        }
        if(errors.isEmpty()) return Pair.with(objectProgram, programListing);
        else throw new AsmErrors(errors);
    }



}
