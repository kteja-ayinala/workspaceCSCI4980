package view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.services.EMenuService;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.GraphConnection;
import org.eclipse.zest.core.widgets.GraphNode;
import org.eclipse.zest.core.widgets.ZestStyles;
import org.eclipse.zest.layouts.LayoutStyles;
//import org.eclipse.zest.layouts.algorithms.SpringLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.TreeLayoutAlgorithm;

import data.DataNode;
import util.UtilMsg;

public class SimpleZestViewAST1025Ayinala {
	public final static String VIEW_ID = "project1025-zest-ast-ayinala.partdescriptor.simplezestview-ast-1025ayinala";
	public final static String POPUPMENU_ID = "project1025-zest-ast-ayinala.popupmenu.mypopupmenu";

	private Graph graph;
	private Map<String, GraphNode> graphNodeMap = new HashMap<String, GraphNode>();

	@PostConstruct
	public void createControls(Composite parent, EMenuService menuService) {
		this.graph = new Graph(parent, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		graph.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println(e);
			}
		});
		menuService.registerContextMenu(this.graph, POPUPMENU_ID);
	}

	public void update(List<DataNode> nodeList) {
		clear();
		for (DataNode iNode : nodeList) {
			GraphNode iGraphNode = createGraphNode(iNode); // create a new one
															// or reuse if it
															// exists already.
			List<DataNode> childNodeList = iNode.getChildNodes();

			for (DataNode jAdjNode : childNodeList) {
				GraphNode jGraphNode = createGraphNode(jAdjNode);
				createConnection(iGraphNode, jGraphNode);
			}
		}
		graph.setLayoutAlgorithm(new TreeLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING), true);
	}

	private GraphNode createGraphNode(DataNode dataNode) {
		GraphNode graphNode = graphNodeMap.get(dataNode.getNodeName());
		if (graphNode == null) {
			graphNode = new GraphNode(this.graph, SWT.NONE, dataNode.getNodeName());
			graphNodeMap.put(dataNode.getNodeName(), graphNode);
		}
		return graphNode;
	}

	public void updateByContextMenu(List<DataNode> nodeList) {
		clear();
		for (DataNode iNode : nodeList) {
			GraphNode iGraphNode = createGraphNode(iNode); // create a new one
															// or reuse if it
															// exists already.
			List<DataNode> childNodeList = iNode.getChildNodes();

			for (DataNode jChildNode : childNodeList) {
				GraphNode jChildGraphNode = createGraphNode(jChildNode);
				createConnection(iGraphNode, jChildGraphNode);
				checkEmptyNode(jChildNode, jChildGraphNode);
				// checkEmptyNode(/* Your Answer */, /* Your Answer */);
			}
		}
		graph.setLayoutAlgorithm(new TreeLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING), true);
	}

	private void checkEmptyNode(DataNode dataNode, GraphNode graphNode) {

		if (dataNode.isEmptyBody()) {
			graphNode.setBackgroundColor(ColorConstants.red);
			graphNode.setForegroundColor(ColorConstants.yellow);
		}
	}

	private void clear() {
		for (Entry<String, GraphNode> entry : graphNodeMap.entrySet()) {
			GraphNode iNode = entry.getValue();
			iNode.dispose();
		}
		graphNodeMap.clear();
	}

	private void createConnection(GraphNode srcGNode, GraphNode dstGNode) {
		new GraphConnection(graph, ZestStyles.CONNECTIONS_SOLID, srcGNode, dstGNode);
	}

	@PreDestroy
	public void dispose() {
	}

	@Focus
	public void setFocus() {
		this.graph.setFocus();
	}
}