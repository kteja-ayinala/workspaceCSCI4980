package handler;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;

import model.MyPerson;
import model.MyPersonModelProvider;
import view.SimpleTableAddingDeletingAyinalaViewer;

public class ExportHandler {
	@Inject
	private ESelectionService selectionService;
	String file = "/Users/krishnatejaayinala/Desktop/project0920-Ayinala/export.csv";

	@Execute
	public void execute(EPartService epartService) throws IOException {
		MPart findPart = epartService.findPart(SimpleTableAddingDeletingAyinalaViewer.ID);
		Object findPartObj = findPart.getObject();

		if (findPartObj instanceof SimpleTableAddingDeletingAyinalaViewer) {
			SimpleTableAddingDeletingAyinalaViewer v = (SimpleTableAddingDeletingAyinalaViewer) findPartObj;
			Object selection = selectionService.getSelection();
			export(selection);
			v.refresh();
		}
	}

	private void export(Object selection) throws IOException {
		FileWriter fileWriter = new FileWriter(file);
		PrintWriter printWriter = new PrintWriter(fileWriter);
		printWriter.print(selection);
		printWriter.close();
		System.out.println("exported");

	}

}