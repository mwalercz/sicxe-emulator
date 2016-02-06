package sicxe.model.simulator.machine.register;

/**
 * Created by maciek on 24.10.15.
 */
public enum RegisterEnum {
    A(0), X(1), L(2), B(3), S(4), T(5), F(6), PC(8), SW(9);

    public final Integer index;

    RegisterEnum(Integer index) {
        this.index = index;
    }

    public static RegisterEnum get(int index) {
        if (RegisterEnum.A.index == index) return RegisterEnum.A;
        if (RegisterEnum.B.index == index) return RegisterEnum.B;
        if (RegisterEnum.X.index == index) return RegisterEnum.X;
        if (RegisterEnum.L.index == index) return RegisterEnum.L;
        if (RegisterEnum.PC.index == index) return RegisterEnum.PC;
        if (RegisterEnum.SW.index == index) return RegisterEnum.SW;
        if (RegisterEnum.S.index == index) return RegisterEnum.S;
        if (RegisterEnum.T.index == index) return RegisterEnum.T;
        if (RegisterEnum.F.index == index) return RegisterEnum.F;

        return null;
    }


}
