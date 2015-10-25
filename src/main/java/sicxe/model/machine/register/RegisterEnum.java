package sicxe.model.machine.register;

/**
 * Created by maciek on 24.10.15.
 */
public enum RegisterEnum {
    A(0), X(1), L(2), B(3), S(4), T(5), F(6), PC(8), SW(9);

    public final int index;

    RegisterEnum(int index) {
        this.index = index;
    }


}
