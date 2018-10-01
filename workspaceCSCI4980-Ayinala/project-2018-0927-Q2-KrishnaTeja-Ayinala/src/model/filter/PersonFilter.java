package model.filter;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import model.MyPerson;

public class PersonFilter extends ViewerFilter {
	private String searchString;

	public void setSearchText(String s) {
		this.searchString = ".*" + s + ".*";
	}

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (searchString == null || searchString.length() == 0) {
			return true; // if the input value is empty, every items are
							// displayed.
		}
		MyPerson p = (MyPerson) element;
		if (p.getFirstName().matches(searchString)) {
			return true;
		}
		if (p.getLastName().matches(searchString)) {
			return true;
		}
		if (p.getPhn().matches(searchString)) {
			return true;
		}
		if (p.getAddress().matches(searchString)) {
			return true;
		}
		return false;
	}
}
