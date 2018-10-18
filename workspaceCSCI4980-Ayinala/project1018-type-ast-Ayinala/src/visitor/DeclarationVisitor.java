/*
 * @(#) MethodVisitor.java
 *
 * Copyright 2015-2018 The Software Analysis Laboratory
 * Computer Science, The University of Nebraska at Omaha
 * 6001 Dodge Street, Omaha, NE 68182.
 */
package visitor;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import model.ModelProvider;

public class DeclarationVisitor extends ASTVisitor {

	private String	pkgName;
	private String	className;
	private String	methodName;

	public DeclarationVisitor() {
	}

	@Override
	public boolean visit(PackageDeclaration pkgDecl) {
		pkgName = pkgDecl.getName().getFullyQualifiedName();
		return super.visit(pkgDecl);
	}

	/**
	 * A type declaration is the union of a class declaration
	 * and an interface declaration.
	 */
	@Override
	public boolean visit(TypeDeclaration typeDecl) {
		className = typeDecl.getName().getIdentifier();
		return super.visit(typeDecl);
	}

	@Override
	public boolean visit(MethodDeclaration methodDecl) {
		methodName = methodDecl.getName().getIdentifier();
		int parmSize = methodDecl.parameters().size();
		Type returnType = methodDecl.getReturnType2();
		boolean isRetVoid = false;
		if ( returnType.isPrimitiveType() ) {
			PrimitiveType pt = (PrimitiveType) returnType;
			if (pt.getPrimitiveTypeCode().equals(PrimitiveType.VOID)) {
				isRetVoid = true;
			}
		}
		ModelProvider.INSTANCE.addProgramElements(pkgName, className, methodName, isRetVoid, parmSize);
		return super.visit(methodDecl);
	}
}
