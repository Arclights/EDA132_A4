package baselinetagger;

import java.util.HashMap;

import data.Corpus;
import data.Word;

public class WordStatistics {

	HashMap<String, HashMap<String, Integer>> stats;

	public WordStatistics(Corpus corp) {
		stats = new HashMap<>();
		for (Word w : corp) {
			addWord(w);
		}
	}

	public void addWord(Word word) {
		String form = word.getForm();
		String pos = word.getPos();
		if (stats.containsKey(form)) {
			HashMap<String, Integer> poses = stats.get(form);
			int count = 0;
			if (poses.containsKey(pos)) {
				count = poses.get(pos);
			}
			poses.put(pos, count + 1);
		} else {
			HashMap<String, Integer> poses = new HashMap<>();
			poses.put(pos, 1);
			stats.put(form, poses);
		}

	}

	public HashMap<String, String> getMostFrequentPos() {
		HashMap<String, String> result = new HashMap<>();

		for (String form : stats.keySet()) {
			int highestFreq = 0;
			String mostFreqPos = "";
			HashMap<String, Integer> poses = stats.get(form);
			for (String pos : poses.keySet()) {
				if (poses.get(pos) > highestFreq) {
					highestFreq = poses.get(pos);
					mostFreqPos = pos;
				}
			}
			result.put(form, mostFreqPos);
		}
		return result;
	}

	public void tag(Corpus corpus) {
		HashMap<String, String> mf = getMostFrequentPos();
		for (Word w : corpus) {
			w.setPpos(mf.get(w.getForm()));
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (String form : stats.keySet()) {
			sb.append(form).append("\n");
			HashMap<String, Integer> poses = stats.get(form);
			for (String pos : poses.keySet()) {
				sb.append("\t").append(pos).append(": ").append(poses.get(pos))
						.append("\n");
			}
		}
		return sb.toString();
	}

}
