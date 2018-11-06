package util;

import java.util.EventObject;
import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.GraphNode;

import graph.model.GClassNode;
import graph.model.GMethodNode;
import graph.model.GNode;
import graph.model.GNodeType;
import graph.model.GPackageNode;

public class UtilNode {
   public static void resetDstNode(GraphNode graphNode, GNode node) {
      if (graphNode == null || graphNode.isDisposed() || !(node instanceof GClassNode))
         return;
      graphNode.setForegroundColor(ColorConstants.black);
      graphNode.setBackgroundColor(ColorConstants.lightBlue);
      graphNode.setBorderColor(ColorConstants.lightBlue);
      graphNode.setHighlightColor(ColorConstants.lightBlue);
      graphNode.setBorderHighlightColor(ColorConstants.lightBlue);
      node.setNodeType(GNodeType.InValid);
   }

   public static void resetPackageNode(GraphNode graphNode, GNode node) {
      if (graphNode == null || graphNode.isDisposed() || !(node instanceof GPackageNode)) {
         return;
      }
      // TODO: Class Exercise
      // graphNode.setForegroundColor(ColorConstants.black);
      // graphNode.setBackgroundColor(* Your Answer *);
      // graphNode.setBorderColor(* Your Answer *);
      // graphNode.setHighlightColor(* Your Answer *);
      // graphNode.setBorderHighlightColor(* Your Answer *);
      // node.setNodeType(* Your Answer *);
   }

   public static boolean isPackageNode(EventObject e) {
      Object obj = e.getSource();
      Graph graph = (Graph) obj;

      boolean result = false;
      // TODO: Class Exercise
      // result = (!graph.getSelection().isEmpty() && //
      //   graph.getSelection().get(0) instanceof GraphNode && //
      //   ((GraphNode) graph.getSelection().get(0)).getData() instanceof * Your Answer *);
      return result;
   }

   public static boolean isClassNode(EventObject e) {
      Object obj = e.getSource();
      Graph graph = (Graph) obj;

      return (!graph.getSelection().isEmpty() && //
            graph.getSelection().get(0) instanceof GraphNode && //
            ((GraphNode) graph.getSelection().get(0)).getData() instanceof GClassNode);
   }

   public static boolean isMethodNode(EventObject e) {
      Object obj = e.getSource();
      Graph instGraph = (Graph) obj;
      List<?> selection = instGraph.getSelection();
      return ((!selection.isEmpty() && selection.get(0) instanceof GraphNode) && //
            (((GraphNode) selection.get(0)).getData() instanceof GMethodNode));
   }
}
