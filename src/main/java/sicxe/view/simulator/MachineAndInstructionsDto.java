package sicxe.view.simulator;


import sicxe.model.simulator.machine.instruction.Instruction;

import java.util.List;

/**
 * Created by maciek on 26/01/16.
 */
public class MachineAndInstructionsDto {
    private MachineDto machine;
    private List<Instruction> instructions;
    private List<String> errors;

    public MachineAndInstructionsDto() {

    }

    public MachineAndInstructionsDto(MachineDto machine, List<Instruction> instructions) {
        this.machine = machine;
        this.instructions = instructions;
    }

    public MachineDto getMachine() {
        return machine;
    }

    public void setMachine(MachineDto machine) {
        this.machine = machine;
    }

    public List<Instruction> getInstructions() {
        return instructions;
    }

    public void setInstructions(List<Instruction> instructions) {
        this.instructions = instructions;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
