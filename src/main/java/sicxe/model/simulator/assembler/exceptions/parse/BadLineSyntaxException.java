package sicxe.model.simulator.assembler.exceptions.parse;

/**
 * Created by maciek on 12/01/16.
 */
public class BadLineSyntaxException extends ParseException {
    private int tokensNumber;
    private String line;
    public BadLineSyntaxException(int length, String line) {
        tokensNumber = length;
        this.line = line;
    }

    @Override
    public String toString() {
        return "BadLineSyntaxException{" +
                "number of tokens=" + tokensNumber +
                ", line='" + line + '\'' +
                '}';
    }
}
