package sicxe.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sicxe.service.file.FileService;
import sicxe.service.file.NoSuchFile;
import sicxe.service.machine.MachineService;
import sicxe.view.simulator.MachineAndInstructionsDto;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by maciek on 10.11.15.
 */
@RestController
@RequestMapping("/machine")
@Scope(value = "session")
public class MachineController {

    private FileService fileService;
    private MachineService machineService;
    private static final Logger LOG = LoggerFactory.getLogger(MachineController.class);

    @Autowired
    public MachineController(FileService fileService, MachineService machineService) {
        this.fileService = fileService;
        this.machineService = machineService;
    }

    @RequestMapping(value = "/assembly/{id}", method = RequestMethod.GET)
    public MachineAndInstructionsDto
    assembly(@PathVariable Long id){
        try {
            BufferedReader reader = fileService.getFileById(id);
            return machineService.assembly(reader);
        } catch (NoSuchFile noSuchFile) {
            noSuchFile.printStackTrace();
            LOG.error(noSuchFile.toString());
            return null;
        }
    }

    @RequestMapping(value = "/assembly", method = RequestMethod.POST)
    public MachineAndInstructionsDto
    assembly(@RequestParam("file") MultipartFile file) {
        try {
            BufferedReader reader = fileService.getReader(file);
            return machineService.assembly(reader);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }


    @RequestMapping(value = "/step", method = RequestMethod.GET)
    public MachineAndInstructionsDto step() {
        return machineService.step();
    }


}
