package sicxe.model.simulator.assembler.exceptions;

/**
 * Created by maciek on 12/01/16.
 */
public class BadLineSyntaxException extends Throwable {
    private int numberOfWords;
    public BadLineSyntaxException(int length) {
        numberOfWords = length;
    }
}
