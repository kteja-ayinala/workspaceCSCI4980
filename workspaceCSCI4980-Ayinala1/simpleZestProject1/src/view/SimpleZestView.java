package view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Item;
import org.eclipse.zest.core.widgets.Graph;

import org.eclipse.swt.SWT;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.zest.core.widgets.GraphConnection;
import org.eclipse.zest.core.widgets.GraphNode;
import org.eclipse.zest.core.widgets.ZestStyles;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.SpringLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.TreeLayoutAlgorithm;

import util.UtilNode;

public class SimpleZestView {
   private Graph graph;
   private int layout = 1;
   @Inject
   EPartService service;

   GraphNode node1 = null, node2 = null, node3 = null, node4 = null;
   Map<String, GraphNode> mapGraphNode = new HashMap<String, GraphNode>();

   public SimpleZestView() {
   }

   /**
    * Create contents of the view part.
    */
   @PostConstruct
   public void createControls(Composite parent) {
      graph = new Graph(parent, SWT.NONE); // Graph will hold all other objects
      createGraphNode();
      createConnection(parent);
      graph.setLayoutAlgorithm(new SpringLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING), true);
      addSelectionListener();
   }

   void createGraphNode() {
      node1 = new GraphNode(graph, SWT.NONE, "Node1");
      node2 = new GraphNode(graph, SWT.NONE, "Node2");
      node3 = new GraphNode(graph, SWT.NONE, "Node3");
      node4 = new GraphNode(graph, SWT.NONE, "Node4");
      
      /*
      List<String> listNodeNames = UtilNode.readNodeNames(filePath);
      for (String iName : listNodeNames) {
         // TODO: Class Exercise
         this.mapGraphNode.put(iName, * Your Answer * );
      }
      */
   }

   void createConnection(Composite parent) {
      new GraphConnection(graph, ZestStyles.CONNECTIONS_DIRECTED, node1, node2);
      new GraphConnection(graph, ZestStyles.CONNECTIONS_DOT, node2, node3);
      new GraphConnection(graph, SWT.NONE, node3, node1);
      // Change line color and line width
      GraphConnection graphConnection = new GraphConnection(graph, SWT.NONE, node1, node4);
      graphConnection.changeLineColor(parent.getDisplay().getSystemColor(SWT.COLOR_GREEN));
      // Also set a text
      graphConnection.setText("This is a text");
      graphConnection.setHighlightColor(parent.getDisplay().getSystemColor(SWT.COLOR_RED));
      graphConnection.setLineWidth(3);
   
      /*
      List<String[]> readConnectionInfo = UtilNode.readConnectionInfo(filePath);
      for (String[] iConn : readConnectionInfo) {
         // TODO: Class Exercise
         // new GraphConnection(graph, SWT.NONE, * Your Answer *, * Your Answer *);
      }
      */
   }

   private void addSelectionListener() {
      // Selection listener on graphConnect
      SelectionAdapter selectionAdapter = new SelectionAdapter() {
         @Override
         public void widgetSelected(SelectionEvent e) {
            List<?> list = ((Graph) e.widget).getSelection();
            for (Object o : list) {
               if (o instanceof GraphNode) {
                  GraphNode iNode = (GraphNode) o;
                  findUpdateSimpleView(iNode);
               }
            }
         }
      };
      graph.addSelectionListener(selectionAdapter);
   }

   void findUpdateSimpleView(Item iNode) {
      // Find a view.
      MPart findPart = service.findPart(SimpleView.ID);
      SimpleView simpleView = (SimpleView) findPart.getObject();
      simpleView.appendText(iNode.getText() + "\n");
   }

   @PreDestroy
   public void dispose() {
   }

   public void setLayoutManager() {
      switch (layout) {
      case 1:
         graph.setLayoutAlgorithm(new TreeLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING), true);
         layout++;
         break;
      case 2:
         graph.setLayoutAlgorithm(new SpringLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING), true);
         layout = 1;
         break;
      }
   }

   @Focus
   public void setFocus() {
      this.graph.setFocus();
   }
}
