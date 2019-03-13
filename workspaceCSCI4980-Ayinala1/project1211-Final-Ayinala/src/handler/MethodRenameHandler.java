package handler;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;

import analysis.JavaASTAnalyzeReplaceMethodCall;
import view.AyinalaZestView;

/**
 * @since JavaSE-1.8
 */
public class MethodRenameHandler {
	@Inject
	@Named(IServiceConstants.ACTIVE_SHELL)
	Shell shell;
	String newmethodname;
	private EPartService service;

	@Execute
	public void execute(EPartService service) {
		MPart part = service.findPart(AyinalaZestView.VIEW_ID);
		if (part != null)

		{
			if (part.getObject() instanceof AyinalaZestView) {
			}
		}
	}

	public void renameMethod() {
		InputDialog dlgNewMethodInfo = new InputDialog(shell, "", "Enter new method name:", "", null);
		if (dlgNewMethodInfo.open() == Window.OK) {
			System.out.println(dlgNewMethodInfo.getValue());
		}
		newmethodname = dlgNewMethodInfo.getValue();

	}

	// JavaASTAnalyzeReplaceMethodCall analyze = new
	// JavaASTAnalyzeReplaceMethodCall( //
	// (AyinalaZestView) part.getObject());
	// try {
	// analyze.analyze(dlgMethodToBeReplacedDetails, dlgNewMethodInfoDetails);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// } else {
	// System.out.println("[DBG] Please open the part descriptor view!!");
	// }
	// }
	// }
}
