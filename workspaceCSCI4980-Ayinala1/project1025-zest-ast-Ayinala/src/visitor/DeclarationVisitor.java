/*
 * @(#) MethodVisitor.java
 *
 * Copyright 2015-2018 The Software Analysis Laboratory
 * Computer Science, The University of Nebraska at Omaha
 * 6001 Dodge Street, Omaha, NE 68182.
 */
package visitor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import data.DataNode;

public class DeclarationVisitor extends ASTVisitor {
	Map<String, DataNode> mapClassNodes = new HashMap<String, DataNode>();

	/**
	 * A type declaration is the union of a class declaration and an interface
	 * declaration.
	 */
	@Override
	public boolean visit(TypeDeclaration typeDecl) {
		String pkgName = typeDecl.resolveBinding().getPackage().getName();
		String typeName = typeDecl.getName().getFullyQualifiedName();
		String classNodeName = pkgName + "." + typeName;
		mapClassNodes.put(classNodeName, new DataNode(classNodeName));
		return super.visit(typeDecl);
	}

	@Override
	public boolean visit(MethodDeclaration methodDecl) {
		String typeName = methodDecl.resolveBinding().getDeclaringClass().getQualifiedName();
		DataNode classNode = mapClassNodes.get(typeName);
		if (classNode == null) {
			throw new RuntimeException(); // no parent node.
		} else {
			String methodName = methodDecl.getName().getFullyQualifiedName();
			String methodNodeName = typeName + "." + methodName;
			boolean isEmptyBody = isEmptyBody(methodDecl);
			classNode.addChildNode(new DataNode(methodNodeName, isEmptyBody));
		}
		return super.visit(methodDecl);
	}

	private boolean isEmptyBody(MethodDeclaration methodDecl) {
		List<?> statements = methodDecl.getBody().statements();

		if (statements.isEmpty()) {
			return true;
		} else {
			return false;
		}

	}

	public List<DataNode> getClassNodes() {
		Collection<DataNode> values = mapClassNodes.values();
		return new ArrayList<DataNode>(values);
	}

	@Deprecated
	public Map<String, DataNode> getMapClassNodes() {
		return mapClassNodes;
	}

	@Deprecated
	public ASTNode getOuterClass(ASTNode node) {
		do {
			node = node.getParent();
		} while (node != null && node.getNodeType() != ASTNode.TYPE_DECLARATION && //
				((AbstractTypeDeclaration) node).isPackageMemberTypeDeclaration());
		return node;
	}
}
