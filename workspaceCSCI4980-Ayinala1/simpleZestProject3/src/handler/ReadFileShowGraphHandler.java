
package handler;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.workbench.modeling.EPartService;

import graph.builder.GModelBuilder;
import util.UtilMsg;
import view.SimpleZestView;

public class ReadFileShowGraphHandler {
   @Inject
   EPartService service;

   @Execute
   public void execute() {
      SimpleZestView zestView = (SimpleZestView) service.findPart(SimpleZestView.SIMPLEZESTVIEW_ID).getObject();
      System.out.println("TODO: Class Exercise");
      UtilMsg.openWarning("TODO: Class Exercise");
      //TODO: Class Exercise
      //zestView.update(/* Your Answer */);
   }
}