package sicxe.model.simulator.assembler.exceptions;

import sicxe.model.simulator.machine.register.IntegerRegister;

/**
 * Created by maciek on 23/01/16.
 */
public class NewBaseException extends Throwable {
    private String baseName;
    private Integer currentLocation;

    public String getBaseName() {
        return baseName;
    }

    public void setBaseName(String baseName) {
        this.baseName = baseName;
    }

    public Integer getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Integer currentLocation) {
        this.currentLocation = currentLocation;
    }

    public NewBaseException(String baseName, Integer currentLocation) {
        this.baseName = baseName;
        this.currentLocation = currentLocation;
    }

}
