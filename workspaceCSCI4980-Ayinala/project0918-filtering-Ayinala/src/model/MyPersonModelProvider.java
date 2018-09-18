package model;

import java.util.ArrayList;
import java.util.List;

import util.UtilFile;

public enum MyPersonModelProvider {
	INSTANCE(getFilePath());

	private List<MyPerson> persons;

	private MyPersonModelProvider() {
		persons = new ArrayList<MyPerson>();
	}

	private MyPersonModelProvider(String inputdata) {
		List<String> contents = UtilFile.readFile(inputdata);
		List<List<String>> tableContents = UtilFile.convertTableContents(contents);

		persons = new ArrayList<MyPerson>();
		for (List<String> iList : tableContents) {
			persons.add(new MyPerson(iList.get(0), iList.get(1), iList.get(2)));
		}
	}

	private static String getFilePath() {
		return "/Users/krishnatejaayinala/workspaceCSCI4980/workspaceCSCI4980-Ayinala/project0918-filtering-Ayinala/src/inputdata.txt";
	}

	public List<MyPerson> getPersons() {
		return persons;
	}
}
