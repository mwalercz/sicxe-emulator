package sicxe.model.simulator.machine.register;

/**
 * Created by maciek on 11.11.15.
 */
public enum IntegerRegisterEnum {
    A(0), X(1), L(2), B(3), S(4), T(5), PC(8), SW(9);

    public final int index;

    IntegerRegisterEnum(int index) {
        this.index = index;
    }
}
