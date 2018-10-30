
package handler;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;

import view.SimpleView;

public class ClearViewHandler {
   @Execute
   public void execute(EPartService service) {
      System.out.println("ClearViewHandler!!");
      // Find a view.
      MPart findPart = service.findPart(SimpleView.ID);
      SimpleView simpleView = (SimpleView) findPart.getObject();
      simpleView.clearText();
   }
}