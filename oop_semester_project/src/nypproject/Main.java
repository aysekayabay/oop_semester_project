package nypproject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
public class Main {
	public static void main(String[] args) {
		Login login = new Login();
		login.main(args);
	}


	public static void writeFile() {
		System.err.println("This program saves information of your contact to a file on your drive.");
		Parent aParent = new Parent("aysekayabay@gmail.com", "2002");
		ArrayList<Parent> parents = new ArrayList<>();
		ArrayList<Child> children = new ArrayList<>();

		Exercise anExercise1 = new Exercise("Egzersiz 1 - Kolay", new Option(1, 10, 5, 15, 5));
		Exercise anExercise2 = new Exercise("Egzersiz 2 - Kolay ", new Option(10, 20, 1, 10, 8));
		Exercise anExercise3 = new Exercise("Egzersiz 3 - Orta", new Option(10, 20, 1, 20, 10));
		Exercise anExercise4 = new Exercise("Egzersiz 4 - Orta", new Option(10, 20, 1, 11, 10));

		parents.add(aParent);
		Integer parentCount = parents.size();

		children.add(new Child("ibrahim@gmail.com", "ibrahim20", aParent));
		children.add(new Child("selin@gmail.com", "selin20", aParent));
		children.add(new Child("semih@gmail.com", "semih20", aParent));

		aParent.setChildren(children);
		Integer childrenCount = children.size();
		aParent.addExercise(anExercise1);
		aParent.addExercise(anExercise2);
		aParent.addExercise(anExercise3);
		aParent.addExercise(anExercise4);

		try {
			String fileName = "info.dat";
			ObjectOutputStream yazici = new ObjectOutputStream(new FileOutputStream(fileName));
			yazici.writeObject(parentCount);
			for (Parent parent : parents) {
				yazici.writeObject(parent);
			}
			yazici.writeObject(childrenCount);
			for (Child child : children) {
				yazici.writeObject(child);
			}

			yazici.close();
			System.out.println("The information you have entered has been successfully saved in file " + fileName);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
