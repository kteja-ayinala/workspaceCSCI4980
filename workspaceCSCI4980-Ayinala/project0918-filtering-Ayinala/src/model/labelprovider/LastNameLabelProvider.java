package model.labelprovider;

import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.widgets.Text;

import model.MyPerson;

public class LastNameLabelProvider extends FirstNameLabelProvider {

	public LastNameLabelProvider(Text searchText) {
		super(searchText);
	}

	@Override
	public void update(ViewerCell cell) {
		super.update(cell);
	}

	protected String getCellText(ViewerCell cell) {
		MyPerson person = (MyPerson) cell.getElement();
		String cellText = person.getLastName();
		return cellText;
	}
}

/*
  public class FirstNameLabelProvider extends ColumnLabelProvider {
	@Override
	public String getText(Object element) {
		Person p = (Person) element;
		return p.getLastName();
	}
}
*/