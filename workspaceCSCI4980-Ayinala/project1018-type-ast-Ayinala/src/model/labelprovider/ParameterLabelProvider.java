package model.labelprovider;

import org.eclipse.jface.viewers.ColumnLabelProvider;

import model.ProgramElement;

public class ParameterLabelProvider extends ColumnLabelProvider {
	@Override
	public String getText(Object element) {
		ProgramElement p = (ProgramElement) element;
		return String.valueOf(p.getParameterSize());
	}
}
