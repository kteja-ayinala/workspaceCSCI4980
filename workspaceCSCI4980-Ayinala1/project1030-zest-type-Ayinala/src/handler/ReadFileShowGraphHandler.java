
package handler;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.workbench.modeling.EPartService;

import graph.builder.GModelBuilder;
import util.UtilMsg;
import view.SimpleZestType1030Ayinala;

public class ReadFileShowGraphHandler {
	@Inject
	EPartService service;

	@Execute
	public void execute() {
		SimpleZestType1030Ayinala zestView = (SimpleZestType1030Ayinala) service.findPart(SimpleZestType1030Ayinala.SIMPLEZESTVIEW_ID).getObject();

		// TODO: Class Exercise
		// zestView.update(/* Your Answer */);
		GModelBuilder.instance().buildByGraphInfoFile();
		zestView.update(GModelBuilder.instance().getNodes());

	}
}