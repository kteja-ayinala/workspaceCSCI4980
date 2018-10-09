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

import model.ProgramElement;
import util.SearchUtil;

public class ProgElemLabelProvider extends StyledCellLabelProvider {
	protected Text				searchText;
	protected static Color	colorYellow	= Display.getCurrent().getSystemColor(SWT.COLOR_YELLOW);

	public ProgElemLabelProvider(final Text searchText) {
		this.searchText = searchText;
	}

	public ProgElemLabelProvider() {
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
		ProgramElement p = (ProgramElement) cell.getElement();
		String cellText = p.getPkgName();
		return cellText;
	}
}
