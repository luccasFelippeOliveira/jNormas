/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analisadorlexico1_test;

import analisador.lexico.Token;

/**
 *
 * @author luccas
 */
public class TokenTest {
		public static void main(String[] args) {
				Token t;
				
				t = Token.getToken("");
				
				
				System.out.println(t);
		}
		
}
