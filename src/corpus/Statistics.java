package corpus;

import java.util.HashMap;

public class Statistics {
	private HashMap<String, Integer> wordFreq;
	private HashMap<String, Integer> posFreq;

	public Statistics() {
		wordFreq = new HashMap<>();
		posFreq = new HashMap<>();
	}

	public void addWord(String[] row) {
		int count = wordFreq.containsKey(row[2]) ? wordFreq.get(row[2]) : 0;
		wordFreq.put(row[2], count + 1);
	}

	public void addPos(String[] row) {
		int count = posFreq.containsKey(row[4]) ? posFreq.get(row[4]) : 0;
		posFreq.put(row[4], count + 1);
	}

	public String getWordFreqList() {
		StringBuilder sb = new StringBuilder();
		for (String key : wordFreq.keySet()) {
			sb.append(key).append("\t").append(wordFreq.get(key)).append("\n");
		}
		return sb.toString();
	}

	public String getPosFreqList() {
		StringBuilder sb = new StringBuilder();
		for (String key : posFreq.keySet()) {
			sb.append(key).append("\t").append(posFreq.get(key)).append("\n");
		}
		return sb.toString();
	}

}
