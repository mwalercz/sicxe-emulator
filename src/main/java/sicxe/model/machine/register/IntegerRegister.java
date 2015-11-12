package sicxe.model.machine.register;

import org.springframework.stereotype.Service;
import sicxe.model.commons.SICXE;
import sicxe.model.commons.exceptions.OutOfRangeException;

/**
 * Created by maciek on 24.10.15.
 */


public class IntegerRegister extends Register {
    private Integer value;

    public IntegerRegister(RegisterEnum regEnum) {
        super(regEnum);
        value = 0;
    }

    public IntegerRegister(){
        value=0;
    }

    public IntegerRegister(IntegerRegister reg){
        super(reg.getIndex());
        value = new Integer(reg.getValue());

    }
    public Integer getValue() {
        return value;
    }
    /**
     * @TODO
     */
    public Integer getSignedValue() {
        return SICXE.convertWordToSignedInt(value);
    }

    public void setValue(Integer value) throws OutOfRangeException {
        this.value = SICXE.convertIntToUnsignedWord(value);
    }

    public void increment() throws OutOfRangeException {
        setValue(value + 1);
    }




}
