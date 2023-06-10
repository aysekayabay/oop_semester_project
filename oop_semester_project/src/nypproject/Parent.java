package nypproject;

import java.util.ArrayList;

public class Parent implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private String email;
	private String password;
	private ArrayList<Exercise> exercises;
	private ArrayList<Child> children;
	
	public Parent(String email, String password) {
		this.email = email;
		this.password = password;
		exercises = new ArrayList<>();
		children = new ArrayList<>();
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

	public ArrayList<Exercise> getExercises() {
		return exercises;
	}

	public void setExercises(ArrayList<Exercise> exercises) {
		this.exercises = exercises;
	}

	public ArrayList<Child> getChildren() {
		return children;
	}

	public void setChildren(ArrayList<Child> children) {
		this.children = children;
	}
	
	public void addExercise(Exercise anExercise) {
		exercises.add(anExercise);
	}
	
}
