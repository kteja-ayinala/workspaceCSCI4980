package view;

//
//import javax.annotation.PostConstruct;
//import javax.annotation.PreDestroy;
//import javax.inject.Inject;
//
//import org.eclipse.e4.ui.di.Focus;
//import org.eclipse.e4.ui.services.EMenuService;
//import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
//import org.eclipse.jface.viewers.ArrayContentProvider;
//import org.eclipse.jface.viewers.ColumnLabelProvider;
//import org.eclipse.jface.viewers.ISelectionChangedListener;
//import org.eclipse.jface.viewers.IStructuredSelection;
//import org.eclipse.jface.viewers.SelectionChangedEvent;
//import org.eclipse.jface.viewers.TableViewer;
//import org.eclipse.jface.viewers.TableViewerColumn;
//import org.eclipse.swt.SWT;
//import org.eclipse.swt.events.KeyAdapter;
//import org.eclipse.swt.events.KeyEvent;
//import org.eclipse.swt.events.SelectionAdapter;
//import org.eclipse.swt.events.SelectionEvent;
//import org.eclipse.swt.layout.GridData;
//import org.eclipse.swt.layout.GridLayout;
//import org.eclipse.swt.widgets.Composite;
//import org.eclipse.swt.widgets.Label;
//import org.eclipse.swt.widgets.Table;
//import org.eclipse.swt.widgets.TableColumn;
//import org.eclipse.swt.widgets.Text;
//
//import model.MyPerson;
//import model.MyPersonModelProvider;
//import model.filter.PersonFilter;
//import model.labelprovider.FirstNameLabelProvider;
//import model.labelprovider.AddressLabelProvider;
//import model.labelprovider.LastNameLabelProvider;
//import model.labelprovider.PhoneLabelProvider;
//import model.sorter.PersonSorter;
//
//public class SimpleTableView20180927Q2KrishnaTejaAyinala {
//
//	public final static String ID = "project0920-ayinala.partdescriptor.simpletableaddingdeletingayinalaviewer";
//	public final static String POPUPMENU = "project0920-ayinala.popupmenu.adddeleteexport";
//	private TableViewer viewer;
//	private PersonFilter filter;
//	private Text searchText;
//	private PersonSorter personSorter;
//
//	@Inject
//	private ESelectionService selectionService;
//
//	/**
//	 * Create contents of the view part.
//	 */
//	@PostConstruct
//	public void createControls(Composite parent, EMenuService menuService) {
//		GridLayout layout = new GridLayout(2, false);
//		parent.setLayout(layout);
//
//		createSearchText(parent);
//		createViewer(parent);
//		// register context menu on the table
//		menuService.registerContextMenu(viewer.getControl(), POPUPMENU);
//		addKeyEventToSearchText();
//	}
//
//	private void createSearchText(Composite parent) {
//		Label searchLabel = new Label(parent, SWT.NONE);
//		searchLabel.setText("Search: ");
//		searchText = new Text(parent, SWT.BORDER | SWT.SEARCH);
//		searchText.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL | GridData.HORIZONTAL_ALIGN_FILL));
//	}
//
//	private void addKeyEventToSearchText() {
//		searchText.addKeyListener(new KeyAdapter() {
//			public void keyReleased(KeyEvent ke) {
//				filter.setSearchText(searchText.getText());
//				viewer.refresh();
//			}
//		});
//	}
//
//	private void createViewer(Composite parent) {
//		viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
//		createColumns(parent, viewer);
//		final Table table = viewer.getTable();
//		table.setHeaderVisible(true);
//		table.setLinesVisible(true);
//
//		viewer.setContentProvider(ArrayContentProvider.getInstance());
//		viewer.setInput(MyPersonModelProvider.INSTANCE.getPersons());
//		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
//			@Override
//			public void selectionChanged(SelectionChangedEvent event) {
//				IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();
//				Object firstElement = selection.getFirstElement();
//				System.out.println("Do something with it: " + firstElement);
//
//				// set the selection to the service
//				selectionService.setSelection(selection.size() == 1 ? //
//				firstElement : selection.toArray());
//			}
//		});
//
//		GridData gridData = new GridData();
//		gridData.verticalAlignment = GridData.FILL;
//		gridData.horizontalSpan = 2;
//		gridData.grabExcessHorizontalSpace = true;
//		gridData.grabExcessVerticalSpace = true;
//		gridData.horizontalAlignment = GridData.FILL;
//		viewer.getControl().setLayoutData(gridData);
//		filter = new PersonFilter();
//		viewer.addFilter(filter);
//		personSorter = new PersonSorter();
//		viewer.setComparator(personSorter);
//	}
//
//	private void createColumns(final Composite parent, final TableViewer viewer) {
//		String[] titles = { "First name", "Last name", "Phone", "Address" };
//		int[] bounds = { 100, 100, 100, 100 };
//
//		TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 0);
//		col.setLabelProvider(new ColumnLabelProvider() {
//			@Override
//			public String getText(Object element) {
//				return ((MyPerson) element).getFirstName();
//			}
//		});
//		// col.setEditingSupport(new FirstNameEditingSupport(viewer));
//
//		col = createTableViewerColumn(titles[1], bounds[1], 1);
//		col.setLabelProvider(new LastNameLabelProvider(searchText));
//		// col.setEditingSupport(new LastNameEditingSupport(viewer));
//		col.setLabelProvider(new ColumnLabelProvider() {
//			@Override
//			public String getText(Object element) {
//				return ((MyPerson) element).getLastName();
//			}
//		});
//
//		col = createTableViewerColumn(titles[2], bounds[2], 2);
//		col.setLabelProvider(new PhoneLabelProvider(searchText));
//		// col.setEditingSupport(new PhoneEditingSupport(viewer));
//		col.setLabelProvider(new ColumnLabelProvider() {
//			@Override
//			public String getText(Object element) {
//				return ((MyPerson) element).getPhn();
//			}
//		});
//
//		col = createTableViewerColumn(titles[3], bounds[3], 3);
//		col.setLabelProvider(new AddressLabelProvider(searchText));
//		// col.setEditingSupport(new AddressEditingSupport(viewer));
//		col.setLabelProvider(new ColumnLabelProvider() {
//			@Override
//			public String getText(Object element) {
//				return ((MyPerson) element).getAddress();
//			}
//		});
//
//	}
//
//	private TableViewerColumn createTableViewerColumn(String title, int bound, final int colNumber) {
//		final TableViewerColumn viewerColumn = new TableViewerColumn(viewer, SWT.NONE);
//		final TableColumn column = viewerColumn.getColumn();
//		column.setText(title);
//		column.setWidth(bound);
//		column.setResizable(true);
//		column.setMoveable(true);
//		column.addSelectionListener(getSelectionAdapter(column, colNumber));
//		return viewerColumn;
//	}
//
//	private SelectionAdapter getSelectionAdapter(final TableColumn column, final int index) {
//		SelectionAdapter selectionAdapter = new SelectionAdapter() {
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				personSorter.setColumn(index);
//				int dir = personSorter.getDirection();
//				viewer.getTable().setSortDirection(dir);
//				viewer.getTable().setSortColumn(column);
//				viewer.refresh();
//			}
//		};
//		return selectionAdapter;
//	}
//
//	public TableViewer getViewer() {
//		return viewer;
//	}
//
//	@PreDestroy
//	public void dispose() {
//	}
//
//	@Focus
//	public void setFocus() {
//	}
//
//	public void refresh() {
//		viewer.refresh();
//	}
//}
//
//package view;
//

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.jface.bindings.keys.ParseException;
import org.eclipse.jface.fieldassist.ContentProposalAdapter;
import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.jface.fieldassist.SimpleContentProposalProvider;
import org.eclipse.jface.fieldassist.TextContentAdapter;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

import model.MyPerson;
import model.MyPersonModelProvider;

public class SimpleTableView20180927Q2KrishnaTejaAyinala {
	private TableViewer viewer;

	public SimpleTableView20180927Q2KrishnaTejaAyinala() {
	}

	/**
	 * Create contents of the view part.
	 */
	@PostConstruct
	public void createControls(Composite parent) {
		GridLayout layout = new GridLayout(2, false);
		parent.setLayout(layout);
		createSearchTextControl(parent);
		createTableViewerControl(parent);
	}

	private void createSearchTextControl(Composite parent) {
		new Label(parent, SWT.NONE).setText("Search: ");
		final Text searchText = new Text(parent, SWT.BORDER | SWT.SEARCH);
		searchText.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL | GridData.HORIZONTAL_ALIGN_FILL));

		// create the decoration for the text component
		final ControlDecoration deco = new ControlDecoration(searchText, SWT.TOP | SWT.LEFT);
		Image image = FieldDecorationRegistry.getDefault().getFieldDecoration( //
				FieldDecorationRegistry.DEC_INFORMATION).getImage();
		deco.setDescriptionText("Enter the first character to see possible values");
		deco.setImage(image);
		deco.setShowOnlyOnFocus(false);

		char[] autoActivationCharacters = getAutoActivationCharacters();
		String[] proposalData = getContentProposalData();

		try {
			SimpleContentProposalProvider scProvider = new SimpleContentProposalProvider(proposalData);
			TextContentAdapter textContentAdapter = new TextContentAdapter();
			ContentProposalAdapter adapter = new ContentProposalAdapter(searchText, //
					textContentAdapter, //
					scProvider, //
					KeyStroke.getInstance("Ctrl+Space"), //
					autoActivationCharacters);
			adapter.setPropagateKeys(true);
			adapter.setProposalAcceptanceStyle(ContentProposalAdapter.PROPOSAL_REPLACE);

			searchText.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					int curPos = textContentAdapter.getCursorPosition(searchText);
					if (curPos == 0) {
						System.out.println("[DBG] Key: " + e.character);

						if (checkAutoActivationCharacters(autoActivationCharacters, e.character)) {
							String[] newProposalData = getContentProposalData(e.character);
							scProvider.setProposals(newProposalData);
							adapter.setContentProposalProvider(scProvider);
							// adapter.setContentProposalProvider(new
							// SimpleContentProposalProvider(newProposalData));
							adapter.setAutoActivationCharacters(autoActivationCharacters);
							deco.show();
						} else {
							scProvider.setProposals(proposalData);
							adapter.setContentProposalProvider(scProvider);
							adapter.setAutoActivationCharacters(autoActivationCharacters);
							deco.show();
						}
					} else {
						adapter.setAutoActivationCharacters(null);
						deco.hide(); // hide the decoration if the text
										// component has content
					}
					super.keyReleased(e);
				}
			});
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
	}

	boolean checkAutoActivationCharacters(char[] autoActivationCharacters, char pressedKey) {
		for (char iChar : autoActivationCharacters) {
			if (iChar == pressedKey) {
				return true;
			}
		}
		return false;
	}

	String[] getContentProposalData() {
		List<MyPerson> persons = MyPersonModelProvider.INSTANCE.getPersons();
		List<String> lastNameList = new ArrayList<String>();

		for (MyPerson p : persons) {
			String iFirstName = p.getLastName();
			lastNameList.add(iFirstName);
		}
		String[] proposalData = lastNameList.toArray(new String[lastNameList.size()]);
		return proposalData;
	}

	String[] getContentProposalData(char autoActivationChar) {
		List<MyPerson> persons = MyPersonModelProvider.INSTANCE.getPersons();
		List<String> lastNameList = new ArrayList<String>();

		for (MyPerson p : persons) {
			String iFirstName = p.getFirstName();
			String iLastName = p.getLastName();
			// filtered
			if (iLastName.startsWith(String.valueOf(autoActivationChar))) {
				lastNameList.add(iFirstName + " " + iLastName);
			}
		}

		// sorted
		Collections.sort(lastNameList);
		String[] proposalData = lastNameList.toArray(new String[lastNameList.size()]);
		return proposalData;
	}

	char[] getAutoActivationCharacters() {
		List<MyPerson> persons = MyPersonModelProvider.INSTANCE.getPersons();
		Set<Character> firstCharList = new HashSet<Character>();
		for (MyPerson p : persons) {
			firstCharList.add(p.getFirstName().charAt(0));
		}
		StringBuilder buf = new StringBuilder();
		for (Character c : firstCharList) {
			buf.append(c);
		}
		return buf.toString().toCharArray();
	}

	private void createTableViewerControl(Composite parent) {
		viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		createColumns(parent, viewer);
		final Table table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		viewer.setContentProvider(ArrayContentProvider.getInstance());
		// get the content for the viewer, setInput will call getElements in the
		// contentProvider
		viewer.setInput(MyPersonModelProvider.INSTANCE.getPersons());
		// make the selection available to other views
		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();
				Object firstElement = selection.getFirstElement();
				System.out.println("[DBG] The selected first element: " + firstElement);
			}
		});

		// define layout for the viewer
		GridData gridData = new GridData(GridData.GRAB_HORIZONTAL | GridData.HORIZONTAL_ALIGN_FILL | //
				GridData.GRAB_VERTICAL | GridData.VERTICAL_ALIGN_FILL);
		gridData.horizontalSpan = 2;
		viewer.getControl().setLayoutData(gridData);
	}

	private void createColumns(final Composite parent, final TableViewer viewer) {
		String[] titles = { "First name", "Last name", "Phone", "Address" };
		int[] bounds = { 100, 100, 100, 100 };

		TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 0);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return ((MyPerson) element).getFirstName();
			}
		});
		// col.setEditingSupport(new FirstNameEditingSupport(viewer));

		col = createTableViewerColumn(titles[1], bounds[1], 1);
		// col.setEditingSupport(new LastNameEditingSupport(viewer));
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return ((MyPerson) element).getLastName();
			}
		});

		col = createTableViewerColumn(titles[2], bounds[2], 2);
		// col.setEditingSupport(new PhoneEditingSupport(viewer));
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return ((MyPerson) element).getPhn();
			}
		});

		col = createTableViewerColumn(titles[3], bounds[3], 3);
		// col.setEditingSupport(new AddressEditingSupport(viewer));
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return ((MyPerson) element).getAddress();
			}
		});

	}

	private TableViewerColumn createTableViewerColumn(String title, int bound, final int colNumber) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(viewer, SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		column.setMoveable(true);
		return viewerColumn;
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
}
