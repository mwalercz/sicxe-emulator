package sicxe.view.simulator;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by maciek on 11.11.15.
 */
@JsonSerialize(include= JsonSerialize.Inclusion.NON_DEFAULT)
public class RegistersDto {

    private Object integers = new Object();
    private String F;
    private String CC;


    public Object getIntegers() {
        return integers;
    }

    public void setIntegers(Object integers) {
        this.integers = integers;
    }

    public String getF() {
        return F;
    }

    public void setF(String f) {
        F = f;
    }

    public String getCC() {
        return CC;
    }

    public void setCC(String CC) {
        this.CC = CC;
    }
}
