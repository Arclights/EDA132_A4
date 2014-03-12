package data;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

import data.bigram.Bigrams;
import data.bigram.PosBigram;
import data.bigram.WordBigram;

public class Corpus implements Iterable<Word> {

	private ArrayList<Sentence> sentences;
	private HashMap<PosBigram, Double> posProbs;
	private HashMap<WordBigram, Double> wordProb;

	public Corpus() {
		sentences = new ArrayList<>();
		sentences.add(new Sentence());
	}

	void addWord(int sentence, String[] row) {
		if (sentence == sentences.size()) {
			Sentence list = new Sentence();
			sentences.add(list);
		}
		try {
		String id = row[0];
		String form = row[1];
		String lemma = row[2];
		String plemma = row[3];
		String pos = row[4];
		String ppos = row[5];
		Word w = new Word(id, form, lemma, plemma, pos, ppos);
		ArrayList<Word> givenSentence = sentences.get(sentence);
		givenSentence.add(w);
		} catch (Exception e) {
			System.out.println(Arrays.toString(row));
			throw e;
		}
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

		for (Sentence sentence : sentences) {
			sb.append(sentence).append(nl);
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

	public void tag(Corpus corpus) {
		System.out.println("Tagging...");
		int proc = 0;
		int size = corpus.sentences.size();
		for (int i = 0; i < size; i++) {
			int curr = (100 * i) / size;
			if (curr > proc) {
				proc = curr;
				System.out.printf("%3d%%\t",	curr);
				if (curr % 10 == 0)
					System.out.println();
			}
			Sentence sentence = corpus.sentences.get(i);
			tag(sentence);
		}
		System.out.printf("100%%\t");
	}

	public void tag(Sentence sentence) {

		ArrayList<HashMap<String, Double>> table = new ArrayList<>();
		HashMap<String, Double> bosCol = new HashMap<>();
		bosCol.put("<BOS>", 1.0);
		table.add(bosCol);

		for (Word w : sentence) {
			String form = w.getForm();
			HashMap<String, Double> wordProbs = getWordProbs(form);

			// System.out.println();
			// System.out.println(form);
			if (!form.equals("<bos>")) {
				HashMap<String, Double> prevProbs = table.get(table.size() - 1);
				for (String pos : wordProbs.keySet()) {
					double bestCombProb = -1;
					double bestPrevprob = 0;
					double bestPosProb = 0;
					PosBigram bestBigram = null;

					for (String prevPos : prevProbs.keySet()) {
						PosBigram currBigram = new PosBigram(new Word(pos),
								new Word(prevPos));
						double posProb = 0;
						if (posProbs.containsKey(currBigram)) {
							posProb = posProbs.get(currBigram);
						}

						double prevProb = prevProbs.get(prevPos);
						double combProb = posProb * prevProb;

						if (combProb > bestCombProb) {
							bestCombProb = combProb;
							bestBigram = currBigram;
							bestPrevprob = prevProb;
							bestPosProb = posProb;
						}
					}
					// System.out.println(bestBigram + "*" + pos + " = "
					// + bestPrevprob + "*" + bestPosProb + "*"
					// + wordProbs.get(pos) + " = " + bestCombProb
					// * wordProbs.get(pos));
					wordProbs.put(pos, bestCombProb * wordProbs.get(pos));
				}
				table.add(wordProbs);
			}

		}

		// Backtrack
		for (int i = 1; i < table.size(); i++) {
			HashMap<String, Double> column = table.get(i);
			double maxProb = -1;
			String ppos = "-";
			for (String pos : column.keySet()) {
				if (column.get(pos) > maxProb) {
					maxProb = column.get(pos);
					ppos = pos;
				}
			}
			sentence.get(i).setPpos(ppos);
		}
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

	public void printToFile(String fileName) throws FileNotFoundException {
		PrintWriter pw = new PrintWriter(fileName);
		pw.append(toString());
		pw.close();
	}

	private void printTable(ArrayList<HashMap<String, Double>> table) {
		for (int i = 0; i < table.size(); i++) {
			System.out.println("Column " + i + ": " + table.get(i));
		}
	}

}
