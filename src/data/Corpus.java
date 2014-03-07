package data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import data.bigram.Bigrams;
import data.bigram.PosBigram;
import data.bigram.WordBigram;

public class Corpus implements Iterable<Word> {

	private ArrayList<ArrayList<Word>> sentences;
	private HashMap<PosBigram, Double> posProb;
	private HashMap<WordBigram, Double> wordProb;

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

	void calculateBigrams() {
		posProb = getPosBigramsProbabilities();
		wordProb = getWordBigramsProbabilities();
	}

	private HashMap<PosBigram, Double> getPosBigramsProbabilities() {
		
		Bigrams<PosBigram> b = new Bigrams<>();
		for (ArrayList<Word> sentence : sentences) {
			for (int i = 1; i < sentence.size(); i++) {
				PosBigram bigram = new PosBigram(sentence.get(i),
						sentence.get(i - 1));
				b.addBigram(bigram);
			}
		}
		return b.getProbabilities();
	}

	private HashMap<WordBigram, Double> getWordBigramsProbabilities() {
		Bigrams<WordBigram> b = new Bigrams<>();
		for (ArrayList<Word> sentence : sentences) {
			for (int i = 0; i < sentence.size(); i++) {
				WordBigram bigram = new WordBigram(sentence.get(i));
				b.addBigram(bigram);
			}
		}
		return b.getProbabilities();
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

	public Iterator<Word> getCompleteIterator() {
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

	public void tag(ArrayList<String> list) {
		ArrayList<Word> output = new ArrayList<>();

		Word prev = null;
		for (String lemma : list) {
			HashMap<String, Double> wordProb = getWordProb(lemma);
			Word w;
			if (wordProb.size() == 1) {
				String ppos = wordProb.keySet().iterator().next();
				w = new Word("?", "?", lemma, "?", "?", ppos);
			} else {
				for (String pos : wordProb.keySet()) {
					PosBigram b = new PosBigram(new Word(pos), prev);
					double d = posProb.containsKey(b) ? posProb.get(b) : 0;
					double current = wordProb.get(pos);
					wordProb.put(pos, d * current);
				}
				String ppos = "";
				double prob = -1.0;
				for (String pos : wordProb.keySet()) {
					if (wordProb.get(pos) > prob) {
						prob = wordProb.get(pos);
						ppos = pos;
					}
				}
				w = new Word("?", "?", lemma, "?", "?", ppos);
			}
			output.add(w);
			prev = w;
		}
		for (Word w: output)
			System.out.println(w);
	}

	private HashMap<String, Double> getWordProb(String lemma) {
		HashMap<String, Double> data = new HashMap<>();
		for (WordBigram wb : wordProb.keySet()) {
			if (wb.getLemma().equals(lemma)) {
				data.put(wb.getPos(), wordProb.get(wb));
			}
		}
		return data;
	}
}
