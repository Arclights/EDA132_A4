package data;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

import data.bigram.Bigrams;
import data.bigram.PosBigram;
import data.bigram.WordBigram;

public class Corpus implements Iterable<Word> {

	private ArrayList<ArrayList<Word>> sentences;
	private HashMap<PosBigram, Double> posProbs;
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

	void calculateBigrams() throws FileNotFoundException {
		posProbs = getPosBigramsProbabilities();
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

	public void tag(ArrayList<String> list) {
		ArrayList<Word> output = new ArrayList<>();

		ArrayList<HashMap<String, Double>> table = new ArrayList<>();

		for (String form : list) {
			HashMap<String, Double> wordProbs = getWordProbs(form);

			if (!form.equals("<BOS>")) {
				HashMap<String, Double> prevProbs = table.get(table.size() - 1);

				for (String pos : wordProbs.keySet()) {
					double bestCombProb = -1;

					for (String prevPos : prevProbs.keySet()) {
						PosBigram currBigram = new PosBigram(new Word(pos),
								new Word(prevPos));
						double posProb = 0;
//						if (posProbs.containsKey(currBigram)) {
							posProb = posProbs.get(currBigram);
//						}

						double prevProb = prevProbs.get(prevPos);
						double combProb = posProb * prevProb;

						if (combProb > bestCombProb) {
							bestCombProb = combProb;
						}
					}
					wordProbs.put(pos, bestCombProb * wordProbs.get(pos));
				}
			}
			table.add(wordProbs);
		}

		// Backtrack
		for (int i = 0; i < table.size(); i++) {
			HashMap<String, Double> column = table.get(i);
			String form = list.get(i);
			double maxProb = -1;
			String ppos = "";
			for (String pos : column.keySet()) {
				if (column.get(pos) > maxProb) {
					maxProb = column.get(pos);
					ppos = pos;
				}
			}
			output.add(new Word("?", form, "?", "?", "?", ppos));
		}

		for (Word w : output)
			System.out.println(w);
	}

	/**
	 * Gets each POS the lemma can have and returns the probabilities of the
	 * lemma being that POS mapped to the respective POS
	 * 
	 * @param lemma
	 * @return
	 */
	private HashMap<String, Double> getWordProbs(String lemma) {
		HashMap<String, Double> data = new HashMap<>();
		for (WordBigram wb : wordProb.keySet()) {
			if (wb.getLemma().equals(lemma)) {
				data.put(wb.getPos(), wordProb.get(wb));
			}
		}
		return data;
	}

	private void printTable(ArrayList<HashMap<String, Double>> table) {
		for (int i = 0; i < table.size(); i++) {
			System.out.println("Column " + i + ": " + table.get(i));
		}
	}

}
