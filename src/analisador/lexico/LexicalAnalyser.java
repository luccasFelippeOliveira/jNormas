/*
 * To change rthis license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analisador.lexico;

import java.util.StringTokenizer;

/**
 * Full Lexical Analyser
 *
 * @author luccas
 */
public class LexicalAnalyser {

		private final TokenStream tstream = new TokenStream();
		private String input;

		public LexicalAnalyser(String stringInput) {
				this.input = stringInput;
				doLexicalAnalysis();

		}

		private void doLexicalAnalysis() {
				String tokenString;
				Token t;
				StringTokenizer st = new StringTokenizer(input, "/ = { } ( ) ,", true);
				while (st.hasMoreTokens()) {
						tokenString = st.nextToken();
						if (!" ".equals(tokenString)) {
								if ((t = Token.getToken(tokenString.toLowerCase())) != null) {
										//System.out.println(t);
										tstream.addToStream(t);
								} else {
										addIdentifierToStream(tokenString);

								}
						}
				}

		}

		private void addIdentifierToStream(String tokenString) {
				try {
						int numberToken = Integer.parseInt(tokenString);
						tstream.addToStream(new NumberToken(numberToken));

				} catch (NumberFormatException n) {
						// Is not a number, therefore a identifier
						tstream.addToStream(new IdentifierToken(tokenString));
				}
		}

		public void setInput(String stringInput) {
				this.input = stringInput;
				doLexicalAnalysis();
		}

		public TokenStream getTokenStream() {
				return tstream;
		}


}
