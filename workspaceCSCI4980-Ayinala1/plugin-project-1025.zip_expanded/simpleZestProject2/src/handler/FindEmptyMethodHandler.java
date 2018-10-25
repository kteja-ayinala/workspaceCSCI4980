
package handler;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;

import analysis.ProjectAnalyzer;
import view.SimpleASTZestView;

public class FindEmptyMethodHandler {
   @Execute
   public void execute(EPartService service) {
      try {
         MPart findPart = service.findPart(SimpleASTZestView.VIEW_ID);
         if (findPart != null && findPart.getObject() instanceof SimpleASTZestView) {
            ProjectAnalyzer analyzer = new ProjectAnalyzer();
            analyzer.analyze();

            SimpleASTZestView zestView = (SimpleASTZestView) findPart.getObject();
            if (!analyzer.getNodeList().isEmpty()) {
               zestView.updateByContextMenu(analyzer.getNodeList());
            }
         }
      } catch (CoreException e) {
         e.printStackTrace();
      }
   }
}
