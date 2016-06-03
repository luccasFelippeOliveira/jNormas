/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analisador.lexico;

/**
 * Numeber Token for numbers -- date--
 * @author luccas
 */
public class NumberToken implements TokenMark {
		private int value; /*All numbers used are integers, failure to convert indicates error */
		
		public NumberToken(int intToken) {
				this.value = intToken;
		}
		
		public int getValue() {
				return this.value;
		}
		
		@Override
		public String toString() {
				return Integer.toString(value);
		}
		
}
