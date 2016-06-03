/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analisadorlexico1_test;

import analisador.lexico.*;

/**
 * @author luccas
 */
public class LexicalAnalyserTest {
    public static void main(String[] args) {
        String input = "identificador 12 OBLIGED ALL some random";
        TokenStream ts;
        LexicalAnalyser la = new LexicalAnalyser(input);
        ts = la.getTokenStream();

        System.out.println(ts.toString());

        /*Printing using iterator*/
        System.out.println("Printing using a iterator");

        for(TokenMark elemento: ts ) {
            System.out.println("Elemento: " + elemento);
            if(elemento instanceof NumberToken) {
                System.out.print(" Is number \n");
            }
            if(elemento instanceof IdentifierToken) {
                System.out.print(" Is identifier");
            }
        }
    }
}
