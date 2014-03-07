package data;

import java.util.ArrayList;
import java.util.Iterator;

import data.bigram.Bigram;
import data.bigram.Bigrams;
import data.bigram.PosBigram;
import data.bigram.WordBigram;

public class Corpus implements Iterable<Word> {

	ArrayList<ArrayList<Word>> sentences;

	public Corpus() {
		sentences = new ArrayList<>();
		sentences.add(new ArrayList<Word>());
		sentences.get(0).add(new Word("<BOS>"));
		sentences.get(0).add(new Word("<EOS>"));

	}

	void addWord(int sentence, String[] row) {
		if (sentence == sentences.size()) {
			ArrayList<Word> list = new ArrayList<Word>();
			list.add(new Word("<BOS>"));
			list.add(new Word("<EOS>"));
			sentences.add(list);
		}
		String id = row[0];
		String form = row[1];
		String lemma = row[2];
		String plemma = row[3];
		String pos = row[4];
		String ppos = row[5];
		Word w = new Word(id, form, lemma, plemma, pos, ppos);
		ArrayList<Word> givenSentence = sentences.get(sentence);
		givenSentence.add(givenSentence.size() - 1, w);
	}
	
	public Bigrams getPosBigrams() {
		Bigrams b = new Bigrams();
		for (ArrayList<Word> sentence : sentences) {
			for (int i = 1; i < sentence.size(); i++) {
				Bigram bigram = new PosBigram(sentence.get(i), sentence.get(i-1));
				b.addBigram(bigram);
			}
		}
		return b;
	}
	
	public Bigrams getWordBigrams() {
		Bigrams b = new Bigrams();
		for (ArrayList<Word> sentence : sentences) {
			for (int i = 0; i < sentence.size(); i++) {
				Bigram bigram = new WordBigram(sentence.get(i));
				b.addBigram(bigram);
			}
		}
		return b;
	}

	@Override
	public String toString() {
		String nl = System.getProperty("line.separator");
		StringBuilder sb = new StringBuilder();

		for (ArrayList<Word> sentence : sentences) {
			for (Word w : sentence) {
				sb.append(w).append(nl);
			}
			sb.append(nl);
		}

		return sb.toString();
	}

	@Override
	public Iterator<Word> iterator() {
		return new CorpusIterator();
	}
	
	public Iterator<Word> getCompleteIterator(){
		return new CompleteCorpusIterator();
	}

	private class CorpusIterator implements Iterator<Word> {

		int sentence = 0;
		int word = 1;

		@Override
		public boolean hasNext() {
			if (sentence == sentences.size())
				return false;
			return true;
		}

		@Override
		public Word next() {
			Word w = sentences.get(sentence).get(word);
			word++;
			if (sentences.get(sentence).size() - 1 == word) {
				word = 1;
				sentence++;
			}
			return w;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();

		}

	}

	public String statistics() {
		int words = 0;
		for (ArrayList<Word> w : sentences)
			words += w.size();

		String s = "Corpus consists of: \n\t" + sentences.size()
				+ " sentences\n\t" + words + " words";
		return s;
	}
	
	private class CompleteCorpusIterator implements Iterator<Word> {

		int sentence = 0;
		int word = 0;

		@Override
		public boolean hasNext() {
			if (sentence == sentences.size())
				return false;
			return true;
		}

		@Override
		public Word next() {
			Word w = sentences.get(sentence).get(word);
			word++;
			if (sentences.get(sentence).size() == word) {
				word = 0;
				sentence++;
			}
			return w;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();

		}

	}
}
