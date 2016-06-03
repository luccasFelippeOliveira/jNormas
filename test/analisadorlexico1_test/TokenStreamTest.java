/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analisadorlexico1_test;

import analisador.lexico.Token;
import analisador.lexico.TokenMark;
import analisador.lexico.TokenStream;

/**
 * @author luccas
 */
public class TokenStreamTest {
    public static void main(String[] args) {
        TokenStream stream = new TokenStream();

        TokenMark t1 = Token.getToken("obliged");
        TokenMark t2 = Token.getToken("organization");
        TokenMark t3 = Token.getToken(",");

        stream.addToStream(t1);
        stream.addToStream(t2);
        stream.addToStream(t3);


    }
}
