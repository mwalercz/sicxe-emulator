package sicxe.model.simulator.assembler.exceptions.asm;

import java.util.List;

/**
 * Created by maciek on 14/01/16.
 */
public class AsmErrors extends Exception{
    private List<AsmError> errors;



    public AsmErrors(List<AsmError> asmExceptionList) {
        this.errors = asmExceptionList;
    }

    @Override
    public String toString() {
        return "AsmErrors" +
                "\n" + printList();
    }

    private String printList(){
        String errorList ="";
        for (AsmError error : errors) {
            errorList = errorList + error.toString();

        }
        return errorList;
    }

    public List<AsmError> getErrors() {
        return errors;
    }

    public void setErrors(List<AsmError> errors) {
        this.errors = errors;
    }
}
