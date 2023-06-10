package nypproject;

public class Question implements java.io.Serializable {
	private static final long serialVersionUID = -1147688851284862850L;
	private int questionNumber;
	private int a;
	private int b;
	private int answer;
	private boolean isCorrect;
	private long duration;
	
	public Question(int number, int a, int b) {
		this.questionNumber = number;
		this.a = a;
		this.b = b;
		this.answer = a*b;
	}
	 
	
	public boolean getIsCorrect() {
		return isCorrect;
	}


	public void setIsCorrect(boolean isCorrect) {
		this.isCorrect = isCorrect;
	}


	public long getDuration() {
		return duration;
	}


	public void setDuration(long duration) {
		this.duration = duration;
	}


	public int getQuestionNumber() {
		return questionNumber;
	}

	public void setQuestionNumber(int questionNumber) {
		this.questionNumber = questionNumber;
	}

	public int getA() {
		return a;
	}

	public void setA(int a) {
		this.a = a;
	}

	public int getB() {
		return b;
	}

	public void setB(int b) {
		this.b = b;
	}

	public int getAnswer() {
		return answer;
	}

	public void setAnswer(int answer) {
		this.answer = answer;
	}


	@Override
	public String toString() {
		return "Question [questionNumber=" + questionNumber + ", a=" + a + ", b=" + b + ", answer=" + answer
				+ ", isCorrect=" + isCorrect + ", duration=" + duration + "]";
	}


	
}
