/*
 * @(#) NodeModelContentProvider.java
 *
 */
package graph.builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import graph.GConnection;
import graph.GNode;
import graph.GNodeTypeA;
import graph.GNodeTypeB;
import graph.GNodeTypeC;
import util.UtilNode;

public class GModelBuilder {
   private static final String filePath = "input-graph-info.txt";
   private static List<GConnection> connections = new ArrayList<GConnection>();
   private static List<GNode> nodes = new ArrayList<GNode>();
   private static Map<String, GNode> nodesMap = new HashMap<String, GNode>();

   static GModelBuilder singleton = null;

   public GModelBuilder() {
   }

   public static GModelBuilder instance() {
      if (singleton == null) {
         singleton = new GModelBuilder();
      }
      return singleton;
   }

   public GModelBuilder build() {
      connections.clear();
      nodes.clear();
      // Create nodes
      nodes.add(new GNodeTypeA("n1", "Node Type-A 1"));
      nodes.add(new GNodeTypeB("n2", "Node Type-B 2"));
      nodes.add(new GNode("n3", "Node 3"));
      nodes.add(new GNode("n4", "Node 4"));
      nodes.add(new GNodeTypeA("n5", "Node Type-A 5"));
      nodes.add(new GNode("n6", "Node 6"));
      // Create connections
      connections.add(new GConnection("e1", "Edge 1", nodes.get(0), nodes.get(1)));
      connections.add(new GConnection("e2", "Edge 2", nodes.get(0), nodes.get(4)));
      connections.add(new GConnection("e3", "Edge 3", nodes.get(2), nodes.get(1)));
      connections.add(new GConnection("e4", "Edge 4", nodes.get(1), nodes.get(3)));
      connections.add(new GConnection("e5", "Edge 5", nodes.get(2), nodes.get(5)));
      // Save the info about the connections in the nodes
      for (GConnection connection : connections) {
         connection.getSource().getConnectedTo().add(connection.getDestination());
      }
      return singleton;
   }

   public GModelBuilder buildByGraphInfoFile() {
      connections.clear();
      nodes.clear();

      // Create GNode objects
      List<String> nodeNames = UtilNode.readNodeNames(filePath);
      for (String iNodeName : nodeNames) {
         GNode gNode = null;
         if (getNodeType(iNodeName).equals("A")) {
            // TODO: Class Exercise
            // gNode = *Your Answer*
         } else if (getNodeType(iNodeName).equals("B")) {
            // TODO: Class Exercise
            // gNode = *Your Answer*
         } else if (getNodeType(iNodeName).equals("C")) {
            // TODO: Class Exercise
            // gNode = *Your Answer*
         } else {
            gNode = new GNode(getNodeId(iNodeName), iNodeName);
         }
         nodes.add(gNode);
         nodesMap.put(getNodeId(iNodeName), gNode);
      }

      // Create GConnection objects
      List<String[]> connectionInfo = UtilNode.readConnectionInfo(filePath);
      for (String[] iConn : connectionInfo) {
         String id = getNodeId(iConn[0]) + "." + getNodeId(iConn[1]);
         String label = getNodeId(iConn[0]) + "-" + getNodeId(iConn[1]);
         GNode nodeSrc = nodesMap.get(getNodeId(iConn[0]));
         GNode nodeDst = nodesMap.get(getNodeId(iConn[1]));
         //TODO: Class Exercise
         // *Your Answer*.getConnectedTo().add(*Your Answer*);
         connections.add(new GConnection(id, label, nodeSrc, nodeDst));
      }
      return singleton;
   }

   String getNodeId(String n) {
      return n.split("-")[0].trim().substring(1);
   }

   String getNodeType(String n) {
      if (!n.contains("-"))
         return "";
      return n.split("-")[1].trim();
   }

   public List<GNode> getNodes() {
      return nodes;
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
}
