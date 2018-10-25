/*
 * @(#) Node.java
 *
 * Copyright 2015-2018 The Software Analysis Laboratory
 * Computer Science, The University of Nebraska at Omaha
 * 6001 Dodge Street, Omaha, NE 68182.
 */
package data;

import java.util.ArrayList;
import java.util.List;

public class DataNode {
   private String name;
   private List<DataNode> childNodes = new ArrayList<DataNode>();
   private boolean isEmptyBody;

   public DataNode(String n) {
      this.name = n;
      this.isEmptyBody = false;
   }

   public DataNode(String n, boolean isEmptyBody) {
      this.name = n;
      this.isEmptyBody = isEmptyBody;
   }

   public DataNode addChildNodes(List<DataNode> nodes) {
      childNodes.addAll(nodes);
      return this;
   }

   public List<DataNode> getChildNodes() {
      return this.childNodes;
   }

   public String getNodeName() {
      return this.name;
   }

   public void addChildNode(DataNode n) {
      this.childNodes.add(n);
   }

   public boolean isEmptyBody() {
      return isEmptyBody;
   }

   public void setEmptyBody(boolean isEmptyBody) {
      this.isEmptyBody = isEmptyBody;
   }
}
