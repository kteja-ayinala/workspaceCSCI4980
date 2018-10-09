package analysis;
/*
 * @(#) ASTAnalyzer.java
 *
 * Copyright 2015-2018 The Software Analysis Laboratory
 * Computer Science, The University of Nebraska at Omaha
 * 6001 Dodge Street, Omaha, NE 68182.
 */

import java.util.List;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.TextEdit;

import model.ProgramElement;
import util.MsgUtil;
import visitor.rewrite.ReplaceMethodVisitor;

public class ReplaceMethodNameAnalyzer extends ProjectAnalyzer {
	private ProgramElement curProgElem;
	private String newMethodName;
	boolean matchedExistingMethod = false;

	public ReplaceMethodNameAnalyzer(ProgramElement curProgName, String newMethodName) {
		this.curProgElem = curProgName;
		this.newMethodName = newMethodName;
		matchedExistingMethod = false;
	}

	@Override
	protected void analyzeCompilationUnit(ICompilationUnit[] iCompilationUnits) throws JavaModelException {
		try {
			replaceMethodName(iCompilationUnits);
		} catch (MalformedTreeException | BadLocationException e) {
			e.printStackTrace();
		}
	}

	void replaceMethodName(ICompilationUnit[] iCompilationUnits)
			throws JavaModelException, MalformedTreeException, BadLocationException {
		if (this.pkgName.equals(curProgElem.getPkgName()) == false) {
			return;
		}

		for (ICompilationUnit iCUnit : iCompilationUnits) {
			if (iCUnit.getElementName().replace(".java", "").equals(curProgElem.getClassName()) == false) {
				continue;
			}

			// List<String> methodInfo = new ArrayList<String>();
			CompilationUnit cUnit = parse(iCUnit);
			cUnit.accept(new ASTVisitor() {
				@Override
				public boolean visit(MethodDeclaration node) {
					String mName = node.getName().getIdentifier();
					if (mName.equals(newMethodName)) {
						if (equalParams(node.parameters(), curProgElem.getParameters())) {
							matchedExistingMethod = true;

						}
					}
					return true;
				}
			});
		}

		if (matchedExistingMethod) {
			System.out.println("[WRN] Cannot overwrite an existing method");
			MsgUtil.openWarning("The name " + newMethodName + " is already taken. " //
					+ "Please choose a different name");
			return;
		}

		for (ICompilationUnit iCUnit : iCompilationUnits) {
			ICompilationUnit workingCopy = iCUnit.getWorkingCopy(null);
			CompilationUnit cUnit = parse(workingCopy);
			ASTRewrite rewrite = ASTRewrite.create(cUnit.getAST());
			ReplaceMethodVisitor visitor = new ReplaceMethodVisitor(curProgElem, newMethodName);
			visitor.setAST(cUnit.getAST());
			visitor.setASTRewrite(rewrite);
			cUnit.accept(visitor);
			TextEdit edits = rewrite.rewriteAST(); // Compute the edits
			workingCopy.applyTextEdit(edits, null); // Apply the edits.
			workingCopy.commitWorkingCopy(false, null); // Save the changes.
		}
	}

	boolean equalParams(List<?> parmsLeft, List<?> parmsRight) {

		if (parmsLeft.size() != parmsRight.size()) {
			return false;
		}
		for (int i = 0; i < parmsLeft.size(); i++) {
			Object obj1 = parmsLeft.get(i);
			Object obj2 = parmsRight.get(i);
			Type type1 = null, type2 = null;

			if (obj1 instanceof SingleVariableDeclaration) {
				SingleVariableDeclaration svd1 = (SingleVariableDeclaration) obj1;
				type1 = svd1.getType();
			}
			if (obj2 instanceof SingleVariableDeclaration) {
				SingleVariableDeclaration svd2 = (SingleVariableDeclaration) obj2;
				type2 = svd2.getType();
			}

			if (type1 == null || type2 == null || type1.toString().equals(type2.toString()) == false) {
				return false;
			}
			System.out.println(type1.toString() + ", " + type2.toString());
		}
		return true;
	}
}