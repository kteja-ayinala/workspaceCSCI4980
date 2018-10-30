package view;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.services.EMenuService;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

public class SimpleView {
   private StyledText         styledText;
   public static final String ID        = "simplezestproject3.partdescriptor.simpleview";
   public final static String POPUPMENU = "simplezestproject3.popupmenu.mypopupmenu";

   public SimpleView() {
   }

   @PostConstruct
   public void createControls(Composite parent, EMenuService menuService) {
      parent.setLayout(new FillLayout(SWT.HORIZONTAL));
      styledText = new StyledText(parent, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
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
