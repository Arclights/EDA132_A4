package data;

public class ProbabilitySentence {
	private Sentence s;
	private double prob;
	
	public void setSentence(Sentence s, double prob) {
		if (prob > this.prob) {
			this.s = s.clone();
			this.prob = prob;
		}
	}
	
	public Sentence getSentence() {
		return s;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("probability: ").append(prob).append("\n");
		sb.append(s.toString());
		return sb.toString();
	}
	
	public double getProb() {
		return prob;
	}
}
