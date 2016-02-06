package sicxe.model.simulator.assembler.objectprogram;

import java.util.LinkedList;

/**
 * Created by maciek on 11/01/16.
 */
public class ObjectProgram {
    private HeaderRecord headerRecord;
    private LinkedList<TextRecord> textRecords = new LinkedList<>();
    private EndRecord endRecord;
    private Instruction prevInstruction;


    public ObjectProgram(Integer startingAddress, String programName) {
        this.headerRecord = new HeaderRecord(programName, startingAddress);
        this.endRecord = new EndRecord(startingAddress);
        this.prevInstruction = new Instruction("", startingAddress);
    }

    public void storeInstruction(Instruction instruction){
        if(instruction.getObjectCode().equals("")) return;

        if(canTextRecordStoreInstruction(instruction)){
            storeObjectCodeInTextRecord(instruction.getObjectCode());
            this.prevInstruction = instruction;
        } else {
            createNewTextRecord(instruction.getLocation());
            storeObjectCodeInTextRecord(instruction.getObjectCode());
            this.prevInstruction = instruction;
        }
    }

    private boolean canTextRecordStoreInstruction(Instruction instruction){
        if(textRecords.isEmpty()){
            return false;
        } else {
            return (textRecords.getLast().hasSpaceForNewInstruction(instruction.length())
            && ((prevInstruction.getLocation() + prevInstruction.length()) == instruction.getLocation()));
        }
    }

    private void storeObjectCodeInTextRecord(String objectCode){
        textRecords.getLast().storeObjectCode(objectCode);
    }

    private void createNewTextRecord(Integer location){
        if(textRecords.isEmpty()){
            textRecords.add(new TextRecord(headerRecord.getStartingAddress()));
        } else{
//            TextRecord last = textRecords.getLast();
//            Integer newStartingAddress =
//                    last.getObjectCodeLength() + last.getStartingAddress();
            textRecords.add(new TextRecord(location));
        }

    }

    public HeaderRecord getHeaderRecord() {
        return headerRecord;
    }

    public void setHeaderRecord(HeaderRecord headerRecord) {
        this.headerRecord = headerRecord;
    }

    public LinkedList<TextRecord> getTextRecords() {
        return textRecords;
    }

    public void setTextRecords(LinkedList<TextRecord> textRecords) {
        this.textRecords = textRecords;
    }

    public EndRecord getEndRecord() {
        return endRecord;
    }

    public void setEndRecord(EndRecord endRecord) {
        this.endRecord = endRecord;
    }
}
