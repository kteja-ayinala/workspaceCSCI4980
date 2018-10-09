package visitor.rewrite;

import java.util.List;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jdt.core.dom.rewrite.ListRewrite;

import model.ProgramElement;
import util.MsgUtil;

public class ReplaceMethodVisitor extends ASTVisitor {
	private ProgramElement curProgElem;
	private String newMethodName;

	private ASTRewrite rewrite;
	private AST astCUnit;

	private MethodDeclaration methodToBeRemoved;
	private TypeDeclaration typeDecl;

	private boolean isNewMethodAdded = false;

	public ReplaceMethodVisitor(ProgramElement curProgElem, String newMethodName) {
		this.curProgElem = curProgElem;
		this.newMethodName = newMethodName;
	}

	@Override
	public boolean visit(TypeDeclaration node) {
		typeDecl = node;
		return super.visit(node);
	}

	@Override
	public void endVisit(TypeDeclaration typeDecl) {
		if (isNewMethodAdded == false) {
			return;
		}
		ListRewrite lrw = rewrite.getListRewrite(typeDecl, //
				TypeDeclaration.BODY_DECLARATIONS_PROPERTY);
		lrw.remove(methodToBeRemoved, null);
	}

	public boolean visit(MethodDeclaration node) {
		if (checkMethod(node)) {

			if (isPublicModifier(node, node.getName(), "public") && //
					MsgUtil.openQuestion("Do you want to rename this static method " + //
							node.getName() + " ?") == false) {
				return false;
			}

			this.methodToBeRemoved = node;
			addNewMethod(node);
			isNewMethodAdded = true;
		}
		return true;
	}

	private boolean isPublicModifier(MethodDeclaration node, SimpleName oldName, String modifierStr) {
		List<?> modifiers = node.modifiers();
		System.out.println("[DBG] modifiers' size: " + modifiers.size());
		for (Object m : modifiers) {
			if (m instanceof Modifier) {
				Modifier mod = (Modifier) m;
				String modStr = mod.getKeyword().toString();
				System.out.println("[DBG] " + oldName + "'s Modifier: " + modifierStr);
				if (modStr == modifierStr) {
					return true;
				} else {
					return false;
				}
			}
		}
		return false;
	}

	private boolean checkMethod(MethodDeclaration md) {
		boolean check1 = this.curProgElem.getMethodName().equals(md.getName().getFullyQualifiedName());
		boolean check2 = this.curProgElem.getParameterSize().equals(md.parameters().size());
		if (check1 && check2) {
			return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	void addNewMethod(MethodDeclaration node) {
		Type curRetType = node.getReturnType2();
		List<?> curBodyStmts = node.getBody().statements();
		List<?> curModifiers = node.modifiers();
		List<?> curParameters = node.parameters();

		MethodDeclaration newMethodDecl = typeDecl.getAST().newMethodDeclaration();
		newMethodDecl.setName(astCUnit.newSimpleName(this.newMethodName));
		// * modifier
		for (Object m : curModifiers) {
			if (m instanceof Modifier) {
				Modifier mod = (Modifier) m;
				Modifier newMod = astCUnit.newModifier(mod.getKeyword());
				newMethodDecl.modifiers().add(newMod);
				System.out.println("[DBG] Modifier: " + mod.getKeyword());
			}
		}
		// * return type
		if (curRetType.isPrimitiveType()) {
			PrimitiveType pt = (PrimitiveType) curRetType;
			newMethodDecl.setReturnType2(astCUnit.newPrimitiveType(pt.getPrimitiveTypeCode()));
		}
		// * parameters
		for (Object o : curParameters) {
			if (o instanceof SingleVariableDeclaration) {
				SingleVariableDeclaration svd = (SingleVariableDeclaration) o;
				SingleVariableDeclaration newSVD = astCUnit.newSingleVariableDeclaration();
				Type type = svd.getType();
				if (type.isPrimitiveType()) {
					PrimitiveType pt = (PrimitiveType) type;
					newSVD.setType(astCUnit.newPrimitiveType(pt.getPrimitiveTypeCode()));
				}
				newSVD.setName(astCUnit.newSimpleName(svd.getName().getIdentifier()));
				newMethodDecl.parameters().add(newSVD);
			}
		}
		// * body
		Block block = astCUnit.newBlock();
		ListRewrite listRewrite = rewrite.getListRewrite(block, Block.STATEMENTS_PROPERTY);
		for (Object stmt : curBodyStmts) {
			listRewrite.insertLast((ASTNode) stmt, null);
		}
		newMethodDecl.setBody(block);

		ListRewrite lrw = rewrite.getListRewrite(typeDecl, //
				TypeDeclaration.BODY_DECLARATIONS_PROPERTY);
		lrw.insertAfter(newMethodDecl, methodToBeRemoved, null);
	}

	public void setAST(AST ast) {
		this.astCUnit = ast;
	}

	public void setASTRewrite(ASTRewrite rewrite) {
		this.rewrite = rewrite;
	}
}
