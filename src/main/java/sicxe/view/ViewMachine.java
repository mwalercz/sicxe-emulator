package sicxe.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by maciek on 11.11.15.
 */
public class ViewMachine {
    private Object memory = new Object();
    private ViewRegisters registers;

    public ViewRegisters getRegisters() {
        return registers;
    }

    public void setRegisters(ViewRegisters registers) {
        this.registers = registers;
    }

    public Object getMemory() {
        return memory;
    }

    public void setMemory(Object memory) {
        this.memory = memory;
    }
}
