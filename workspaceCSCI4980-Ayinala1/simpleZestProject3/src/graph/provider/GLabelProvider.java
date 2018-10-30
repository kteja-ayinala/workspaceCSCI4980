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
import org.eclipse.zest.core.widgets.ZestStyles;

import graph.GNode;
import graph.GNodeTypeA;
import graph.GNodeTypeB;
import graph.GNodeTypeC;
import graph.builder.GModelBuilder;

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
   public Color getBackgroundColour(Object entity) {
      return getNodeColor(entity);
   }

   private Color getNodeColor(Object o) {
      // TODO: Class Exercise
      // Your answer should modify the following conditional statements.
      // Also, it should add another conditional statement according to a node type.
      if (o instanceof GNodeTypeA) {
         return ColorConstants.lightGreen;
      }
      if (o instanceof GNodeTypeB) {
         return ColorConstants.lightBlue;
      }
      return ColorConstants.lightGray;
   }

   @Override
   public int getConnectionStyle(Object src, Object dest) {
      if (src instanceof GNodeTypeA && dest instanceof GNodeTypeA) {
         return ZestStyles.CONNECTIONS_DASH | ZestStyles.CONNECTIONS_DIRECTED;
      }
      return ZestStyles.CONNECTIONS_SOLID;
   }

   @Override
   public Color getNodeHighlightColor(Object entity) {
      return null;
   }

   @Override
   public Color getBorderColor(Object entity) {
      return null;
   }

   @Override
   public Color getBorderHighlightColor(Object entity) {
      return null;
   }

   @Override
   public int getBorderWidth(Object entity) {
      return 0;
   }

   @Override
   public Color getColor(Object src, Object dest) {
      return null;
   }

   @Override
   public Color getHighlightColor(Object src, Object dest) {
      return null;
   }

   @Override
   public int getLineWidth(Object src, Object dest) {
      return 0;
   }

   @Override
   public Color getForegroundColour(Object entity) {
      return ColorConstants.yellow;
      // return null;
   }

   @Override
   public IFigure getTooltip(Object entity) {
      return null;
   }

   @Override
   public boolean fisheyeNode(Object entity) {
      return false;
   }
}
