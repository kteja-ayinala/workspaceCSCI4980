
package handler;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Execute;

import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;

import view.SimpleView20180913Q1KrishnaTejaAyinala;

public class Reverse {
	@Inject
	private EPartService epartService;

	@Execute
	public void execute() {
		System.out.println("[DBG] Reverse");
		MPart findPart = epartService.findPart(SimpleView20180913Q1KrishnaTejaAyinala.VIEW_ID);
		Object findPartObj = findPart.getObject();

		if (findPartObj instanceof SimpleView20180913Q1KrishnaTejaAyinala) {
			SimpleView20180913Q1KrishnaTejaAyinala v = (SimpleView20180913Q1KrishnaTejaAyinala) findPartObj;
			v.reverseText();
		}
	}

}