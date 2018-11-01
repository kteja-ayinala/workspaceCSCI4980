/*
 * @(#) ZestLabelProvider.java
 *
 */
package graph.provider;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.IFigure;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.zest.core.viewers.EntityConnectionData;
import org.eclipse.zest.core.viewers.IEntityConnectionStyleProvider;
import org.eclipse.zest.core.viewers.IEntityStyleProvider;

import graph.builder.GModelBuilder;
import graph.model.GNode;
import graph.model.node.GClassNode;
import graph.model.node.GPackageNode;

public class GLabelProvider extends LabelProvider implements IEntityStyleProvider, IEntityConnectionStyleProvider {
   @Override
   public String getText(Object element) {
      // Create a label for node.
      if (element instanceof GNode) {
         GNode myNode = (GNode) element;
         return myNode.getName();
      }
      // Create a label for connection.
      if (element instanceof EntityConnectionData) {
         EntityConnectionData eCon = (EntityConnectionData) element;
         if (eCon.source instanceof GNode) {
            return GModelBuilder.instance().getConnectionLabel( //
                  ((GNode) eCon.source).getId(), //
                  ((GNode) eCon.dest).getId());
         }
      }
      return "";
   }

   @Override
   public Color getBackgroundColour(Object o) {
      return getNodeColor(o);
   }

   private Color getNodeColor(Object o) {
      if (o instanceof GPackageNode) {
         return ColorConstants.lightGreen;
      }
      if (o instanceof GClassNode) {
         return ColorConstants.lightBlue;
      }
      return ColorConstants.yellow;
   }

   @Override
   public Color getForegroundColour(Object arg0) {
      return ColorConstants.black;
   }

   @Override
   public boolean fisheyeNode(Object arg0) {
      return false;
   }

   @Override
   public Color getNodeHighlightColor(Object o) {
      return null;
   }

   @Override
   public Color getBorderHighlightColor(Object arg0) {
      return null;
   }

   @Override
   public Color getBorderColor(Object arg0) {
      return null;
   }

   @Override
   public int getBorderWidth(Object arg0) {
      return 0;
   }

   @Override
   public IFigure getTooltip(Object arg0) {
      return null;
   }

   @Override
   public int getConnectionStyle(Object src, Object dest) {
      return 0;
   }

   @Override
   public Color getColor(Object src, Object dest) {
      return ColorConstants.black;
   }

   @Override
   public Color getHighlightColor(Object src, Object dest) {
      return null;
   }

   @Override
   public int getLineWidth(Object src, Object dest) {
      return 0;
   }
}
