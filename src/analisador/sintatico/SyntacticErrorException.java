package analisador.sintatico;

import analisador.lexico.TokenMark;

/**
 * Created by luccas on 3/21/16.
 */
public class SyntacticErrorException extends Exception {
    public SyntacticErrorException(TokenMark token) {
        super("Syntax Error at token = " + token);
    }
}
