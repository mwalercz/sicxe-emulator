package sicxe.model.simulator.assembler.objectprogram;

import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * Created by maciek on 11/01/16.
 */
public class ObjectProgram {
    private HeaderRecord headerRecord;
    private LinkedList<TextRecord> textRecords = new LinkedList<>();
    private EndRecord endRecord;


    public ObjectProgram(Integer startingAddress, String programName) {
        this.headerRecord = new HeaderRecord(programName, startingAddress);
        this.endRecord = new EndRecord(startingAddress);
    }

    public void storeInstruction(String instruction){
        if(canTextRecordStoreInstruction(instruction.length())){
            storeInstructionInTextRecord(instruction);
        } else {
            createNewTextRecord();
            storeInstructionInTextRecord(instruction);
        }
    }

    private boolean canTextRecordStoreInstruction(Integer instructionLength){
        if(textRecords.isEmpty()){
            return false;
        } else {
            return (textRecords.getLast().hasSpaceForNewInstruction(instructionLength));
        }
    }

    private void storeInstructionInTextRecord(String instruction){
        textRecords.getLast().storeInstruction(instruction);
    }

    private void createNewTextRecord(){
        if(textRecords.isEmpty()){
            textRecords.add(new TextRecord(headerRecord.getStartingAddress()));
        } else{
            TextRecord last = textRecords.getLast();
            Integer newStartingAddress =
                    last.getObjectCodeLength() + last.getStartingAddress();
            textRecords.add(new TextRecord(newStartingAddress));
        }

    }
}
