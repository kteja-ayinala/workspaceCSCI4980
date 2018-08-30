 
package handler;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;

import viewer.SimpleView;


public class ViewPopupHandler {
	@Inject
	private EPartService epartService;

	@Execute
	public void execute() {
		System.out.println("[DBG] ViewPopupHandler");
		MPart findPart = epartService.findPart(SimpleView.VIEW_ID);
		Object findPartObj = findPart.getObject();

		if (findPartObj instanceof SimpleView) {
			SimpleView v = (SimpleView) findPartObj;
			v.appendText("Hello World!");
		}
	}
		
}