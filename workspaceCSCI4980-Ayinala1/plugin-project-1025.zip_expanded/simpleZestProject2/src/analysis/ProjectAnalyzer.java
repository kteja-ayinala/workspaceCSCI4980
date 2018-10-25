/*
 * @(#) ASTAnalyzer.java
 *
 * Copyright 2015-2018 The Software Analysis Laboratory
 * Computer Science, The University of Nebraska at Omaha
 * 6001 Dodge Street, Omaha, NE 68182.
 */
package analysis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

import data.DataNode;
import visitor.DeclarationVisitor;

public class ProjectAnalyzer {
   private static final String JAVANATURE = "org.eclipse.jdt.core.javanature";
   protected String prjName, pkgName;
   List<DataNode> nodeList = new ArrayList<DataNode>();

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
            DataNode pkgNode = new DataNode(pkgName);
            this.nodeList.add(pkgNode);
            analyzeCompilationUnit(iPackage.getCompilationUnits(), pkgNode);
         }
      }
   }

   protected void analyzeCompilationUnit(ICompilationUnit[] iCompilationUnits, DataNode pkgNode) throws JavaModelException {
      // =============================================================
      // 3rd step: ICompilationUnits
      // =============================================================
      for (ICompilationUnit iUnit : iCompilationUnits) {
         CompilationUnit cUnit = parse(iUnit);
         DeclarationVisitor declVisitor = new DeclarationVisitor();
         cUnit.accept(declVisitor);
         // Add child nodes of a package.
         pkgNode.addChildNodes(declVisitor.getClassNodes());
         // Add a list of class nodes, each of which adds its method nodes.
         this.nodeList.addAll(declVisitor.getClassNodes());
      }
   }

   public List<DataNode> getNodeList() {
      return this.nodeList;
   }

   /**
    * Reads a ICompilationUnit and creates the AST DOM for manipulating the Java source file.
    */
   protected static CompilationUnit parse(ICompilationUnit iCUnit) {
      ASTParser parser = ASTParser.newParser(AST.JLS8);
      parser.setResolveBindings(true);
      parser.setKind(ASTParser.K_COMPILATION_UNIT);
      Map<String, String> options = JavaCore.getOptions();
      options.put(JavaCore.COMPILER_COMPLIANCE, JavaCore.VERSION_1_8);
      options.put(JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM, JavaCore.VERSION_1_8);
      options.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_8);
      parser.setCompilerOptions(options);
      parser.setSource(iCUnit);
      return (CompilationUnit) parser.createAST(null);
   }
}