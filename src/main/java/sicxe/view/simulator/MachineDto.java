package sicxe.view.simulator;

/**
 * Created by maciek on 11.11.15.
 */
public class MachineDto {
    private Object memory;
    private RegistersDto registers;
    private String nixbpe;

    public String getNixbpe() {
        return nixbpe;
    }

    public void setNixbpe(String nixbpe) {
        this.nixbpe = nixbpe;
    }

    public RegistersDto getRegisters() {
        return registers;
    }

    public void setRegisters(RegistersDto registers) {
        this.registers = registers;
    }

    public Object getMemory() {
        return memory;
    }

    public void setMemory(Object memory) {
        this.memory = memory;
    }
}
