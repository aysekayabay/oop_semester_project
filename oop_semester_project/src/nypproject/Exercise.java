package nypproject;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;

public class Exercise implements java.io.Serializable {
	private static final long serialVersionUID = -8803939552232038672L;
	private String exerciseName;
	private ArrayList<Question> questions;
	private LocalDateTime startTime;
	private long duration;
	private Option option;
	private ArrayList<Child> solvedBy;
	private ArrayList<HighScoreTableModel> highScoreTable = new ArrayList<>();
	private double speedScore;
	private double accuracyScore;
	private int correctAnswerCount;
	private int wrongAnswerCount;

	public Exercise(String exerciseName, Option option) {
		this.exerciseName = exerciseName;
		this.option = option;
		this.questions = new ArrayList<>();
		createQuestions();
		this.solvedBy = new ArrayList<>();
	}

	public void calculateCorrectAndWrongAnswerCount() {
		for (Question question : questions) {
			if (question.getIsCorrect()) {
				correctAnswerCount++;
			} else {
				wrongAnswerCount++;
			}
		}
	}
	
	public ArrayList<HighScoreTableModel> getHighScoreTable() {
		return highScoreTable;
	}
	public void addDataToHighScoreTable(HighScoreTableModel data) {
		highScoreTable.add(data);
	}
	public void setHighScoreTable(ArrayList<HighScoreTableModel> highScoreTable) {
		this.highScoreTable = highScoreTable;
	}

	public void calculateScores() {
		calculateCorrectAndWrongAnswerCount();
		accuracyScore = (double) 100* correctAnswerCount / questions.size() ;
		System.out.println("Correct Answer Count:"+ correctAnswerCount + " Questions Size:" + questions.size()+ "Accuracy Score:" + accuracyScore);
		double seconds = (double) duration / 1000;
		speedScore = (double)100* questions.size() / seconds;
		
		System.out.println("Saniye:"+ seconds + " Questions Size:" + questions.size()+ "Speed Score:" + speedScore);

	}

	public double getSpeedScore() {
		return speedScore;
	}

	public int getCorrectAnswerCount() {
		return correctAnswerCount;
	}

	public void setCorrectAnswerCount(int correctAnswerCount) {
		this.correctAnswerCount = correctAnswerCount;
	}

	public int getWrongAnswerCount() {
		return wrongAnswerCount;
	}

	public void setWrongAnswerCount(int wrongAnswerCount) {
		this.wrongAnswerCount = wrongAnswerCount;
	}

	public void setSpeedScore(double speedScore) {
		this.speedScore = speedScore;
	}

	public double getAccuracyScore() {
		return accuracyScore;
	}

	public void setAccuracyScore(double accuracyScore) {
		this.accuracyScore = accuracyScore;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public ArrayList<Child> getSolvedBy() {
		return solvedBy;
	}

	public void setSolvedBy(ArrayList<Child> solvedBy) {
		this.solvedBy = solvedBy;
	}

	public void setQuestions(ArrayList<Question> questions) {
		this.questions = questions;
	}

	public void setOption(Option option) {
		this.option = option;
	}

	public Exercise() {
		this.exerciseName = "Varsayılan Alıştırma";
		this.option = new Option(1, 10, 1, 10, 5);
		this.questions = new ArrayList<>();
		this.solvedBy = new ArrayList<>();
		createQuestions();

	}

	public String getExerciseName() {
		return exerciseName;
	}

	public void solveExerciseByOne(Child aChild) {
		solvedBy.add(aChild);
	}

	public void setExerciseName(String exerciseName) {
		this.exerciseName = exerciseName;
	}

	public boolean isSolvedBy(String email) {
		for (Child child : solvedBy) {
			if (child.getEmail().equals(email)) {
				return true;
			}
		}
		return false;
	}

	public ArrayList<Question> getQuestions() {
		return questions;
	}

	public void createQuestions() {
		Random random = new Random();
		int num1, num2;
		for (int i = 0; i < option.getN(); i++) {
			num1 = random.nextInt(option.getaEnd() - option.getaStart() + 1) + option.getaStart();
			num2 = random.nextInt(option.getbEnd() - option.getbStart() + 1) + option.getbStart();
			questions.add(new Question(i + 1, num1, num2));
		}

	}

	@Override
	public String toString() {
		return exerciseName + " - " + questions.size() + " Soru";
	}

	public void addExerciseToFile() {

	}

	public Option getOption() {
		return option;
	}

}
