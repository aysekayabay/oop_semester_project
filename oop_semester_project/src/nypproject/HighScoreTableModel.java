package nypproject;

public class HighScoreTableModel implements java.io.Serializable {
	private static final long serialVersionUID = 2898985650024211640L;
	private String email;
	private double speedScore;
	private double accuracyScore;
	public HighScoreTableModel(String email, double speedScore, double accuracyScore) {
		this.email = email;
		this.speedScore = speedScore;
		this.accuracyScore = accuracyScore;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public double getSpeedScore() {
		return speedScore;
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
	
}
