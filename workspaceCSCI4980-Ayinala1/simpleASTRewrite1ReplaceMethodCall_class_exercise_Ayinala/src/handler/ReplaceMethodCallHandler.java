/**
 */
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
import view.ASTRewriteViewer;

/**
 * @since JavaSE-1.8
 */
public class ReplaceMethodCallHandler {
   @Inject
   @Named(IServiceConstants.ACTIVE_SHELL)
   Shell shell;
   String[] dlgMethodToBeReplacedDetails;
   String[] dlgNewMethodInfoDetails;

   @Execute
   public void execute(EPartService service) {
      // TODO Class Exercise
      InputDialog dlgMethodToBeReplaced = new InputDialog(shell, "", "Enter method name to be replaced:", "", null);
      if (dlgMethodToBeReplaced.open() == Window.OK) {
         System.out.println(dlgMethodToBeReplaced.getValue());
         
         InputDialog dlgNewMethodInfo = new InputDialog(shell, "", "Enter new method name:", "", null);
         if (dlgNewMethodInfo.open() == Window.OK) {
            System.out.println(dlgNewMethodInfo.getValue());
         }
          dlgMethodToBeReplacedDetails = dlgMethodToBeReplaced.getValue().split("\\.");
         dlgNewMethodInfoDetails = dlgNewMethodInfo.getValue().split("\\.");
      }

      MPart part = service.findPart(ASTRewriteViewer.VIEWID);
      if (part != null) {
         if (part.getObject() instanceof ASTRewriteViewer) {
            JavaASTAnalyzeReplaceMethodCall analyze = new JavaASTAnalyzeReplaceMethodCall( //
                  (ASTRewriteViewer) part.getObject());
            try {
               analyze.analyze(dlgMethodToBeReplacedDetails, dlgNewMethodInfoDetails );
            } catch (Exception e) {
               e.printStackTrace();
            }
         } else {
            System.out.println("[DBG] Please open the part descriptor view!!");
         }
      }
   }
}