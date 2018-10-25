package handler;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;

import analysis.ProjectAnalyzer;
import view.SimpleZestViewAST1025Ayinala;

public class FindEmptyMethodsHandler {
	@Execute
	public void execute(EPartService service) {
		try {
			MPart findPart = service.findPart(SimpleZestViewAST1025Ayinala.VIEW_ID);
			if (findPart != null && findPart.getObject() instanceof SimpleZestViewAST1025Ayinala) {
				ProjectAnalyzer analyzer = new ProjectAnalyzer();
				analyzer.analyze();

				SimpleZestViewAST1025Ayinala zestView = (SimpleZestViewAST1025Ayinala) findPart.getObject();
				if (!analyzer.getNodeList().isEmpty()) {
					zestView.updateByContextMenu(analyzer.getNodeList());
				}
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}
}
