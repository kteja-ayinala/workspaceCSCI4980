package handler;

import javax.inject.Inject;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;

import analysis.ProjectAnalyzer;
import model.ModelProvider;
import view.SimpleTableViewDeleteMethodAST1018Ayinala;

public class RefreshHandler {
	@Inject
	private EPartService	epartService;

	@Execute
	public void execute() throws CoreException {
		System.out.println("TableRefreshHandler!!");
		MPart findPart = epartService.findPart(SimpleTableViewDeleteMethodAST1018Ayinala.ID);
		Object findPartObj = findPart.getObject();

		if (findPartObj instanceof SimpleTableViewDeleteMethodAST1018Ayinala) {
			ModelProvider.INSTANCE.clearProgramElements();
			ProjectAnalyzer analyzer = new ProjectAnalyzer();
			analyzer.analyze();
			SimpleTableViewDeleteMethodAST1018Ayinala v = (SimpleTableViewDeleteMethodAST1018Ayinala) findPartObj;
			v.setInput(ModelProvider.INSTANCE.getProgramElements());
			v.refresh();
		}
	}
}