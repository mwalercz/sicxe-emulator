package sicxe.view;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by maciek on 11.11.15.
 */
public class ViewMachine {
    private Map<Integer,Integer> viewMemory = new HashMap<>();
    private ViewRegisters viewRegisters;

    public ViewRegisters getViewRegisters() {
        return viewRegisters;
    }

    public void setViewRegisters(ViewRegisters viewRegisters) {
        this.viewRegisters = viewRegisters;
    }

    public Map<Integer, Integer> getViewMemory() {
        return viewMemory;
    }

    public void setViewMemory(Map<Integer, Integer> viewMemory) {
        this.viewMemory = viewMemory;
    }
}
