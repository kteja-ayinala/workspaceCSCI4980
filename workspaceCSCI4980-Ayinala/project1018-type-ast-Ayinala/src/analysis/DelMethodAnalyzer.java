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
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.text.edits.TextEdit;

import model.ProgramElement;
import util.ParseUtil;
import visitor.rewrite.DelMethodVisitor;

public class DelMethodAnalyzer extends DelClassAnalyzer {
	private ProgramElement curProgElem;

	public DelMethodAnalyzer(ProgramElement newProgName) {
		super(newProgName);
		this.curProgElem = newProgName;
		this.isDeleted = false;
	}

	public void deleteMethod() {
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		try {
			for (IProject iProject : projects) {
				if (isOpenedJavaProject(iProject) == false) {
					continue;
				}
				deleteMethod(JavaCore.create(iProject).getPackageFragments());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void deleteMethod(IPackageFragment[] packages) throws Exception {
		for (IPackageFragment iPackage : packages) {
			if (compare(iPackage, curProgElem.getPkgName())) {
				deleteMethod(iPackage.getCompilationUnits());
			}
		}
	}

	void deleteMethod(ICompilationUnit[] iCompilationUnits) throws Exception {
		for (ICompilationUnit iCUnit : iCompilationUnits) {
			if (compare(iCUnit, curProgElem.getClassName()) && !isDeleted) {
				ICompilationUnit workingCopy = iCUnit.getWorkingCopy(null);
				CompilationUnit cUnit = ParseUtil.parse(workingCopy);
				ASTRewrite rewrite = ASTRewrite.create(cUnit.getAST());

				DelMethodVisitor v = new DelMethodVisitor(curProgElem);
				v.setASTRewrite(rewrite);
				cUnit.accept(v);
				isDeleted = v.isDeleted();

				if (isDeleted) {
					TextEdit edits = rewrite.rewriteAST();
					workingCopy.applyTextEdit(edits, null);
					workingCopy.commitWorkingCopy(false, null);
				}
			}
		}
	}
}