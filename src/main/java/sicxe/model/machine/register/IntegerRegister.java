package sicxe.model.machine.register;

import sicxe.model.commons.SICXE;
import sicxe.model.commons.exceptions.OutOfRange;

/**
 * Created by maciek on 24.10.15.
 */
public class IntegerRegister extends Register {
    private Integer value;

    public IntegerRegister(RegisterEnum name) {
        super(name);
        value = 0;
    }

    public Integer getValue() {
        return value;
    }

    /**
     * @TODO
     */
    public Integer getSignedValue() {
        return value;

    }

    public void setValue(Integer value) throws OutOfRange {
        this.value = SICXE.convertIntToUnsignedWord(value);
    }

    public void increment() {
        value += 1;
    }


}
