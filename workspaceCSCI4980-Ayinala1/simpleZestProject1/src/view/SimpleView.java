package view;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.services.EMenuService;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;

public class SimpleView {
   StyledText styledText;
   public static final String ID = "simplezestproject1.partdescriptor.simpleview";
   public final static String POPUPMENU = "simplezestproject1.popupmenu.mypopupmenu";

   @PostConstruct
   public void createControls(Composite parent, EMenuService menuService) {
      parent.setLayout(new FillLayout(SWT.HORIZONTAL));
      styledText = new StyledText(parent, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
      // register context menu on the table
      menuService.registerContextMenu(styledText, POPUPMENU);
   }

   public void appendText(String s) {
      this.styledText.append(s);
   }

   @PreDestroy
   public void dispose() {
   }

   @Focus
   public void setFocus() {
      this.styledText.setFocus();
   }

   public void clearText() {
      this.styledText.setText("");
   }
}
