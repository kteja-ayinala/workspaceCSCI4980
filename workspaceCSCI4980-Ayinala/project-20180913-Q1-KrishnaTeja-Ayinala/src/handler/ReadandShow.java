
package handler;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.swt.widgets.Shell;

import view.SimpleView20180913Q1KrishnaTejaAyinala;

public class ReadandShow {

	@Inject
	private EPartService epartService;
	private String str = "";

	@Execute
	public void execute(Shell shell) {

		List<String> contents = readFile(
				"/Users/krishnatejaayinala/workspaceCSCI4980/workspaceCSCI4980-Ayinala/project-20180913-Q1-KrishnaTeja-Ayinala/src/config.txt");
		for (int i = 0; i < contents.size(); i++) {
			String line = contents.get(i);
			System.out.println("[DBG] Line " + i + " - " + line);

			String name = line.split(":")[1].trim();
			System.out.println("[DBG] \tName: " + name);
			// MessageDialog.openInformation(shell, "Name:", "hello " + name);
			str = name;
		}

		MPart findPart = epartService.findPart(SimpleView20180913Q1KrishnaTejaAyinala.VIEW_ID);
		Object findPartObj = findPart.getObject();
		if (findPartObj instanceof SimpleView20180913Q1KrishnaTejaAyinala) {
			SimpleView20180913Q1KrishnaTejaAyinala v = (SimpleView20180913Q1KrishnaTejaAyinala) findPartObj;
			v.appendText("Hello" + " " + str);
		}
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