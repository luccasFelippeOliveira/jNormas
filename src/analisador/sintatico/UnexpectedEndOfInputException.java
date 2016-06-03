package analisador.sintatico;

/**
 * Created by luccas on 3/21/16.
 */
public class UnexpectedEndOfInputException extends Exception{
    public UnexpectedEndOfInputException() {
        super("Unexpected End of Input");
    }
}
