package sicxe.model.machine;

import sicxe.model.commons.exceptions.OutOfRange;
import sicxe.model.machine.register.FloatRegister;
import sicxe.model.machine.register.IntegerRegister;
import sicxe.model.machine.register.RegisterEnum;

/**
 * Created by maciek on 24.10.15.
 */
public class Registers {
    private IntegerRegister A, B, X, L, PC, SW, S, T;
    private FloatRegister F;

    Registers() {
        A = new IntegerRegister(RegisterEnum.A);
        B = new IntegerRegister(RegisterEnum.B);
        X = new IntegerRegister(RegisterEnum.X);
        L = new IntegerRegister(RegisterEnum.L);
        PC = new IntegerRegister(RegisterEnum.PC);
        SW = new IntegerRegister(RegisterEnum.SW);
        S = new IntegerRegister(RegisterEnum.S);
        T = new IntegerRegister(RegisterEnum.T);

        F = new FloatRegister(RegisterEnum.F);
    }

    public void reset() {
        try {
            A.setValue(0);
            B.setValue(0);
            X.setValue(0);
            L.setValue(0);
            PC.setValue(0);
            SW.setValue(0);
            S.setValue(0);
            T.setValue(0);
            F.setValue(0.0);
        } catch (OutOfRange e) {
            /**
             * impossible
             */
        }
    }

    public IntegerRegister get(int index) {
        if (RegisterEnum.A.index == index) return A;
        if (RegisterEnum.B.index == index) return B;
        if (RegisterEnum.X.index == index) return X;
        if (RegisterEnum.L.index == index) return L;
        if (RegisterEnum.PC.index == index) return PC;
        if (RegisterEnum.SW.index == index) return SW;
        if (RegisterEnum.S.index == index) return S;
        if (RegisterEnum.T.index == index) return T;

        return null;
    }


    public void incrementPC() throws OutOfRange {
        PC.setValue(PC.getValue() + 1);
    }

    public IntegerRegister getA() {
        return A;
    }

    public IntegerRegister getB() {
        return B;
    }

    public IntegerRegister getX() {
        return X;
    }

    public IntegerRegister getL() {
        return L;
    }

    public IntegerRegister getPC() {
        return PC;
    }

    public IntegerRegister getSW() {
        return SW;
    }

    public IntegerRegister getS() {
        return S;
    }

    public IntegerRegister getT() {
        return T;
    }

    public FloatRegister getF() {
        return F;
    }
}
