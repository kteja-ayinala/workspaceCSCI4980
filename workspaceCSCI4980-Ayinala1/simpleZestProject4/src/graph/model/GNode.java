/*
 * @(#) MyNode.java
 *
 */
package graph.model;

import java.util.ArrayList;
import java.util.List;

public class GNode {
   private final String id;
   private final String name;
   private List<GNode>  connections;

   public GNode(String id, String name) {
      this.id = name;
      this.name = name;
      this.connections = new ArrayList<GNode>();
   }

   public String getId() {
      return id;
   }

   public String getName() {
      return name;
   }

   public List<GNode> getConnectedTo() {
      return connections;
   }
}
