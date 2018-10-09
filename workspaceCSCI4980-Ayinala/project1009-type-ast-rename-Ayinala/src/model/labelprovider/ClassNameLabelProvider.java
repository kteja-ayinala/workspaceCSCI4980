package model.labelprovider;

import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.widgets.Text;

import model.ProgramElement;

public class ClassNameLabelProvider extends ProgElemLabelProvider {
	public ClassNameLabelProvider(final Text searchText) {
		this.searchText = searchText;
	}

	@Override
	public void update(ViewerCell cell) {
		super.update(cell);
	}

	protected String getCellText(ViewerCell cell) {
		ProgramElement p = (ProgramElement) cell.getElement();
		String cellText = p.getClassName();
		return cellText;
	}
}
