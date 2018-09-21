package model.labelprovider;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;

import model.Person;
import util.SearchUtil;

public class FirstNameLabelProvider extends StyledCellLabelProvider {
	private Text			searchText;
	private static Color	colorYellow	= Display.getCurrent().getSystemColor(SWT.COLOR_YELLOW);

	public FirstNameLabelProvider(final Text searchText) {
		this.searchText = searchText;
	}

	@Override
	public void update(ViewerCell cell) {
		String cellText = getCellText(cell);

		String search = searchText.getText();
		cell.setText(cellText);
		if (search != null && search.length() > 0) {
			int intRangesCorrectSize[] = SearchUtil.getSearchTermOccurrences(search, cellText);
			List<StyleRange> styleRange = new ArrayList<StyleRange>();
			for (int i = 0; i < intRangesCorrectSize.length / 2; i++) {
				int start = intRangesCorrectSize[i];
				int length = intRangesCorrectSize[++i];
				StyleRange myStyledRange = new StyleRange(start, length, null, colorYellow);

				styleRange.add(myStyledRange);
			}
			cell.setStyleRanges(styleRange.toArray(new StyleRange[styleRange.size()]));
		} else {
			cell.setStyleRanges(null);
		}
		super.update(cell);
	}

	protected String getCellText(ViewerCell cell) {
		Person person = (Person) cell.getElement();
		String cellText = person.getFirstName();
		return cellText;
	}
}

/* 
  public class FirstNameLabelProvider extends ColumnLabelProvider {
	@Override
	public String getText(Object element) {
		Person p = (Person) element;
		return p.getFirstName();
	}
}*/