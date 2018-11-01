package util;

import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class UtilNode {

   public static ASTNode getOuterClass(ASTNode node) {
      do {
         // Keep searching if this node is not null and
         // it is not a type declaration node and
         // it is a child of compilation unit node.
         node = node.getParent();
      } while (node != null && node.getNodeType() != ASTNode.TYPE_DECLARATION && //
            ((AbstractTypeDeclaration) node).isPackageMemberTypeDeclaration());
      return node;
   }

   public static String getName(ASTNode astNode) {
      if (astNode instanceof TypeDeclaration) {
         return ((TypeDeclaration) astNode).getName().getFullyQualifiedName();
      } else {
         return ((MethodDeclaration) astNode).getName().getFullyQualifiedName();
      }
   }

   public static String getTypeModifier(ASTNode astNode) {
      TypeDeclaration tNode = (TypeDeclaration) astNode;
      StringBuilder buffer = new StringBuilder();
      List<?> modifiers = tNode.modifiers();
      System.out.println("[DBG] modifiers' size: " + modifiers.size());
      for (Object m : modifiers) {
         if (m instanceof Modifier) {
            Modifier mod = (Modifier) m;
            String modStr = mod.getKeyword().toString();
            buffer.append(modStr + " ");
         }
      }
      return buffer.toString().trim();
   }

   public static String getMethodReturnType(ASTNode astNode) {
      MethodDeclaration node = (MethodDeclaration) astNode;
      return node.getReturnType2().toString();
   }
}
