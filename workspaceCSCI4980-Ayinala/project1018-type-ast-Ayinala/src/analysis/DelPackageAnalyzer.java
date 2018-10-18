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
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;

import model.ProgramElement;

public class DelPackageAnalyzer extends ProjectAnalyzer {
   private ProgramElement curProgElem;
   public boolean isDeleted = false;

   public DelPackageAnalyzer(ProgramElement newProgName) {
      this.curProgElem = newProgName;
      this.isDeleted = false;
   }

   public void deletePkg() {
      IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
      for (IProject iProject : projects) {
         try {
            if (isOpenedJavaProject(iProject) == false) {
               continue;
            }
            deletePkg(JavaCore.create(iProject).getPackageFragments());
         } catch (Exception e) {
            e.printStackTrace();
         }
      }
   }

   void deletePkg(IPackageFragment[] packages) throws Exception {
      for (IPackageFragment iPackage : packages) {
         if (compare(iPackage, curProgElem.getPkgName()) && !isDeleted) {
            iPackage.delete(true, null);
            isDeleted = true;
            break;
         }
      }
   }

   boolean compare(IPackageFragment iPackage, String pkgName) throws Exception {
      return (iPackage.getKind() == IPackageFragmentRoot.K_SOURCE && //
            iPackage.getCompilationUnits().length >= 1 && //
            iPackage.getElementName().equals(pkgName));
   }

   public boolean isDeleted() {
      return isDeleted;
   }

   public void setDeleted(boolean isDeleted) {
      this.isDeleted = isDeleted;
   }
}