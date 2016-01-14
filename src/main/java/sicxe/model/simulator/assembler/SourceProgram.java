package sicxe.model.simulator.assembler;

import org.javatuples.Pair;
import sicxe.model.simulator.assembler.exceptions.NoStartException;
import sicxe.model.simulator.assembler.exceptions.NoSuchOpcodeException;
import sicxe.model.simulator.assembler.objectprogram.ObjectProgram;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by maciek on 11/01/16.
 */
public class SourceProgram {

    private Integer startingLocation;
    private Integer currentLocation;
    private LinkedList<SourceLine> sourceLines;
    private String programName;
    private SymTab symTab = new SymTab();

    public SourceProgram(LinkedList<SourceLine> sourceLines) throws NoStartException{
        this.sourceLines = sourceLines;
        for (SourceLine sourceLine : sourceLines) {
            sourceLine.setSymTab(symTab);
        }
        setNameAndStartingLocation();

    }

    public ObjectProgram assembly() throws NoStartException, NoSuchOpcodeException {
        /* 1st phase */
        for (SourceLine sourceLine : sourceLines) {
            currentLocation = sourceLine.assignLocation(currentLocation);
            if(sourceLine.getLabel().equals("END")) break;
        }
        /* 2nd phase */
        ObjectProgram objectProgram = new ObjectProgram(startingLocation, programName);
        for (SourceLine sourceLine : sourceLines) {
            objectProgram.storeInstruction(sourceLine.translate());
        }

        return objectProgram;
    }

    private void setNameAndStartingLocation() throws NoStartException{
        Pair<String, Integer> pair = sourceLines.getFirst().getDataFromFirstLine();
        programName = pair.getValue0();
        startingLocation = pair.getValue1();
        currentLocation = startingLocation;
        sourceLines.removeFirst();
    }


}
