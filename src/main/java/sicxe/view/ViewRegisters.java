package sicxe.view;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by maciek on 11.11.15.
 */
public class ViewRegisters {

    private Map<String, Integer> intRegisters = new HashMap<>();
    private Double fRegister;
    private String CC;

    public Map<String, Integer> getIntRegisters() {
        return intRegisters;
    }

    public void setIntRegisters(Map<String, Integer> intRegisters) {
        this.intRegisters = intRegisters;
    }

    public Double getfRegister() {
        return fRegister;
    }

    public void setfRegister(Double fRegister) {
        this.fRegister = fRegister;
    }

    public String getCC() {
        return CC;
    }

    public void setCC(String CC) {
        this.CC = CC;
    }
}
