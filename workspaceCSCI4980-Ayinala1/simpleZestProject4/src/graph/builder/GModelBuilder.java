/*
 * @(#) NodeModelContentProvider.java
 *
 */
package graph.builder;

import java.util.ArrayList;
import java.util.List;

import graph.model.GConnection;
import graph.model.GNode;

public class GModelBuilder {
   private static List<GConnection> connections = new ArrayList<GConnection>();
   private static List<GNode> nodes = new ArrayList<GNode>();
   static GModelBuilder singleton = null;

   public GModelBuilder() {
   }

   public static GModelBuilder instance() {
      if (singleton == null) {
         singleton = new GModelBuilder();
      }
      return singleton;
   }

   public List<GNode> getNodes() {
      return nodes;
   }

   public List<GConnection> getConnections() {
      return connections;
   }

   public String getConnectionLabel(String srcId, String dstId) {
      for (GConnection iCon : connections) {
         if (iCon.getSource().getId().equals(srcId) && //
               iCon.getDestination().getId().equals(dstId)) {
            return iCon.getLabel();
         }
      }
      return "";
   }

   public void reset() {
      nodes.clear();
      connections.clear();
   }
}
