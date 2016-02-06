package sicxe.model.simulator.assembler.exceptions.parse;

import java.util.List;

/**
 * Created by maciek on 14/01/16.
 */
public class ParseErrors extends Throwable{
    List<ParseError> errors;

    public List<ParseError> getErrors() {
        return errors;
    }

    public void setErrors(List<ParseError> errors) {
        this.errors = errors;
    }

    public ParseErrors(List<ParseError> errors) {
        this.errors = errors;

    }

    @Override
    public String toString() {
        return "ParseErrors" +
                "\n" + printList();
    }

    private String printList(){
        String errorList ="";
        for (ParseError error : errors) {
            errorList = errorList + error.toString() + "\n";

        }
        return errorList;
    }
}
