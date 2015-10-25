package sicxe.model.machine.register;

import sicxe.model.commons.SICXE;

/**
 * Created by maciek on 24.10.15.
 */
public class FloatRegister extends Register {

    private Double value;

    public FloatRegister(RegisterEnum name) {
        super(name);
        value = 0.0;
    }


    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = SICXE.convertDoubleToFloat(value);
    }

}
