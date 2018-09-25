package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// *****************************************************
// Add JUnit library:
//   Select Your Eclipse Project >> Java Build Path >>
//   Libraries Tab >> Add Libraries.. >> Select JUnit.
// Run this program:
//   Run As >> JUnit Test
//*****************************************************
import org.junit.Test;

public class UtilFile {

    @Test
    public void testReadFileConvertTableContents() {
        List<String> contents = UtilFile.readFile("inputdata.txt");
        List<List<String>> tableContents = UtilFile.convertTableContents(contents);

        for (List<String> iList : tableContents) {
            System.out.println("[DBG] Show the elements:");
            for (String iElem : iList) {
                System.out.println("[DBG]   Elem -> " + iElem);
            }
        }
    }

    public static List<List<String>> convertTableContents(List<String> contents) {
        List<List<String>> tableContents = new ArrayList<List<String>>();

        for (int i = 0; i < contents.size(); i++) {
            String line = contents.get(i);
            if (line == null || line.isEmpty()) {
                continue;
            }

            List<String> listElements = new ArrayList<String>();
            String[] splitedLine = line.split(",");

            for (int j = 0; j < splitedLine.length; j++) {
                String iElem = splitedLine[j].trim();
                listElements.add(iElem);
            }
            tableContents.add(listElements);
        }
        return tableContents;
    }

    public static List<String> readFile(String filePath) {
        List<String> contents = new ArrayList<String>();
        File file = new File(filePath);
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                contents.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (scanner != null)
                scanner.close();
        }
        return contents;
    }
}