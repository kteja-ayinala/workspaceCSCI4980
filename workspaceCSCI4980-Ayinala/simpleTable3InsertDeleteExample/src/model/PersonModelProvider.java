package model;

import java.util.ArrayList;
import java.util.List;

public enum PersonModelProvider {
	INSTANCE;

	private List<Person> persons;

	private PersonModelProvider() {
		persons = new ArrayList<Person>();
		persons.add(new Person("Rainer", "Zufall", "male", true));
		persons.add(new Person("Reiner", "Babbel", "male", true));
		persons.add(new Person("Marie", "Dortmund", "female", false));
		persons.add(new Person("Holger", "Adams", "male", true));
		persons.add(new Person("Juliane", "Adams", "female", true));
	}

	public List<Person> getPersons() {
		return persons;
	}
}
