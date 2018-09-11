package model;

import java.util.ArrayList;
import java.util.List;

public enum PersonModelProvider {
   INSTANCE;

   private List<Person> persons;

   private PersonModelProvider() {
      persons = new ArrayList<Person>();
      persons.add(new Person("AFirstName1", "ALastName1", "female", true));
      persons.add(new Person("CFirstName1", "CLastName1", "female", true));
      persons.add(new Person("DFirstName1", "DLastName1", "male", true));
      persons.add(new Person("BFirstName1", "BLastName1", "female", true));
      persons.add(new Person("AFirstName3", "ALastName3", "female", true));
      persons.add(new Person("CFirstName3", "CLastName3", "female", true));
      persons.add(new Person("DFirstName3", "DLastName3", "male", true));
      persons.add(new Person("BFirstName3", "BLastName3", "female", true));
      persons.add(new Person("AFirstName2", "ALastName2", "male", false));
      persons.add(new Person("CFirstName2", "CLastName2", "female", true));
      persons.add(new Person("DFirstName2", "DLastName2", "female", true));
      persons.add(new Person("BFirstName2", "BLastName2", "female", true));
   }

   public List<Person> getPersons() {
      return persons;
   }
}
