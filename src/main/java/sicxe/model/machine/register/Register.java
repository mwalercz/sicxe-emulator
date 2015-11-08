package sicxe.model.machine.register;

/**
 * Created by maciek on 24.10.15.
 */

public class Register {
    private int index;
    private RegisterEnum name;

    public Register() {

    }
    public Register(RegisterEnum name) {
        this.name = name;
        this.index = name.index;
    }

    public RegisterEnum getName() {
        return name;
    }

    public int getIndex() {
        return index;
    }

}
