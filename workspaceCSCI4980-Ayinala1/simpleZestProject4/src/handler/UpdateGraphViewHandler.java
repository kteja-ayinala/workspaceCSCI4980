/*
 * @(#) ASTZestHandler.java
 *
 * Copyright 2015-2018 The Software Analysis Laboratory
 * Computer Science, The University of Nebraska at Omaha
 * 6001 Dodge Street, Omaha, NE 68182.
 */
package handler;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;

import analysis.ProjectAnalyzer;
import view.SimpleZestGraphView;

public class UpdateGraphViewHandler {
   @Execute
   public void execute(EPartService service) throws CoreException {
      System.out.println("[DBG] UpdateGraphViewHandler");
      MPart findPart = service.findPart(SimpleZestGraphView.SIMPLEZESTVIEW);
      if (findPart != null && findPart.getObject() instanceof SimpleZestGraphView) {
         ProjectAnalyzer analyzer = new ProjectAnalyzer();
         analyzer.analyze();
         ((SimpleZestGraphView) findPart.getObject()).update();
      }
   }
}