package model.editing;

import org.eclipse.jface.viewers.TableViewer;

import model.MyPerson;

public class LastNameEditingSupport extends FirstNameEditingSupport {
	private final TableViewer viewer;

	public LastNameEditingSupport(TableViewer viewer) {
		super(viewer);
		this.viewer = viewer;
	}

	@Override
	protected Object getValue(Object element) {
		return ((MyPerson) element).getLastName();
	}

	@Override
	protected void setValue(Object element, Object value) {
		((MyPerson) element).setLastName(String.valueOf(value));
		viewer.update(element, null);
	}
}
