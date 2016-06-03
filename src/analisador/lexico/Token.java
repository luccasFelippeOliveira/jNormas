/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analisador.lexico;

/**
 * Defines all constant tokens.
 * @author luccas
 */
public enum Token implements TokenMark{
		TOKEN_OBLIGED("obliged"), /*Deontic conpect OBLIGED*/
		TOKEN_FORBIDDEN("forbidden"), /*Deontic conpect FORBIDDEN*/
		TOKEN_PERMISSION("permission"), /*Deontic conpect PERMISSION*/
		TOKEN_ORGANIZATION("organization"), /*Context ORGANIZATION, and entity ORGANIZATION*/
		TOKEN_ENVIRONMENT("environment"), /*Context ENVIRONMENT*/
		TOKEN_AGENT("agent"), /*Entity AGENT*/
		TOKEN_ROLE("role"),/*Entity ROLE*/
		TOKEN_ALL("all"), /*Entity ALL*/
		TOKEN_EQUALS("="), /* = */
		TOKEN_LEFTCURLY("{"),/* { */
		TOKEN_RIGHTCURLY("}"),/* } */
		TOKEN_LEFTPARENTHESIS("("),/* ( */
		TOKEN_RIGHTPARENTHESIS(")"),/* ) */
		TOKEN_COMMA(","),/* , */
		TOKEN_FOWARDSLASH("/");/* / */
		
		public String tokenVal;
		Token(String val) {
				this.tokenVal = val;
		}
		public static Token getToken(String input) {
				for(Token t : values()) {
						if(input.equals(t.tokenVal)) {
								return t;
						}
				}
				return null;
		}
}
