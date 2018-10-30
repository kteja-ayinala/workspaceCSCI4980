/*
 * @(#) View.java
 *
 */
package view;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.services.EMenuService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.zest.core.viewers.GraphViewer;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.SpringLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.TreeLayoutAlgorithm;

import graph.GNode;
import graph.builder.GModelBuilder;
import graph.provider.GLabelProvider;
import graph.provider.GNodeContentProvider;

public class SimpleZestView {
   private GraphViewer gViewer;
   private int layout = 1;
   public static final String SIMPLEZESTVIEW_ID = "simplezestproject3.partdescriptor.simplezestview3";
   public static final String SIMPLEVIEW_ID = "simplezestproject3.partdescriptor.simpleview";
   public static final String POPUPMENU_ID = "simplezestproject3.popupmenu.mypopup";

   @Inject
   EPartService service;

   @PostConstruct
   public void createControls(Composite parent, EMenuService menuService) {
      gViewer = new GraphViewer(parent, SWT.BORDER);
      gViewer.setContentProvider(new GNodeContentProvider());
      gViewer.setLabelProvider(new GLabelProvider());
      gViewer.setInput(GModelBuilder.instance().build().getNodes());
      gViewer.setLayoutAlgorithm(new TreeLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING), true);
      gViewer.applyLayout();

      gViewer.addSelectionChangedListener(new ISelectionChangedListener() {
         @Override
         public void selectionChanged(SelectionChangedEvent e) {
            IStructuredSelection structuredSelection = (IStructuredSelection) e.getSelection();
            Object o = structuredSelection.getFirstElement();
            if (o instanceof GNode) {
               GNode gNode = (GNode) o;
               SimpleView simpleView = (SimpleView) service.findPart(SIMPLEVIEW_ID).getObject();
               simpleView.appendText(gNode.getId() + ": " + gNode.getName() + "\n");
            }
         }
      });
      menuService.registerContextMenu(gViewer.getGraphControl(), POPUPMENU_ID);
   }

   public void setLayoutManager() {
      switch (layout) {
      case 1:
         gViewer.setLayoutAlgorithm(new SpringLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING), true);
         layout++;
         break;
      case 2:
         gViewer.setLayoutAlgorithm(new TreeLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING), true);
         layout = 1;
         break;
      }
   }

   @PreDestroy
   public void dispose() {
   }

   @Focus
   public void setFocus() {
      this.gViewer.getGraphControl().setFocus();
   }

   public GraphViewer getGraphViewer() {
      return this.gViewer;
   }

   public void update(List<GNode> listGNode) {
      gViewer.setInput(listGNode);
      gViewer.setLayoutAlgorithm(new TreeLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING), true);
   }
}
