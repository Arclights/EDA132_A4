package baselinetagger;

import java.util.Comparator;

public class WordComparator implements Comparator<Word> {

	@Override
	public int compare(Word w1, Word w2) {
		return w1.getLemma().compareTo(w2.getLemma());
	}

}
