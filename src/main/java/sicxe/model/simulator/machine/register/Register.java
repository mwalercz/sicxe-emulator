package sicxe.model.simulator.machine.register;

/**
 * Created by maciek on 24.10.15.
 */

public class Register {
    private int index;

    public Register() {

    }
    public Register(RegisterEnum regEnum) {
        this.index = regEnum.index;
    }
    public Register(int index){
        this.index = index;
    }


    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
