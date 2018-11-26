package handler;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;


import analysis.ProjectAnalyzerClassEx;
import util.UtilMsg;
import view.SimpleZestViewASTTYPE1101Ayinala;

public class UpdateGraphViewHandler {
   @Execute
   public void execute(EPartService service) throws CoreException {

      MPart findPart = service.findPart(SimpleZestViewASTTYPE1101Ayinala.SIMPLEZESTVIEW);
      if (findPart != null && findPart.getObject() instanceof SimpleZestViewASTTYPE1101Ayinala) {
         ProjectAnalyzerClassEx analyzer = new ProjectAnalyzerClassEx();
         analyzer.analyze();
      ((SimpleZestViewASTTYPE1101Ayinala) findPart.getObject()).update();;
      }
   }
}