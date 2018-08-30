
package handler;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;

import viewer.ViewPartDescriptor;

public class ClearPartDescriptorHanler {
	@Inject
	private EPartService epartService;

	@Execute
	public void execute() {
		MPart findPart = epartService.findPart(ViewPartDescriptor.VIEW_ID);
		Object findPartObj = findPart.getObject();
		

		if (findPartObj instanceof ViewPartDescriptor) {
			ViewPartDescriptor v = (ViewPartDescriptor) findPartObj;
			clear(v);
		}
	}

	private void clear(ViewPartDescriptor v) {
		v.clear();
	}

}