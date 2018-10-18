package analysis;
/*
 * @(#) ASTAnalyzer.java
 *
 * Copyright 2015-2018 The Software Analysis Laboratory
 * Computer Science, The University of Nebraska at Omaha
 * 6001 Dodge Street, Omaha, NE 68182.
 */

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.JavaCore;

import model.ProgramElement;
import util.ParseUtil;

public class DelClassAnalyzer extends DelPackageAnalyzer {
   private ProgramElement curProgElem;

   public DelClassAnalyzer(ProgramElement newProgName) {
      super(newProgName);
      this.curProgElem = newProgName;
      this.isDeleted = false;
   }

   public void deleteClass() {
      IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
      for (IProject iProject : projects) {
         try {
            if (isOpenedJavaProject(iProject) == false) {
               continue;
            }
            deleteClass(JavaCore.create(iProject).getPackageFragments());
         } catch (Exception e) {
            e.printStackTrace();
         }
      }
   }

   void deleteClass(IPackageFragment[] packages) throws Exception {
      for (IPackageFragment iPackage : packages) {
         if (compare(iPackage, curProgElem.getPkgName())) {
            deleteClass(iPackage);
         }
      }
   }

   void deleteClass(IPackageFragment iPackage) throws Exception {
      for (ICompilationUnit iCUnit : iPackage.getCompilationUnits()) {
         if (compare(iCUnit, this.curProgElem.getClassName()) && !this.isDeleted) {
            iCUnit.delete(true, null);
            this.isDeleted = true;
            return;
         }
      }
   }

   boolean compare(ICompilationUnit iCUnit, String className) throws Exception {
      String nameICUnit = ParseUtil.getClassNameFromJavaFile(iCUnit.getElementName());
      return nameICUnit.equals(className);
   }
}