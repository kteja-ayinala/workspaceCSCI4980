package util;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

public class ParseUtil {
   /**
    * Reads a ICompilationUnit and creates the AST DOM for manipulating the Java source file.
    */
   public static CompilationUnit parse(ICompilationUnit unit) {
      ASTParser parser = ASTParser.newParser(AST.JLS8);
      parser.setKind(ASTParser.K_COMPILATION_UNIT);
      parser.setSource(unit);
      parser.setResolveBindings(true);
      return (CompilationUnit) parser.createAST(null); // parse
   }

   public static String getClassNameFromJavaFile(String javafileName) {
      int indexOfDot = javafileName.lastIndexOf('.');
      if (indexOfDot < 0) {
         return null;
      }
      return javafileName.substring(0, indexOfDot);
   }
}
