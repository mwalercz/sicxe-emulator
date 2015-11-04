package sicxe.model.loader;

import sicxe.model.machine.Memory;

/**
 * Created by maciek on 05.11.15.
 */
public interface Loader {
    void loadObjectFileIntoMemory(Memory memory);
}
