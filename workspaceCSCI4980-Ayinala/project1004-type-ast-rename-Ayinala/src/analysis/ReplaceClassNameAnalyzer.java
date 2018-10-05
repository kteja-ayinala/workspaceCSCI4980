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

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.text.edits.MalformedTreeException;

import model.ProgramElement;
import util.MsgUtil;
import visitor.rewrite.ReplaceClassVisitor;

public class ReplaceClassNameAnalyzer extends ProjectAnalyzer {
	private ProgramElement curProgElem;
	private String newClassName;

	public ReplaceClassNameAnalyzer(ProgramElement curProgElem, String newClassName) {
		this.curProgElem = curProgElem;
		this.newClassName = newClassName;
	}

	@Override
	protected void analyzeCompilationUnit(ICompilationUnit[] iCompilationUnits) throws JavaModelException {
		try {
			List<String> classes = new ArrayList<String>();
			for (ICompilationUnit iCunit : iCompilationUnits) {
				classes.add(iCunit.getElementName());
			}
			if (!(classes.contains(newClassName + ".java"))) {
				replaceClassName(iCompilationUnits);
			} else {
				MsgUtil.openWarning("The name" + " " + newClassName + " " + "is already taken");
			}

		} catch (MalformedTreeException | BadLocationException e) {
			e.printStackTrace();
		}
	}

	void replaceClassName(ICompilationUnit[] iCompilationUnits)
			throws JavaModelException, MalformedTreeException, BadLocationException {
		if (this.pkgName.equals(curProgElem.getPkgName()) == false) {
			return;
		}

		for (ICompilationUnit iCunit : iCompilationUnits) {

			ICompilationUnit workingCopy = iCunit.getWorkingCopy(null); // create
																		// DOM/AST
																		// from
																		// a
																		// ICompilationUnit

			CompilationUnit cUnit = parse(workingCopy); // create AST
			ASTRewrite rewrite = ASTRewrite.create(cUnit.getAST()); // create
																	// ASTRewrite

			ReplaceClassVisitor visitor = new ReplaceClassVisitor(curProgElem, newClassName);
			visitor.setICompilationUnit(iCunit);
			visitor.setASTRewrite(rewrite);
			visitor.setCompilationUnit(cUnit);
			cUnit.accept(visitor);

			applyTextEditAndCommit(workingCopy, rewrite);
		}
	}
}