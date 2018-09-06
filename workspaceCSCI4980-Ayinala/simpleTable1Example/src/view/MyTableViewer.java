package view;

import java.net.URL;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import model.Person;
import model.PersonModelProvider;

public class MyTableViewer {
   private TableViewer        viewer;
   private static final Image CHECKED   = getImage("checked.gif");
   private static final Image UNCHECKED = getImage("unchecked.gif");

   public MyTableViewer() {
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
   }

   private void createTableViewerControl(Composite parent) {
      viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
      createColumns(parent, viewer);
      final Table table = viewer.getTable();
      table.setHeaderVisible(true);
      table.setLinesVisible(true);

      viewer.setContentProvider(ArrayContentProvider.getInstance());
      // get the content for the viewer, setInput will call getElements in the contentProvider
      viewer.setInput(PersonModelProvider.INSTANCE.getPersons());
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
      String[] titles = { "First name", "Last name", "Gender", "Married" };
      int[] bounds = { 100, 100, 100, 100 };

      TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 0);
      col.setLabelProvider(new ColumnLabelProvider() {
         @Override
         public String getText(Object element) {
            return ((Person) element).getFirstName();
         }
      });
      col = createTableViewerColumn(titles[1], bounds[1], 1);
      col.setLabelProvider(new ColumnLabelProvider() {
         @Override
         public String getText(Object element) {
            return ((Person) element).getLastName();
         }
      });

      col = createTableViewerColumn(titles[2], bounds[2], 2);
      col.setLabelProvider(new ColumnLabelProvider() {
         @Override
         public String getText(Object element) {
            return ((Person) element).getGender();
         }
      });

      col = createTableViewerColumn(titles[3], bounds[3], 3);
      col.setLabelProvider(new ColumnLabelProvider() {
         @Override
         public String getText(Object element) {
            return null;
         }

         @Override
         public Image getImage(Object element) {
            if (((Person) element).isMarried()) {
               return CHECKED;
            } else {
               return UNCHECKED;
            }
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

   private static Image getImage(String file) {
      Bundle bundle = FrameworkUtil.getBundle(MyTableViewer.class);
      URL url = FileLocator.find(bundle, new Path("icons/" + file), null);
      ImageDescriptor image = ImageDescriptor.createFromURL(url);
      return image.createImage();
   }

   @PreDestroy
   public void dispose() {
   }

   @Focus
   public void setFocus() {
   }
}
