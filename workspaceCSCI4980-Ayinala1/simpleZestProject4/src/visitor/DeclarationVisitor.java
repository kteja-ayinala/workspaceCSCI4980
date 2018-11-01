/*
 * @(#) MethodVisitor.java
 *
 * Copyright 2015-2018 The Software Analysis Laboratory
 * Computer Science, The University of Nebraska at Omaha
 * 6001 Dodge Street, Omaha, NE 68182.
 */
package visitor;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import graph.builder.GModelBuilder;
import graph.model.GConnection;
import graph.model.GNode;
import graph.model.node.GClassNode;
import graph.model.node.GMethodNode;
import util.UtilNode;

public class DeclarationVisitor extends ASTVisitor {
   GNode pkgGNode;
   Map<String, GNode> nodeMap = new HashMap<String, GNode>();

   public DeclarationVisitor(GNode pkgNode) {
      this.pkgGNode = pkgNode;
   }

   /**
    * A type declaration is the union of a class declaration and an interface declaration.
    */
   @Override
   public boolean visit(TypeDeclaration tNode) {
      addConnection(this.pkgGNode, tNode);
      return true;
   }

   @Override
   public boolean visit(MethodDeclaration mNode) {
      ASTNode astNode = UtilNode.getOuterClass(mNode);

      if (astNode instanceof TypeDeclaration) {
         TypeDeclaration tNode = (TypeDeclaration) astNode;
         GNode typeGNode = nodeMap.get(UtilNode.getName(tNode) + ":" + tNode.getStartPosition());
         if (typeGNode != null) {
            addConnection(typeGNode, mNode);
         }
      }
      return true;
   }

   void addConnection(GNode srcGNode, ASTNode astNode) {
      // Add a node
      GNode dstGNode = createGNode(astNode, UtilNode.getName(astNode));
      GModelBuilder.instance().getNodes().add(dstGNode);
      // Add a connection
      String conId = srcGNode.getId() + dstGNode.getId();
      String conLabel = "offset: " + astNode.getStartPosition();

      GConnection con = new GConnection(conId, conLabel, srcGNode, dstGNode);
      GModelBuilder.instance().getConnections().add(con);
      srcGNode.getConnectedTo().add(dstGNode);
      // Update map
      nodeMap.put(UtilNode.getName(astNode) + ":" + astNode.getStartPosition(), dstGNode);
   }

   GNode createGNode(ASTNode astNode, String nodeName) {
      String dstGNodeId = nodeName + astNode.getStartPosition();
      if (astNode instanceof TypeDeclaration) {
         return new GClassNode(dstGNodeId, nodeName);
      } else {
         return new GMethodNode(dstGNodeId, nodeName);
      }
   }
}
