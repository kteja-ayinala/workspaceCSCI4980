package handler;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;

import model.MyPerson;
import model.MyPersonModelProvider;
import view.SimpleTableAddingDeletingAyinalaViewer;

public class DeleteHandler {
	@Inject
	private ESelectionService selectionService;

	@Execute
	public void execute(EPartService epartService) {
		MPart findPart = epartService.findPart(SimpleTableAddingDeletingAyinalaViewer.ID);
		Object findPartObj = findPart.getObject();

		if (findPartObj instanceof SimpleTableAddingDeletingAyinalaViewer) {
			SimpleTableAddingDeletingAyinalaViewer v = (SimpleTableAddingDeletingAyinalaViewer) findPartObj;
			Object selection = selectionService.getSelection();
			remove(selection);
			v.refresh();
		}
	}

	private void remove(Object selection) {
		if (selection instanceof MyPerson) {
			MyPerson p = (MyPerson) selection;
			MyPersonModelProvider.INSTANCE.getPersons().remove(p);
			return;
		}
		if (selection instanceof Object[]) {
			Object[] objs = (Object[]) selection;
			for (int i = 0; i < objs.length; i++) {
				remove(objs[i]);
			}
		}
	}
}