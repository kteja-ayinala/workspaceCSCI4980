package model.sorter;

import org.eclipse.jface.viewers.Viewer; 
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;

import model.ProgramElement;

public class ProgElemSorter extends ViewerComparator {
	private int						propertyIndex;
	private static final int	DESCENDING	= 1;
	private int						direction	= DESCENDING;

	public ProgElemSorter() {
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
		ProgramElement p1 = (ProgramElement) e1;
		ProgramElement p2 = (ProgramElement) e2;
		int rc = 0;
		switch (propertyIndex) {
		case 0:
			rc = p1.getPkgName().compareTo(p2.getPkgName());
			break;
		case 1:
			rc = p1.getClassName().compareTo(p2.getClassName());
			break;
		case 2:
			rc = p1.getMethodName().compareTo(p2.getMethodName());
			break;
		case 3:
			if (p1.isReturnVoid() == p2.isReturnVoid()) {
				rc = 0;
			} else
				rc = (p1.isReturnVoid() ? 1 : -1);
			break;
		case 4:
			rc = p1.getParameterSize().compareTo(p2.getParameterSize());
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
