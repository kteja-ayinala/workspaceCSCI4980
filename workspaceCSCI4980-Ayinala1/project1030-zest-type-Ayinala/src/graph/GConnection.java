/*
 * @(#) MyConnection.java
 *
 */
package graph;

public class GConnection {
   final String id;
   final String label;
   final GNode  source;
   final GNode  destination;

   public GConnection(String id, String label, GNode source, GNode destination) {
      this.id = id;
      this.label = label;
      this.source = source;
      this.destination = destination;
   }

   public String getLabel() {
      return label;
   }

   public GNode getSource() {
      return source;
   }

   public GNode getDestination() {
      return destination;
   }
}
