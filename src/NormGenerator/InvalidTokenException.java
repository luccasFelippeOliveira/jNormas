package NormGenerator;

import analisador.lexico.TokenMark;

/**
 * Created by Luccas Oliveira on 04/04/2016.
 */
public class InvalidTokenException extends RuntimeException {
    private TokenMark invalidTokenMark;

    public InvalidTokenException(TokenMark tokenMark) {
        super("Invalid token: " + tokenMark.toString());
    }
}
