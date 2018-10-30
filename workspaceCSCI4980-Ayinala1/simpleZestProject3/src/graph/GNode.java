/*
 * @(#) MyNode.java
 *
 */
package graph;

import java.util.ArrayList;
import java.util.List;

public class GNode {
   private final String id;
   private final String name;
   private List<GNode>  connectedNodes;

   public GNode(String id, String name) {
      this.id = id;
      this.name = name;
      this.connectedNodes = new ArrayList<GNode>();
   }

   public String getId() {
      return id;
   }

   public String getName() {
      return name;
   }

   public List<GNode> getConnectedTo() {
      return connectedNodes;
   }
   
   public String toString() {
      return "ID: " + id + ", Name: " + name;
   }
}
