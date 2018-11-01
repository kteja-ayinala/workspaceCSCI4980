/*
 * @(#) View.java
 *
 */
package view;

import javax.annotation.PostConstruct;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.services.EMenuService;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.zest.core.viewers.GraphViewer;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.RadialLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.TreeLayoutAlgorithm;

import graph.builder.GModelBuilder;
import graph.provider.GLabelProvider;
import graph.provider.GNodeContentProvider;

public class SimpleZestGraphView {
   public static final String SIMPLEZESTVIEW = "simplezestproject4.partdescriptor.simplezestview4";
   public static final String POPUPMENU_ID = "simplezestproject4.popupmenu.mypopupmenu";
   private GraphViewer gViewer;
   private int layout = 0;

   @PostConstruct
   public void createControls(Composite parent, EMenuService menuService) {
      gViewer = new GraphViewer(parent, SWT.BORDER);
      gViewer.setContentProvider(new GNodeContentProvider());
      gViewer.setLabelProvider(new GLabelProvider());
      gViewer.setLayoutAlgorithm(new TreeLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING), true);
      gViewer.applyLayout();
      menuService.registerContextMenu(gViewer.getControl(), POPUPMENU_ID);
   }

   public void update() {
      gViewer.setInput(GModelBuilder.instance().getNodes());
      if (layout % 2 == 0)
         gViewer.setLayoutAlgorithm(new TreeLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING), true);
      else
         gViewer.setLayoutAlgorithm(new RadialLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING), true);
      layout++;
   }

   @Focus
   public void setFocus() {
      this.gViewer.getGraphControl().setFocus();
   }
}
