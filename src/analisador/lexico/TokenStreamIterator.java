package analisador.lexico;

import java.util.Iterator;
import java.util.List;

/**
 * Created by luccas on 3/19/16.
 */
public class TokenStreamIterator implements Iterator<TokenMark> {

    private int currentIndex;
    private TokenMark currentToken = null;
    private List<TokenMark> stream;

    public TokenStreamIterator(List<TokenMark> stream) {
        this.stream = stream;
        currentIndex = 0; /*Sets initial position*/
        currentToken = stream.get(currentIndex);
    }

    @Override
    public boolean hasNext() {
        if(currentIndex >= stream.size() ) {
            return false;
        }
        return true;
    }

    @Override
    public TokenMark next() {
        int indexToReturn = currentIndex;
        currentIndex ++;/*Move to next position*/
        return stream.get(indexToReturn);
    }

    @Override
    public void remove() {
        int indexToDelete = currentIndex;
        currentIndex ++; /*Move to next position*/
        stream.remove(indexToDelete);
    }
}
