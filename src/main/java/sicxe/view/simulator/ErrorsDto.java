package sicxe.view.simulator;

import java.util.List;

/**
 * Created by maciek on 27/01/16.
 */
public class ErrorsDto {
    private List<String> errors;

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public void add(String error){
        errors.add(error);
    }
}
