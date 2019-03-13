
package view;

import java.util.EventObject;
import java.util.List;

import javax.annotation.PostConstruct;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.zest.core.viewers.GraphViewer;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.GraphNode;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.RadialLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.TreeLayoutAlgorithm;

import analysis.ProjectAnalyzer;
import graph.model.GClassNode;
import graph.model.GMethodNode;
import graph.model.GNode;
import graph.model.GNodeType;
import graph.model.GPackageNode;
import graph.provider.GLabelProvider;
import graph.provider.GModelProvider;
import graph.provider.GNodeContentProvider;
import handler.MethodRenameHandler;
import util.UtilMsg;
import util.UtilNode;

//import analysis.MoveMethodAnalyzer;
//import analysis.ProjectAnalyzer;
//import graph.model.GClassNode;
//import graph.model.GMethodNode;
//import graph.model.GNode;
//import graph.model.GNodeType;
//import graph.model.GPackageNode;
//import graph.provider.GLabelProvider;
//import graph.provider.GModelProvider;
//import graph.provider.GNodeContentProvider;
//import util.UtilMsg;
//import util.UtilNode;

public class AyinalaZestView {
	public static final String VIEW_ID = "project1211-final-ayinala.partdescriptor.ayinalazestview";

	private GraphViewer gViewer;
	private int layout = 0;
	private Menu mPopupMenu = null;
	private MenuItem menuItemRenameMethod = null, menuItemRefresh = null;
	private GraphNode selectedSrcGraphNode = null, selectedDstGraphNode = null;
	private GraphNode prevSelectedDstGraphNode = null;

	private StyledText styledText;

	private GNode selectedGMethodNode = null, selectedGClassNode = null, selectedGPackageNode = null;
	private GNode prevSelectedGMethodNode = null, prevSelectedGPackageNode = null;

	@PostConstruct
	public void createControls(Composite parent) {
		gViewer = new GraphViewer(parent, SWT.BORDER);
		gViewer.setContentProvider(new GNodeContentProvider());
		gViewer.setLabelProvider(new GLabelProvider());
		gViewer.setLayoutAlgorithm(new TreeLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING), true);
		gViewer.applyLayout();
		Composite composite = new Composite(parent, SWT.BORDER);
		styledText = new StyledText(composite, SWT.BORDER);
		addPoupMenu();
		addMouseListenerGraphViewer();
	}

	private void addPoupMenu() {
		mPopupMenu = new Menu(gViewer.getControl());
		gViewer.getControl().setMenu(mPopupMenu);

		menuItemRenameMethod = new MenuItem(mPopupMenu, SWT.CASCADE);
		menuItemRenameMethod.setText("Ayinala Popup Menu Item");
		addSelectionListenerMenuItemRenameMethod();

		menuItemRefresh = new MenuItem(mPopupMenu, SWT.CASCADE);
		menuItemRefresh.setText("Refresh");
		addSelectionListenerMenuItemRefresh();
	}

	private void addMouseListenerGraphViewer() {
		MouseAdapter mouseAdapter = new MouseAdapter() {
			public void mouseDown1(MouseEvent e) {
				menuItemRenameMethod.setEnabled(false);
				// resetSelectedSrcGraphNode();

				if (UtilNode.isMethodNode(e)) {
					System.out.println("single clicked");
					menuItemRenameMethod.setEnabled(true);

					selectedSrcGraphNode = (GraphNode) ((Graph) e.getSource()).getSelection().get(0);
					selectedSrcGraphNode.setBorderWidth(1);

					selectedGMethodNode = (GMethodNode) selectedSrcGraphNode.getData();
					selectedGMethodNode.setNodeType(GNodeType.UserSelection);

					UtilMsg.openQuestion("Enter new name to method:");
				}

			}

			@Override
			public void mouseDown(MouseEvent e) {
				if (UtilNode.isMethodNode(e)) {
					System.out.println("single clicked");
					prevSelectedDstGraphNode = selectedDstGraphNode;
					selectedDstGraphNode = (GraphNode) ((Graph) e.getSource()).getSelection().get(0);

					prevSelectedGMethodNode = selectedGClassNode;
					selectedGClassNode = (GMethodNode) selectedDstGraphNode.getData();

					if (selectedGClassNode.eq(prevSelectedGMethodNode)) {
						// same node => marked => unmarked
						if (selectedGClassNode.getNodeType().equals(GNodeType.UserSelection)) {
							UtilNode.resetDstNode(selectedDstGraphNode, selectedGClassNode);
						}
						// same node => unmarked => marked
						else if (selectedGClassNode.getNodeType().equals(GNodeType.InValid)) {
							changeColorDDClikedNode(selectedGClassNode);
						}
					} else {
						// different node => marked && unmarked previous marked
						// node
						changeColorDDClikedNode(selectedGClassNode);
						UtilNode.resetDstNode(prevSelectedDstGraphNode, prevSelectedGMethodNode);
					}

				} else if (UtilNode.isPackageNode(e)) {

					prevSelectedDstGraphNode = selectedDstGraphNode;
					selectedDstGraphNode = (GraphNode) ((Graph) e.getSource()).getSelection().get(0);

					prevSelectedGPackageNode = selectedGPackageNode;
					selectedGPackageNode = (GPackageNode) selectedDstGraphNode.getData();

					if (selectedGPackageNode.eq(prevSelectedGPackageNode)) {
						if (selectedGPackageNode.getNodeType().equals(GNodeType.UserSelection)) {
							UtilNode.resetPackageNode(selectedDstGraphNode, prevSelectedGPackageNode);
						} else if (selectedGPackageNode.getNodeType().equals(GNodeType.InValid)) {
							changeColorDDClikedNode(selectedGPackageNode);
						}
					} else {
						changeColorDDClikedNode(selectedGPackageNode);
						UtilNode.resetPackageNode(prevSelectedDstGraphNode, selectedGPackageNode);
					}

				}
			}
		};
		gViewer.getControl().addMouseListener(mouseAdapter);
	}

	private void changeColorDDClikedNode(GNode node) {
		if (this.selectedDstGraphNode == null)
			return;
		selectedDstGraphNode.setForegroundColor(ColorConstants.red);
		selectedDstGraphNode.setBackgroundColor(ColorConstants.blue);
		selectedDstGraphNode.setBorderColor(ColorConstants.blue);
		selectedDstGraphNode.setHighlightColor(ColorConstants.blue);
		selectedDstGraphNode.setBorderHighlightColor(ColorConstants.black);
		node.setNodeType(GNodeType.UserSelection);
	}

	private void addSelectionListenerMenuItemRenameMethod() {

		MouseAdapter mouseAdapter = new MouseAdapter() {
			public void mouseDown(MouseEvent e) {
				menuItemRenameMethod.setEnabled(false);
				// resetSelectedSrcGraphNode();

				if (UtilNode.isMethodNode(e)) {
					System.out.println("single clicked");
					menuItemRenameMethod.setEnabled(true);

					selectedSrcGraphNode = (GraphNode) ((Graph) e.getSource()).getSelection().get(0);
					selectedSrcGraphNode.setBorderWidth(1);

					selectedGMethodNode = (GMethodNode) selectedSrcGraphNode.getData();
					selectedGMethodNode.setNodeType(GNodeType.UserSelection);

					UtilMsg.openQuestion("Enter new name to method:");
				}
			}
		};

	}

	private void addSelectionListenerMenuItemRefresh() {
		SelectionListener menuItemListenerRefresh = new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("[DBG] MenuItem Refresh");
				syncZestViewAndJavaEditor();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		};
		menuItemRefresh.addSelectionListener(menuItemListenerRefresh);
	}

	// private void resetSelectedSrcGraphNode() {
	// if (selectedSrcGraphNode != null && selectedSrcGraphNode.isDisposed() ==
	// false) {
	// selectedSrcGraphNode.setBorderWidth(0);
	// selectedGMethodNode.setNodeType(GNodeType.InValid);
	// }
	// }

	public void syncZestViewAndJavaEditor() {
		ProjectAnalyzer analyzer = new ProjectAnalyzer();
		analyzer.analyze();
		gViewer.setInput(GModelProvider.instance().getNodes());
	}

	public void update() {
		gViewer.setInput(GModelProvider.instance().getNodes());
		if (layout % 2 == 0)
			gViewer.setLayoutAlgorithm(new TreeLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING), true);
		else
			gViewer.setLayoutAlgorithm(new RadialLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING), true);
		layout++;
	}

	private void resetSelectedSrcGraphNode() {
		if (selectedSrcGraphNode != null && selectedSrcGraphNode.isDisposed() == false) {
			selectedSrcGraphNode.setBorderWidth(0);
			selectedGMethodNode.setNodeType(GNodeType.InValid);
		}
	}

	@Focus
	public void setFocus() {
		this.gViewer.getGraphControl().setFocus();
	}

	public void appendText(String s) {
		this.styledText.append(s);
	}

	public void reset() {
		this.styledText.setText("");
	}
}
