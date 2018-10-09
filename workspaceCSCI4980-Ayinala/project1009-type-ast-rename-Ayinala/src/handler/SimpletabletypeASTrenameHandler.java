package handler;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.swt.widgets.Shell;

import analysis.ProjectAnalyzer;
import model.ModelProvider;
import view.SimpleTableViewRenameTypeAST;

public class SimpletabletypeASTrenameHandler {
	@Inject
	private EPartService epartService;
	@Inject
	@Named(IServiceConstants.ACTIVE_SHELL)
	Shell shell;

	@Execute
	public void execute() throws CoreException {
		System.out.println("TableRefreshHandler!!");
		MPart findPart = epartService.findPart(SimpleTableViewRenameTypeAST.ID);
		Object findPartObj = findPart.getObject();

		if (findPartObj instanceof SimpleTableViewRenameTypeAST) {
			ModelProvider.INSTANCE.clearProgramElements();
			ProjectAnalyzer analyzer = new ProjectAnalyzer();
			analyzer.analyze();
			SimpleTableViewRenameTypeAST v = (SimpleTableViewRenameTypeAST) findPartObj;
			v.setInput(ModelProvider.INSTANCE.getProgramElements());
			v.refresh();
		}
	}
}