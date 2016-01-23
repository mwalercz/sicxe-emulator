package sicxe.model.simulator.assembler;

import sicxe.model.simulator.assembler.exceptions.asm.DuplicateLabelInSymTabException;
import sicxe.model.simulator.assembler.exceptions.asm.NoLabelInSymTabException;
import sicxe.model.simulator.machine.register.RegisterEnum;

import java.util.HashMap;

/**
 * Created by maciek on 13/01/16.
 */
public class SymTab {

    private HashMap<String, Integer> symTab = new HashMap<>();

    public SymTab() {
        for (RegisterEnum reg : RegisterEnum.values()) {
            symTab.put(reg.toString(), 0);
        }
    }

    public void store(String label, Integer value) throws DuplicateLabelInSymTabException {
        if (label != null) {
            if (symTab.containsKey(label)) throw new DuplicateLabelInSymTabException();
            symTab.put(label, value);
        }
    }

    public Integer get(Object key) throws NoLabelInSymTabException {

        Integer location = symTab.get(key);
        if (location == null) throw new NoLabelInSymTabException();
        else return location;
    }

    @Override
    public String toString() {
        return "SymTab {\n"
                + hashToString() + "}";
    }

    public String hashToString() {
        String res = "";
        for (String s : symTab.keySet()) {
            res = res + "\t(" + s + ", " + symTab.get(s) + ")\n";
        }
        return res;
    }
}
