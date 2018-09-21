
package handler;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.swt.widgets.Shell;

import model.PersonModelProvider;
import view.AddPersonDialog;
import view.MyTableViewer;

public class AddPersonHandler {
   @Inject
   private EPartService epartService;
   @Inject
   @Named(IServiceConstants.ACTIVE_SHELL)
   Shell                shell;

   @Execute
   public void execute() {
      PersonModelProvider personInstance = PersonModelProvider.INSTANCE;
      AddPersonDialog dialog = new AddPersonDialog(shell);
      dialog.open();
      if (dialog.getPerson() != null) {
         personInstance.getPersons().add(dialog.getPerson());
         MPart findPart = epartService.findPart(MyTableViewer.ID);
         Object findPartObj = findPart.getObject();

         if (findPartObj instanceof MyTableViewer) {
            MyTableViewer v = (MyTableViewer) findPartObj;
            v.refresh();
         }
      }
   }
}