/*
 * @(#) ASTZestHandler.java
 *
 * Copyright 2015-2018 The Software Analysis Laboratory
 * Computer Science, The University of Nebraska at Omaha
 * 6001 Dodge Street, Omaha, NE 68182.
 */
package handler;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;

import analysis.ProjectAnalyzer;
import view.InteractionAndNavigationViewer;

public class ASTZestHandler {
   @Execute
   public void execute(EPartService service) {
      MPart findPart = service.findPart(InteractionAndNavigationViewer.VIEW_ID);
      if (findPart != null && findPart.getObject() instanceof InteractionAndNavigationViewer) {
         new ProjectAnalyzer().analyze();
         ((InteractionAndNavigationViewer) findPart.getObject()).update();
      }
   }
}