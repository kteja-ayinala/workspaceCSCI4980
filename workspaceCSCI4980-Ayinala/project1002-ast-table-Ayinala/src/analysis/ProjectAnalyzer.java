/*
 * @(#) ASTAnalyzer.java
 *
 * Copyright 2015-2018 The Software Analysis Laboratory
 * Computer Science, The University of Nebraska at Omaha
 * 6001 Dodge Street, Omaha, NE 68182.
 */
package analysis;

import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

import visitor.DeclarationVisitor;

public class ProjectAnalyzer {
	private static final String JAVANATURE = "org.eclipse.jdt.core.javanature";

	public void analyze() throws CoreException {
		// =============================================================
		// 1st step: Project
		// =============================================================
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		for (IProject project : projects) {
			if (!project.isOpen() || !project.isNatureEnabled(JAVANATURE)) { // Check
																				// if
																				// we
																				// have
																				// a
																				// Java
																				// project.
				continue;
			}
			analyzePackages(JavaCore.create(project).getPackageFragments());
		}
	}

	private void analyzePackages(IPackageFragment[] packages) throws CoreException, JavaModelException {
		// =============================================================
		// 2nd step: Packages
		// =============================================================
		for (IPackageFragment iPackage : packages) {
			if (iPackage.getKind() == IPackageFragmentRoot.K_SOURCE) {
				if (iPackage.getCompilationUnits().length < 1) {
					continue;
				}
				analyzeCompilationUnit(iPackage.getCompilationUnits());
			}
		}
	}

	private void analyzeCompilationUnit(ICompilationUnit[] iCompilationUnits) throws JavaModelException {
		// =============================================================
		// 3rd step: ICompilationUnits
		// =============================================================
		for (ICompilationUnit iUnit : iCompilationUnits) {
			CompilationUnit cUnit = parse(iUnit);
			DeclarationVisitor declVisitor = new DeclarationVisitor();
			cUnit.accept(declVisitor);
		}
	}

	/**
	 * Reads a ICompilationUnit and creates the AST DOM for manipulating the
	 * Java source file. Constant for indicating the AST API that handles JLS8.
	 * This API is capable of handling all constructs in the Java language as
	 * described in the Java Language Specification, Java SE 8 Edition (JLS8) as
	 * specified by JSR337. JLS8 is a superset of all earlier versions of the
	 * Java language, and the JLS8 API can be used to manipulate programs
	 * written in all versions of the Java language up to and including Java SE
	 * 8 (aka JDK 1.8).
	 */
	private static CompilationUnit parse(ICompilationUnit iCUnit) {
		ASTParser parser = ASTParser.newParser(AST.JLS8);
		parser.setResolveBindings(true);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		Map<String, String> options = JavaCore.getOptions();
		options.put(JavaCore.COMPILER_COMPLIANCE, JavaCore.VERSION_1_8);
		options.put(JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM, JavaCore.VERSION_1_8);
		options.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_8);
		parser.setCompilerOptions(options);
		parser.setSource(iCUnit);
		return (CompilationUnit) parser.createAST(null);
	}
}