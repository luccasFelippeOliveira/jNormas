/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analisador.lexico;

/**
 * Represents a non checked identifier -- Just puts on TokenStream
 * @author luccas
 */
public class IdentifierToken implements TokenMark{
		private String value;
		
		public IdentifierToken(String token) {
				value = token;
		}
		
		public String getValue() {
				return value;
		}
		@Override
		public String toString() {
				return value;
		}
}
