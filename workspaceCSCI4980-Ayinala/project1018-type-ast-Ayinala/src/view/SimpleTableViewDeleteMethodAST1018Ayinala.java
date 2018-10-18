package view;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.services.EMenuService;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

import model.filter.ProgElemFilter;
import model.labelprovider.ClassNameLabelProvider;
import model.labelprovider.MethodNameLabelProvider;
import model.labelprovider.ParameterLabelProvider;
import model.labelprovider.ProgElemLabelProvider;
import model.labelprovider.ReturnTypeLabelProvider;
import model.sorter.ProgElemSorter;

public class SimpleTableViewDeleteMethodAST1018Ayinala {
	public final static String ID = "project1018-type-ast-ayinala.partdescriptor.simpletableview-deletemethod-ast-1018ayinala";
	public final static String POPUPMENU = "project1018-type-ast-ayinala.popupmenu.mypopupmenu";

	private TableViewer viewer;
	private Text searchText;
	private ProgElemFilter filter;
	private ProgElemSorter progElemSorter;

	@Inject
	private ESelectionService selectionService;

	/**
	 * Create contents of the view part.
	 */
	@PostConstruct
	public void createControls(Composite parent, EMenuService menuService) {
		GridLayout layout = new GridLayout(2, false);
		parent.setLayout(layout);

		createSearchText(parent);
		createViewer(parent);
		// register context menu on the table
		menuService.registerContextMenu(viewer.getControl(), POPUPMENU);
		addKeyEventToSearchText();
	}

	private void createSearchText(Composite parent) {
		Label searchLabel = new Label(parent, SWT.NONE);
		searchLabel.setText("Search: ");
		searchText = new Text(parent, SWT.BORDER | SWT.SEARCH);
		searchText.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL | GridData.HORIZONTAL_ALIGN_FILL));
	}

	private void addKeyEventToSearchText() {
		searchText.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent ke) {
				filter.setSearchText(searchText.getText());
				viewer.refresh();
			}
		});
	}

	private void createViewer(Composite parent) {
		viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		// createColumns(parent, viewer);
		createColumnsProgElem(parent, viewer);
		final Table table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		viewer.setContentProvider(ArrayContentProvider.getInstance());
		// setInput(ModelProvider.INSTANCE.getPersons());
		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();
				Object firstElement = selection.getFirstElement();
				System.out.println("Do something with it: " + firstElement);

				// set the selection to the service
				selectionService.setSelection(selection.size() == 1 ? //
				firstElement : selection.toArray());
			}
		});

		GridData gridData = new GridData();
		gridData.verticalAlignment = GridData.FILL;
		gridData.horizontalSpan = 2;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		viewer.getControl().setLayoutData(gridData);

		filter = new ProgElemFilter();
		viewer.addFilter(filter);
		progElemSorter = new ProgElemSorter();
		viewer.setComparator(progElemSorter);
	}

	public void setInput(Object data) {
		viewer.setInput(data);
	}

	private void createColumnsProgElem(final Composite parent, final TableViewer viewer) {
		String[] titles = { "Package name", "Class name", "Method Name", "isReturnVoid", "Parameter size" };
		int[] bounds = { 100, 100, 100, 100, 100 };

		TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 0);
		col.setLabelProvider(new ProgElemLabelProvider(searchText));

		col = createTableViewerColumn(titles[1], bounds[1], 1);
		col.setLabelProvider(new ClassNameLabelProvider(searchText));

		col = createTableViewerColumn(titles[2], bounds[2], 2);
		col.setLabelProvider(new MethodNameLabelProvider(searchText));

		col = createTableViewerColumn(titles[3], bounds[3], 3);
		col.setLabelProvider(new ReturnTypeLabelProvider());

		col = createTableViewerColumn(titles[4], bounds[4], 4);
		col.setLabelProvider(new ParameterLabelProvider());
	}

	private TableViewerColumn createTableViewerColumn(String title, int bound, final int colNumber) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(viewer, SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		column.setMoveable(true);
		column.addSelectionListener(getSelectionAdapter(column, colNumber));
		return viewerColumn;
	}

	private SelectionAdapter getSelectionAdapter(final TableColumn column, final int index) {
		SelectionAdapter selectionAdapter = new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				progElemSorter.setColumn(index);
				int dir = progElemSorter.getDirection();
				viewer.getTable().setSortDirection(dir);
				viewer.getTable().setSortColumn(column);
				viewer.refresh();
			}
		};
		return selectionAdapter;
	}

	public TableViewer getViewer() {
		return viewer;
	}

	@PreDestroy
	public void dispose() {
	}

	@Focus
	public void setFocus() {
	}

	public void refresh() {
		viewer.refresh();
	}
}
