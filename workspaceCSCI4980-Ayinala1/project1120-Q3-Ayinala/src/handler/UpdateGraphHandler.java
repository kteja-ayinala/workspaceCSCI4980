package handler;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;

import analysis.ProjectAnalyzerClassEx;
import util.UtilMsg;
import view.AyinalaZestView;

public class UpdateGraphHandler {
	@Execute
	public void execute(EPartService service) throws CoreException {

		MPart findPart = service.findPart(AyinalaZestView.SIMPLEZESTVIEW);
		if (findPart != null && findPart.getObject() instanceof AyinalaZestView) {
			ProjectAnalyzerClassEx analyzer = new ProjectAnalyzerClassEx();
			analyzer.analyze();
			((AyinalaZestView) findPart.getObject()).update();
			;
		}
	}
}