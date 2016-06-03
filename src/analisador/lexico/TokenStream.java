/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analisador.lexico;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Token Stream to syntactic analyser
 *
 * @author luccas
 */
public class TokenStream implements Iterable<TokenMark>{
	/*Implementation -- A simple private List*/
	private List<TokenMark> stream;

	public TokenStream() {
		stream = new LinkedList();
	}

	/*Add to stream*/
	public void addToStream(TokenMark token) {
		stream.add(token);
	}

//	/*Get from stream*/
//	public TokenMark getFromStream() {
//		return stream.remove(0);
//	}


	@Override
	public String toString() {
		return stream.toString();
	}

	@Override
	public Iterator<TokenMark> iterator() {
		return new TokenStreamIterator(stream);
	}
}
