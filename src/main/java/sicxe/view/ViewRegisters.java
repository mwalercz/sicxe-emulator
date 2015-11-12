package sicxe.view;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by maciek on 11.11.15.
 */
public class ViewRegisters {

    private Object integers = new Object();
    private Double F;
    private String CC;


    public Object getIntegers() {
        return integers;
    }

    public void setIntegers(Object integers) {
        this.integers = integers;
    }

    public Double getF() {
        return F;
    }

    public void setF(Double f) {
        this.F = f;
    }

    public String getCC() {
        return CC;
    }

    public void setCC(String CC) {
        this.CC = CC;
    }
}
