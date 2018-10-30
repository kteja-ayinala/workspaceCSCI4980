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

import view.SimpleZestView;

public class UpdateZestViewHandler {
   private static final String SIMPLEZESTVIEW3 = "simplezestproject3.partdescriptor.simplezestview3";

   @Execute
   public void execute(EPartService service) {
      MPart findPart = service.findPart(SIMPLEZESTVIEW3);
      if (findPart != null && findPart.getObject() instanceof SimpleZestView) {
         SimpleZestView zestView = (SimpleZestView) findPart.getObject();
         zestView.setLayoutManager();
      }
   }
}