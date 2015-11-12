package sicxe.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import sicxe.model.commons.exceptions.MachineException;
import sicxe.model.machine.Machine;
import sicxe.service.MachineViewConverter;
import sicxe.view.ViewMachine;

/**
 * Created by maciek on 10.11.15.
 */
@Controller
@RequestMapping("/machine")
@Scope(value = "session")
public class MachineController {

    @Autowired
    private Machine machine;
    private static final Logger LOG = LoggerFactory.getLogger(MachineController.class);

    @RequestMapping(value="/step", method = RequestMethod.GET)
    public
    @ResponseBody
    ViewMachine step() {
        Machine prevMachine = new Machine(machine);
        ViewMachine viewMachine = new ViewMachine();
        try {
            machine.process();
            viewMachine.setMemory(MachineViewConverter.convertMemory(prevMachine.getMemory(), machine.getMemory()));
            viewMachine.setRegisters(MachineViewConverter.convertRegisters(prevMachine.getRegisters(), machine.getRegisters()));
        } catch (MachineException e) {
            machine.resetAll();
            LOG.error("maszyna umarla", e);
        }

        return viewMachine;
    }

}
