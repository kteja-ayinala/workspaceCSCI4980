package model.sorter;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;

import model.MyPerson;

public class PersonSorter extends ViewerComparator {
	private int propertyIndex;
	private static final int DESCENDING = 1;
	private int direction = DESCENDING;

	public PersonSorter() {
		this.propertyIndex = 0;
		direction = DESCENDING;
	}

	public int getDirection() {
		return direction == 1 ? SWT.DOWN : SWT.UP;
	}

	public void setColumn(int column) {
		if (column == this.propertyIndex) {
			direction = 1 - direction;
		} else {
			this.propertyIndex = column;
			direction = DESCENDING;
		}
	}

	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {
		MyPerson p1 = (MyPerson) e1;
		MyPerson p2 = (MyPerson) e2;
		int rc = 0;
		switch (propertyIndex) {
		case 0:
			rc = p1.getFirstName().compareTo(p2.getFirstName());
			break;
		case 1:
			rc = p1.getLastName().compareTo(p2.getLastName());
			break;
		case 2:
			rc = p1.getPhn().compareTo(p2.getPhn());
			break;
		case 3:
			rc = p1.getAddress().compareTo(p2.getAddress());
			break;

		default:
			rc = 0;
		}
		if (direction == DESCENDING) {
			rc = -rc;
		}
		return rc;
	}

}
