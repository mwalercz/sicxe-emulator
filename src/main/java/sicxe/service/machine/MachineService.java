package sicxe.service.machine;

import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import sicxe.model.simulator.assembler.Assembler;
import sicxe.model.simulator.assembler.exceptions.asm.AsmErrors;
import sicxe.model.simulator.assembler.exceptions.parse.ParseErrors;
import sicxe.model.simulator.assembler.listing.ProgramListing;
import sicxe.model.simulator.assembler.objectprogram.ObjectProgram;
import sicxe.model.simulator.commons.exceptions.InvalidAddressException;
import sicxe.model.simulator.commons.exceptions.MachineException;
import sicxe.model.simulator.disassembler.Disassembler;
import sicxe.model.simulator.loader.Loader;
import sicxe.model.simulator.machine.Machine;
import sicxe.model.simulator.machine.Memory;
import sicxe.model.simulator.machine.Registers;
import sicxe.model.simulator.machine.instruction.Instruction;
import sicxe.model.simulator.machine.instruction.RawInstruction;
import sicxe.service.mappers.MachineViewConverter;
import sicxe.view.simulator.MachineDto;
import sicxe.view.simulator.MachineAndInstructionsDto;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by maciek on 29/01/16.
 */
@Service
@Scope(value = "session")
public class MachineService {

    private Integer linesNumber = 10;
    private Machine machine;
    private Disassembler disassembler;

    @Autowired
    public MachineService(Machine machine, Disassembler disassembler) {
        this.machine = machine;
        this.disassembler = disassembler;
    }

    public MachineAndInstructionsDto assembly(BufferedReader reader) {
        machine.resetAll();
        Pair<ObjectProgram, ProgramListing> assembly = null;
        try {
            assembly = Assembler.assembly(reader);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseErrors parseErrors) {
            MachineAndInstructionsDto machineAndInstructionsDto = new MachineAndInstructionsDto();
            List<String> errors = new ArrayList<>();
            errors.add("Parse Errors");
            parseErrors.getErrors().forEach((error) -> {
                errors.add(error.toString());
            });
            machineAndInstructionsDto.setErrors(errors);
            return machineAndInstructionsDto;
        } catch (AsmErrors asmErrors) {
            MachineAndInstructionsDto machineAndInstructionsDto = new MachineAndInstructionsDto();
            List<String> errors = new ArrayList<>();
            errors.add("Asm Errors");
            asmErrors.getErrors().forEach((error) -> {
                errors.add(error.toString());
            });
            machineAndInstructionsDto.setErrors(errors);
            return machineAndInstructionsDto;
        }
        Memory prevMemory = new Memory(machine.getMemory());
        Registers prevRegisters = new Registers(machine.getRegisters());
        try {
            Loader.load(assembly.getValue0(), machine.getMemory());
        } catch (InvalidAddressException e) {
            e.printStackTrace();
        }
        List<Instruction> instructions = disassembler.safeDisassemble(linesNumber);

        MachineDto machineDto = new MachineDto();
        machineDto.setMemory(MachineViewConverter.convertMemory(prevMemory, machine.getMemory()));
        machineDto.setRegisters(MachineViewConverter.convertRegisters(prevRegisters, machine.getRegisters()));

        return new MachineAndInstructionsDto(machineDto, instructions);

    }

    public MachineAndInstructionsDto step() {
        Machine prevMachine = new Machine(machine);
        MachineDto machineDto = new MachineDto();
        try {
            RawInstruction instruction= machine.process();
            machineDto.setNixbpe(MachineViewConverter.convertNixbpe(instruction));
            machineDto.setMemory(MachineViewConverter.convertMemory(prevMachine.getMemory(), machine.getMemory()));
            machineDto.setRegisters(MachineViewConverter.convertRegisters(prevMachine.getRegisters(), machine.getRegisters()));
        } catch (MachineException e) {
            machine.resetAll();
            MachineAndInstructionsDto machineAndInstructionsDto = new MachineAndInstructionsDto();
            List<String> errors = new ArrayList<>();
            errors.add("Machine internal error");
            errors.add(e.toString());
            machineAndInstructionsDto.setErrors(errors);
            return machineAndInstructionsDto;
        }
        List<Instruction> instructions = disassembler.safeDisassemble(linesNumber);
        return new MachineAndInstructionsDto(machineDto, instructions);
    }
}
