package model.sorter;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;

import model.Person;

public class PersonSorter extends ViewerComparator {
	private int						propertyIndex;
	private static final int	DESCENDING	= 1;
	private int						direction	= DESCENDING;

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
		Person p1 = (Person) e1;
		Person p2 = (Person) e2;
		int rc = 0;
		switch (propertyIndex) {
		case 0:
			rc = p1.getFirstName().compareTo(p2.getFirstName());
			break;
		case 1:
			rc = p1.getLastName().compareTo(p2.getLastName());
			break;
		case 2:
			rc = p1.getGender().compareTo(p2.getGender());
			break;
		case 3:
			if (p1.isMarried() == p2.isMarried()) {
				rc = 0;
			} else
				rc = (p1.isMarried() ? 1 : -1);
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
