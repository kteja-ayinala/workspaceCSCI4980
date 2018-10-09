package model.filter;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import model.ProgramElement;

public class ProgElemFilter extends ViewerFilter {
	private String searchString;

	public void setSearchText(String s) {
		this.searchString = ".*" + s + ".*";
	}

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (searchString == null || searchString.length() == 0) {
			return true;
		}
		ProgramElement p = (ProgramElement) element;
		if (p.getPkgName().matches(searchString) || //
				p.getClassName().matches(searchString) || //
				p.getMethodName().matches(searchString)) {
			return true;
		}
		return false;
	}
}
