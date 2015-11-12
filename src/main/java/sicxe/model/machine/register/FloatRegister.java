package sicxe.model.machine.register;

import org.springframework.stereotype.Service;
import sicxe.model.commons.SICXE;

/**
 * Created by maciek on 24.10.15.
 */
@Service
public class FloatRegister extends Register {

    private Double value;

    public FloatRegister() {
        value = 0.0;
    }

    public FloatRegister(RegisterEnum name) {
        super(name);
        value = 0.0;
    }
    public FloatRegister(FloatRegister reg){
        super(reg.getIndex());
        value = new Double(reg.getValue());

    }


    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = SICXE.convertDoubleToFloat(value);
    }

}
