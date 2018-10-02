package util;

import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleConstants;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.IConsoleView;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

public class UtilConsole {
   private static MessageConsole findConsole(String name) {
      ConsolePlugin plugin = ConsolePlugin.getDefault();
      IConsoleManager conMan = plugin.getConsoleManager();
      IConsole[] existing = conMan.getConsoles();
      for (int i = 0; i < existing.length; i++)
         if (name.equals(existing[i].getName()))
            return (MessageConsole) existing[i];
      //no console found, so create a new one
      MessageConsole myConsole = new MessageConsole(name, null);
      conMan.addConsoles(new IConsole[] { myConsole });
      return myConsole;
   }

   public static void print(String msg) {
      IConsole myConsole = findConsole("My Console"); // your console instance
      IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
      String id = IConsoleConstants.ID_CONSOLE_VIEW;
      IConsoleView view = null;
      try {
         view = (IConsoleView) page.showView(id);
      } catch (PartInitException e) {
         e.printStackTrace();
      }
      view.display(myConsole);
      MessageConsoleStream out = ((MessageConsole) myConsole).newMessageStream();
      out.println(msg);
   }
}
