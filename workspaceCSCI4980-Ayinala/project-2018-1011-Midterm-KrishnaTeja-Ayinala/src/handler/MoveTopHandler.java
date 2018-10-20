package handler;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;

import model.MyPerson;
import model.MyPersonModelProvider;
import util.MsgUtil;
import view.SimpleTableView20181011MidtermKrishnaTejaAyinala;

public class MoveTopHandler {
	@Inject
	private ESelectionService selectionService;
	private MyPerson selectedPerson;

	@Execute
	public void execute(EPartService epartService) {
		MPart findPart = epartService.findPart(SimpleTableView20181011MidtermKrishnaTejaAyinala.ID);
		Object findPartObj = findPart.getObject();

		if (findPartObj instanceof SimpleTableView20181011MidtermKrishnaTejaAyinala) {
			SimpleTableView20181011MidtermKrishnaTejaAyinala v = (SimpleTableView20181011MidtermKrishnaTejaAyinala) findPartObj;
			Object selection = selectionService.getSelection();
			tempStore(selection);
			remove(selection);
			moveFirst(selection);
			v.refresh();
			MsgUtil.message("Moved the selected line at first Line.");
		}
	}

	private void moveFirst(Object selection) {
		MyPersonModelProvider personInstance = MyPersonModelProvider.INSTANCE;
		personInstance.getPersons().add(0, selectedPerson);
		return;
	}

	private void tempStore(Object selection) {
		if (selection instanceof MyPerson) {
			selectedPerson = new MyPerson(((MyPerson) selection).getFirstName(), ((MyPerson) selection).getLastName(),
					((MyPerson) selection).getPhn(), ((MyPerson) selection).getAddress());
		}
	}

	private void remove(Object selection) {
		if (selection instanceof MyPerson) {
			MyPerson p = (MyPerson) selection;
			MyPersonModelProvider.INSTANCE.getPersons().remove(p);
			return;
		}
	}
}