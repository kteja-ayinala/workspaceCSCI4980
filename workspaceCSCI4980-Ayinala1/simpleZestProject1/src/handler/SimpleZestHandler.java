/*
 * @(#) SimpleZestHandler.java
 *
 */
package handler;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;

import view.SimpleZestView;

public class SimpleZestHandler {
   private static final String SIMPLEZESTVIEW = "simplezestproject1.partdescriptor.simplezestview";

   @Execute
   public void execute(EPartService epartService) {
      MPart findPart = epartService.findPart(SIMPLEZESTVIEW);

      if (findPart != null) {
         Object findPartObj = findPart.getObject();
         if (findPartObj instanceof SimpleZestView) {
            SimpleZestView viewPart = (SimpleZestView) findPartObj;
            viewPart.setLayoutManager();
         }
      }
   }
}