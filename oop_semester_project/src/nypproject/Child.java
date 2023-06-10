package nypproject;

import java.util.ArrayList;

	public class Child implements java.io.Serializable {
		private static final long serialVersionUID = -5734186453284785328L;
		private String email;
		private String password;
		private	ArrayList<Exercise> solvedExercises;
		private Parent parent;
	
	public Child(String email, String password, Parent parent) {
		this.email = email;
		this.password = password;
		this.parent = parent;
		solvedExercises = new ArrayList<>();	
	}
	

	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
	public ArrayList<Exercise> getSolvedExercises() {
		return solvedExercises;
	}

	public void setSolvedExercises(ArrayList<Exercise> solvedExercises) {
		this.solvedExercises = solvedExercises;
	}


	public Parent getParent() {
		return parent;
	}
	public void setParent(Parent parent) {
		this.parent = parent;
	}

	public void solveAnExercise(Exercise exercise) {
		solvedExercises.add(exercise);
		exercise.solveExerciseByOne(this);
	}



	@Override
	public String toString() {
		return email;
	}
	
}
