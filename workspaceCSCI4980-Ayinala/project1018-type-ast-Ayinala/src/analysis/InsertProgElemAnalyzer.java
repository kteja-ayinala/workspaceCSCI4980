package analysis;
/*
 * @(#) ASTAnalyzer.java
 *
 * Copyright 2015-2018 The Software Analysis Laboratory
 * Computer Science, The University of Nebraska at Omaha
 * 6001 Dodge Street, Omaha, NE 68182.
 */

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.text.edits.MalformedTreeException;

import model.ProgramElement;

public class InsertProgElemAnalyzer extends ProjectAnalyzer {
   private ProgramElement newProgElem;
   private IPackageFragment newIPkgFragment;
   private boolean isInserted = false;

   public InsertProgElemAnalyzer(ProgramElement newProgName) {
      this.newProgElem = newProgName;
      this.isInserted = false;
   }

   public void insertNewProgElement() {
      try {
         IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
         for (IProject iProject : projects) {
            if (isOpenedJavaProject(iProject) == false) {
               continue;
            }
            if (isInserted == false) {
               insertNewProgElement(iProject);
            }
         }
      } catch (MalformedTreeException | BadLocationException | CoreException e) {
         e.printStackTrace();
      }
   }

   void insertNewProgElement(IProject project) throws CoreException, JavaModelException, MalformedTreeException, BadLocationException {
      IFolder folder = project.getFolder("src");
      IPackageFragmentRoot srcFolder = JavaCore.create(project).getPackageFragmentRoot(folder);
      newIPkgFragment = srcFolder.createPackageFragment(this.newProgElem.getPkgName(), true, null);
      createNewCUnit();
   }

   @SuppressWarnings("unchecked")
   void createNewCUnit() throws JavaModelException, MalformedTreeException, BadLocationException {
      AST ast = AST.newAST(AST.JLS8);
      CompilationUnit cUnit = ast.newCompilationUnit();
      // package
      PackageDeclaration packageDeclaration = ast.newPackageDeclaration();
      packageDeclaration.setName(ast.newSimpleName(this.newProgElem.getPkgName()));
      cUnit.setPackage(packageDeclaration);
      // type
      TypeDeclaration typeDecl = ast.newTypeDeclaration();
      typeDecl.modifiers().add(ast.newModifier(Modifier.ModifierKeyword.PUBLIC_KEYWORD));
      typeDecl.setName(ast.newSimpleName(this.newProgElem.getClassName()));
      // method
      MethodDeclaration methodDecl = ast.newMethodDeclaration();
      methodDecl.setConstructor(false);
      methodDecl.modifiers().add(ast.newModifier(Modifier.ModifierKeyword.PUBLIC_KEYWORD));
      methodDecl.setName(ast.newSimpleName(this.newProgElem.getMethodName()));
      methodDecl.setReturnType2(ast.newPrimitiveType(PrimitiveType.VOID));
      Block newBlock = ast.newBlock();
      methodDecl.setBody(newBlock);
      typeDecl.bodyDeclarations().add(methodDecl);
      cUnit.types().add(typeDecl);
      // create a compilation unit
      newIPkgFragment.createCompilationUnit(this.newProgElem.getClassName() + ".java", cUnit.toString(), true, null);
      this.isInserted = true;
   }
}