/**
 */
package visitor;

import java.util.List;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.InfixExpression.Operator;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;

/**
 * @since JavaSE-1.8
 */
public class ReplaceMethodCallVisitor extends ASTVisitor {
	private String CLASSNAME_STR;
	private String METHODNAME_TRIM;

	private String PKGNAME_UTILSTR;
	private String CLASSNAME_UTILSTR;
	private String METHODNAME_UTILSTR;

	private ASTRewrite rewrite;
	private AST astCUnit;
	private boolean updated;

	public ReplaceMethodCallVisitor(AST astCUnit, ASTRewrite rewrite, String[] dlgMethodToBeReplacedDetails,
			String[] dlgNewMethodInfoDetails) {
		this.astCUnit = astCUnit;
		this.rewrite = rewrite;
		this.CLASSNAME_STR = dlgMethodToBeReplacedDetails[0];
		this.METHODNAME_TRIM = dlgMethodToBeReplacedDetails[1];
		this.PKGNAME_UTILSTR = dlgNewMethodInfoDetails[0];
		this.CLASSNAME_UTILSTR = dlgNewMethodInfoDetails[1];
		this.METHODNAME_UTILSTR = dlgNewMethodInfoDetails[2];

	}

	public boolean visit(TypeDeclaration node) {
		String name = node.getName().getFullyQualifiedName();
		System.out.println(name);
		if (name.equals(CLASSNAME_UTILSTR)) {
			this.updated = false;
			return false;
		} else {
			return super.visit(node);
		}
	}

	@Override
	public boolean visit(MethodInvocation miExpr) {
		String identifier = miExpr.getName().getIdentifier();
		String declaringClass = miExpr.resolveMethodBinding().getDeclaringClass().getName();

		// TODO Class Exercise
		// if (false /* ?.equals(?) && ?.endsWith(?)*/) {
		if (declaringClass.equals(CLASSNAME_STR) && identifier.endsWith(METHODNAME_TRIM)) {
			ExpressionStatement stmt = createNewStmt(miExpr);
			this.rewrite.replace(miExpr, stmt.getExpression(), null);
			this.updated = true;
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	ExpressionStatement createNewStmt(MethodInvocation miExpr) {
		// TODO Class Exercise
		MethodInvocation newMethodInvocation = this.astCUnit
				.newMethodInvocation(); /* this.astCUnit. YOUR_ANSWER */
		QualifiedName newQualifiedName = this.astCUnit.newQualifiedName( //
				this.astCUnit.newSimpleName(PKGNAME_UTILSTR), this.astCUnit.newSimpleName(CLASSNAME_UTILSTR));
		newMethodInvocation.setExpression(newQualifiedName);
		newMethodInvocation.setName(this.astCUnit.newSimpleName(
				METHODNAME_UTILSTR) /* this.astCUnit.YOUR_ANSWER */);

		// TODO Class Exercise
		Expression objName = miExpr.getExpression();
		if (objName instanceof SimpleName) {
			SimpleName n = (SimpleName) objName;
			SimpleName newSimpleName = this.astCUnit.newSimpleName(n.getIdentifier());
			; /* this.astCUnit.YOUR_ANSWER(n.getIdentifier()); */
			newMethodInvocation.arguments().add(newSimpleName);
		}
		return this.astCUnit.newExpressionStatement(newMethodInvocation /* YOUR_ANSWER */);
	}

	@SuppressWarnings("unchecked")
	void addArguments(MethodInvocation miExpr, MethodInvocation newMethodInvocation) {
		List<Object> args = miExpr.arguments();
		for (Object o : args) {
			if (o instanceof SimpleName) {
				SimpleName n = (SimpleName) o;
				SimpleName arg = this.astCUnit.newSimpleName(n.getIdentifier());
				newMethodInvocation.arguments().add(arg);
			} else if (o instanceof InfixExpression) {
				InfixExpression iExpr = (InfixExpression) o;
				Operator op = iExpr.getOperator();
				Expression leftOp = iExpr.getLeftOperand();
				Expression rightOp = iExpr.getRightOperand();

				InfixExpression newInfixExpr = this.astCUnit.newInfixExpression();
				newInfixExpr.setOperator(op);
				if (rightOp instanceof SimpleName) {
					setSimpleName(rightOp, newInfixExpr, false);
				}
				if (rightOp instanceof StringLiteral) {
					setStringLiteral(rightOp, newInfixExpr, false);
				}
				if (leftOp instanceof SimpleName) {
					setSimpleName(leftOp, newInfixExpr, true);
				}
				if (leftOp instanceof StringLiteral) {
					setStringLiteral(leftOp, newInfixExpr, true);
				}
				newMethodInvocation.arguments().add(newInfixExpr);
			}
		}
	}

	void setSimpleName(Object operand, InfixExpression newInfixExpr, boolean isLeft) {
		SimpleName n = (SimpleName) operand;
		SimpleName newName = this.astCUnit.newSimpleName(n.getIdentifier());
		if (isLeft) {
			newInfixExpr.setLeftOperand(newName);
		} else {
			newInfixExpr.setRightOperand(newName);
		}
	}

	void setStringLiteral(Object operand, InfixExpression newInfixExpr, boolean isLeft) {
		StringLiteral lit = (StringLiteral) operand;
		StringLiteral newLit = this.astCUnit.newStringLiteral();
		newLit.setLiteralValue(lit.getLiteralValue());
		if (isLeft) {
			newInfixExpr.setLeftOperand(newLit);
		} else {
			newInfixExpr.setRightOperand(newLit);
		}
	}

	public boolean isUpdated() {
		return updated;
	}
}
