package model;

import java.util.ArrayList;
import java.util.List;

public enum PersonModelProvider {
   INSTANCE;

   private List<Person> persons;

   private PersonModelProvider() {
      persons = new ArrayList<Person>();
      persons.add(new Person("FirstNameA", "LastNameA", "female", true));
      persons.add(new Person("FirstNameB", "LastNameB", "female", true));
      persons.add(new Person("FirstNameC", "LastNameC", "female", true));
      persons.add(new Person("FirstNameD", "LastNameD", "male", true));
      persons.add(new Person("FirstNameE", "LastNameE", "male", false));
   }

   public List<Person> getPersons() {
      return persons;
   }
}
