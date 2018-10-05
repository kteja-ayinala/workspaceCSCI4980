package util;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

public class MsgUtil {
   @Inject
   @Named(IServiceConstants.ACTIVE_SHELL)
   static Shell shell;

   public static boolean openQuestion(String msg) {
      return MessageDialog.openQuestion(shell, "Question", msg);
   }

   public static void openWarning(String msg) {
      MessageDialog.openWarning(shell, "Warning", msg);
   }
}
