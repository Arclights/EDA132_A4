package baselinetagger;

import java.util.ArrayList;
import java.util.HashMap;

public class WordStatistics {

	HashMap<Word, Integer> stats;

	public WordStatistics() {
		stats = new HashMap<>();
	}

	public void addWord(Word word) {
		int count = stats.containsKey(word) ? stats.get(word) : 0;
		stats.put(word, count + 1);
	}

	public HashMap<String,String> getMostFrequentPos() {
		HashMap<String, Word> map = new HashMap<String, Word>();
		HashMap<String,String> result = new HashMap<>();

		for (Word key : stats.keySet()) {
			if (map.containsKey(key.getLemma())) {
				int freqKey = stats.get(key);
				int freqExisting = stats.get(map.get(key.getLemma()));
				if (freqKey > freqExisting) {
					map.put(key.getLemma(), key);
				}
			} else {
				map.put(key.getLemma(), key);
			}
		}
		for (String key : map.keySet()) {
			Word w = map.get(key);
			result.put(w.getLemma(),w.getPos());
		}
		return result;
	}

	@Override
	public String toString() {
		return stats.toString();
	}

}
