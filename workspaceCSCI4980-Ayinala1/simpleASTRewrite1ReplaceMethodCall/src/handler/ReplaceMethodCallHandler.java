/**
 */
package handler;

import javax.inject.Inject; 
import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
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

   @Execute
   public void execute(EPartService service) {
      MPart part = service.findPart(ASTRewriteViewer.VIEWID);
      if (part != null) {
         if (part.getObject() instanceof ASTRewriteViewer) {
            JavaASTAnalyzeReplaceMethodCall analyze = new JavaASTAnalyzeReplaceMethodCall( //
                  (ASTRewriteViewer) part.getObject());
            try {
               analyze.analyze();
            } catch (Exception e) {
               e.printStackTrace();
            }
         } else {
            System.out.println("[DBG] Please open the part descriptor view!!");
         }
      }
   }
}