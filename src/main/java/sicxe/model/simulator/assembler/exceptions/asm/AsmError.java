package sicxe.model.simulator.assembler.exceptions.asm;

import sicxe.model.simulator.assembler.command.Command;

/**
 * Created by maciek on 20/01/16.
 */
public class AsmError extends Exception {
    private String cause;
    private Command command;

    public AsmError(String cause, Command command) {
        this.cause = cause;
        this.command = command;
    }


    @Override
    public String toString() {
        return  cause + '\n' +
                command + '\n';
    }
}
