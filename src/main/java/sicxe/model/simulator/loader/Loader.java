package sicxe.model.simulator.loader;

import sicxe.model.simulator.machine.Memory;

/**
 * Created by maciek on 05.11.15.
 */
public interface Loader {
    void loadObjectFileIntoMemory(Memory memory);
}
