/*
 * @(#) ASTAnalyzer.java
 *
 * Copyright 2015-2018 The Software Analysis Laboratory
 * Computer Science, The University of Nebraska at Omaha
 * 6001 Dodge Street, Omaha, NE 68182.
 */
package analysis;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import graph.model.GClassNode;
import graph.model.GMethodNode;
import graph.model.GNodeType;
import util.UtilPlatform;
import visitor.DeclarationVisitor;
import util.UtilMsg;

public class MoveMethodAnalyzer {
   private static final String JAVANATURE = "org.eclipse.jdt.core.javanature";
   protected String prjName, pkgName;

   private GMethodNode methodToBeMoved;
   private GClassNode classMoveDestination;
   private IMethod methodElemToBeMoved;
   private IType typeMoveDest;
   private ICompilationUnit iCUnitToBeMovedMethod, iCUnitMoveDest;

   public MoveMethodAnalyzer() {
   }

   public void analyze() {
      // =============================================================
      // 1st step: Project
      // =============================================================
      try {
         IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
         for (IProject project : projects) {
            if (!project.isOpen() || !project.isNatureEnabled(JAVANATURE)) { // Check if we have a Java project.
               continue;
            }
            prjName = project.getName();
            analyzePackages(JavaCore.create(project).getPackageFragments());
         }
      } catch (JavaModelException e) {
         e.printStackTrace();
      } catch (CoreException e) {
         e.printStackTrace();
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
      for (ICompilationUnit iCUnit : iCompilationUnits) {
         CompilationUnit compUnit = parse(iCUnit);

         // TODO: Term Project
         /*
         ASTVisitor * Your Answer * = new * Your Answer *() {
            public boolean visit(* Your Answer * typeDecl) {
               if (checkDst(typeDecl)) {
                  * Your Answer * = (IType) typeDecl.resolveBinding().getJavaElement();
                  iCUnitMoveDest = iCUnit;
               }
               return true;
            }

            private boolean checkDst(* Your Answer * typeDecl) {
               String classSrc = methodToBeMoved.getPkgName() + "." + methodToBeMoved.getClassName();
               String classDst = classMoveDestination.getPkgName() + "." + classMoveDestination.getName();
               String classCur = typeDecl.resolveBinding().getPackage().getName() + "." + typeDecl.getName().getFullyQualifiedName();
               return classCur.equals(classSrc) == false && classCur.equals(classDst);
            }

            public boolean visit(* Your Answer * methodDecl) {
               if (eqSrc(methodDecl)) {
                  * Your Answer * = (IMethod) methodDecl.resolveBinding().getJavaElement();
                  iCUnitToBeMovedMethod = iCUnit;
               }
               return true;
            }

            private boolean eqSrc(* Your Answer * methodDecl) {
               ITypeBinding curClass = methodDecl.resolveBinding().getDeclaringClass();
               String cur = curClass.getPackage().getName() + "." + curClass.getName() + "." + methodDecl.getName();
               String src = methodToBeMoved.getPkgName() + "." + methodToBeMoved.getClassName() + "." + methodToBeMoved.getName();
               return cur.equals(src);
            }
         };
         * Your Answer *.accept(* Your Answer *); */
      }
   }

   private static CompilationUnit parse(ICompilationUnit unit) {
      ASTParser parser = ASTParser.newParser(AST.JLS8);
      parser.setKind(ASTParser.K_COMPILATION_UNIT);
      parser.setSource(unit);
      parser.setResolveBindings(true);
      return (CompilationUnit) parser.createAST(null); // parse
   }

   public void setMethodToBeMoved(GMethodNode mNode) {
      this.methodToBeMoved = mNode;
   }

   public void setClassMoveDestination(GClassNode cNode) {
      this.classMoveDestination = cNode;
   }

   public void moveMethod() {
      /* TODO: Term Project
      if (this.methodToBeMoved.getNodeType().equals(GNodeType.UserSelection) && //
            this.classMoveDestination.getNodeType().equals(GNodeType.UserDoubleClicked)) {
         System.out.println("-> " + this.methodElemToBeMoved.getPath() + "." + this.methodElemToBeMoved.getElementName());
         System.out.println("-> " + this.typeMoveDest.getFullyQualifiedName());
         try {
         
            See https://help.eclipse.org/oxygen/index.jsp?topic=%2Forg.eclipse.jdt.doc.isv%2Freference%2Fapi%2Forg%2Feclipse%2Fjdt%2Fcore%2FIMethod.html
            methodElemToBeMoved.move(* Your Answer *, null, null, false, null);
            
            UtilPlatform.indentAndSave(iCUnitToBeMovedMethod);
            UtilPlatform.indentAndSave(iCUnitMoveDest);
         } catch (JavaModelException e) {
            e.printStackTrace();
         }
      } else {
         System.out.println("[DBG] Please select class and method nodes to move.");
      } */
      UtilMsg.openWarning("TODO: should implement it as a part of the term project.");
   }
}