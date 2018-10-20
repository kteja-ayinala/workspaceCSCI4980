package handler;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;

import model.MyPerson;
import util.MsgUtil;
import view.SimpleTableView20181011MidtermKrishnaTejaAyinala;

public class ExportSelectedRowHandler {
	@Inject
	private ESelectionService selectionService;
	List<String> selectedList = new ArrayList<String>();
	String file = "/Users/krishnatejaayinala/Desktop/output/output-midterm-1011-KrishnaTeja-Ayinala.csv";

	@Execute
	public void execute(EPartService epartService) throws IOException {
		MPart findPart = epartService.findPart(SimpleTableView20181011MidtermKrishnaTejaAyinala.ID);
		Object findPartObj = findPart.getObject();

		if (findPartObj instanceof SimpleTableView20181011MidtermKrishnaTejaAyinala) {
			SimpleTableView20181011MidtermKrishnaTejaAyinala v = (SimpleTableView20181011MidtermKrishnaTejaAyinala) findPartObj;
			getSelectedRows(selectionService.getSelection());
			export(selectedList);
			v.refresh();
		}
	}

	private void getSelectedRows(Object selection) {
		if (selection instanceof MyPerson) {
			MyPerson person = (MyPerson) selection;
			selectedList.add("\"" + person.getFirstName() + "\"," + person.getLastName() + "\"," + person.getPhn()
					+ "\"," + "\"" + person.getAddress() + "\"" + "\n");
		}

		if (selection instanceof Object[]) {
			Object[] objs = (Object[]) selection;
			for (int i = 0; i < objs.length; i++) {
				getSelectedRows(objs[i]);
			}
		}
	}

	private void export(List<String> selectedList) throws IOException {
		FileWriter fileWriter = new FileWriter(file);
		PrintWriter printWriter = new PrintWriter(fileWriter);
		printWriter.print("FirstName" + "," + "LastName" + "," + "Phone" + "," + "Address" + "\n");
		for (int i = 0; i < selectedList.size(); i++) {
			printWriter.print(selectedList.get(i));
		}
		printWriter.close();
		System.out.println("saved a file: " + file);
		MsgUtil.message("saved a file: " + file);
	}

}