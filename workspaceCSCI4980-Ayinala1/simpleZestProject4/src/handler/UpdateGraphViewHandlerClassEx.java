
package handler;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;

import analysis.ProjectAnalyzerClassEx;
import util.UtilMsg;
import view.SimpleZestGraphView;

public class UpdateGraphViewHandlerClassEx {
   @Execute
   public void execute(EPartService service) throws CoreException {
      System.out.println("[DBG] UpdateGraphViewHandlerClassEx");
      UtilMsg.openWarning("Class Exercise");

      /* TODO: Class Exercise
       *  
      MPart findPart = service.findPart(SimpleZestGraphView.SIMPLEZESTVIEW);
      if (findPart != null && findPart.getObject() instanceof SimpleZestGraphView) {
         ProjectAnalyzerClassEx analyzer = new ProjectAnalyzerClassEx();
         analyzer.analyze();
         ((SimpleZestGraphView) findPart.getObject()).* Your Answer *;
      }*/
   }
}