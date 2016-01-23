package sicxe.model.simulator.assembler.exceptions.parse;

/**
 * Created by maciek on 20/01/16.
 */
public class ParseError extends Exception {
    private String line;
    private String cause;
    public ParseError(String cause, String line) {
        this.cause = cause;
        this.line = line;
    }

    @Override
    public String toString() {
        return  "line='" + line + '\t' +
                ", cause='" + cause +'\n';
    }
}
