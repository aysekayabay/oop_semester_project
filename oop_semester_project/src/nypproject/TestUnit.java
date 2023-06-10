package nypproject;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

public class TestUnit {

	@Test
	public void findChildTest() {
		ArrayList<Child> children = new ArrayList<>();
		Parent parent = new Parent("parent@mail.com", "admin1234");
		children.add(new Child("childA@mail.com", "AAAA", parent));
		children.add(new Child("childB@mail.com", "BBBB", parent));
		children.add(new Child("childC@mail.com", "CCCC", parent));
		children.add(new Child("childD@mail.com", "DDDD", parent));
		assertEquals(null, Login.findChild("childA@mail.com", "CCCC", children));
	}
	

	@Test
	public void findParentTest() {
		ArrayList<Parent> parents= new ArrayList<>();
		Parent parent1 = new Parent("parentA@mail.com", "A1234");
		Parent parent2 = new Parent("parentB@mail.com", "B1234");
		Parent parent3 = new Parent("parentC@mail.com", "C1234");
		Parent parent4 = new Parent("parentD@mail.com", "D1234");
		parents.add(parent1);
		parents.add(parent2);
		parents.add(parent3);
		parents.add(parent4);
		assertEquals(parent3, Login.findParent("parentC@mail.com", "C1234", parents));
	}

	@Test
	public void solveAnExerciseTest() {
		Exercise exercise = new Exercise();
		Parent parent = new Parent("parentA@mail.com", "A1234");
		Child child = new Child("childA@mail.com", "AAAA", parent);
		child.solveAnExercise(exercise);
		assertEquals(true,exercise.isSolvedBy(child.getEmail()));
	}
	
	@Test
	public void calculateCorrectAnswerCountTest() {
		Exercise exercise = new Exercise();
		for (Question question : exercise.getQuestions()) {
			question.setIsCorrect(false);
		}
		exercise.calculateCorrectAndWrongAnswerCount();
		assertEquals(0, exercise.getCorrectAnswerCount());
	}
	
	@Test
	public void calculateWrongAnswerCountTest() {
		Exercise exercise = new Exercise();
		for (Question question : exercise.getQuestions()) {
			question.setIsCorrect(false);
		}
		exercise.calculateCorrectAndWrongAnswerCount();
		assertEquals(exercise.getQuestions().size(), exercise.getWrongAnswerCount());
	}
	
	
}
