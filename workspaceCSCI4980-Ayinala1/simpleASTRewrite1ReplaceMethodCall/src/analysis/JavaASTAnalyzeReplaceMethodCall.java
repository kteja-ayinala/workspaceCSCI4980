/**
 */
package analysis;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;

import view.ASTRewriteViewer;
import visitor.ReplaceMethodCallVisitor;

/**
 * @since JavaSE-1.8
 */
public class JavaASTAnalyzeReplaceMethodCall {
   private static final String JAVANATURE = "org.eclipse.jdt.core.javanature";
   protected String prjName, pkgName;
   private ASTRewriteViewer viewer;

   public JavaASTAnalyzeReplaceMethodCall(ASTRewriteViewer viewer) {
      this.viewer = viewer;
   }

   public void analyze() throws CoreException {
      // =============================================================
      // 1st step: Project
      // =============================================================
      IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
      for (IProject project : projects) {
         if (!project.isOpen() || !project.isNatureEnabled(JAVANATURE)) { // Check if we have a Java project.
            continue;
         }
         prjName = project.getName();
         analyzePackages(JavaCore.create(project).getPackageFragments());
      }
   }

   protected void analyzePackages(IPackageFragment[] packages) throws CoreException, JavaModelException {
      // =============================================================
      // 2nd step: Packages
      // =============================================================
      for (IPackageFragment iPackage : packages) {
         if (iPackage.getKind() == IPackageFragmentRoot.K_SOURCE) {
            if (iPackage.getCompilationUnits().length < 1) {
               continue;
            }
            pkgName = iPackage.getElementName();
            analyzeCompilationUnit(iPackage.getCompilationUnits());
         }
      }
   }

   private void analyzeCompilationUnit(ICompilationUnit[] iCompilationUnits) throws JavaModelException {
      // =============================================================
      // 3rd step: ICompilationUnits
      // =============================================================
      for (ICompilationUnit iUnit : iCompilationUnits) {
         // Change to this compilation unit or its children are
         // done in memory. Only the new buffer is affected.
         ICompilationUnit workingCopy = iUnit.getWorkingCopy(null);
         CompilationUnit compilationUnit = parse(workingCopy);
         AST ast = compilationUnit.getAST();
         ASTRewrite rewriter = ASTRewrite.create(ast);

         ReplaceMethodCallVisitor javaASTvisitor = new ReplaceMethodCallVisitor(ast, rewriter);
         compilationUnit.accept(javaASTvisitor);

         // Apply the edits.
         workingCopy.applyTextEdit(rewriter.rewriteAST(), new NullProgressMonitor());
         // Save the changes.
         workingCopy.commitWorkingCopy(false, new NullProgressMonitor());
         if (javaASTvisitor.isUpdated()) {
            this.viewer.appendText(iUnit.getElementName() + " updated.\n");
         }
      }
   }

   private static CompilationUnit parse(ICompilationUnit unit) {
      ASTParser parser = ASTParser.newParser(AST.JLS8);
      parser.setKind(ASTParser.K_COMPILATION_UNIT);
      parser.setSource(unit);
      parser.setResolveBindings(true);
      return (CompilationUnit) parser.createAST(null); // parse
   }
}