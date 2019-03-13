/**
 */
package view;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

/**
 * @since JavaSE-1.8
 */
public class ASTRewriteViewer {
   public final static String VIEWID = "simpleastrewriteproject.partdescriptor.astrewritingview.ex";

   private StyledText         styledText;

   public ASTRewriteViewer() {
   }

   /**
    * Create contents of the view part.
    */
   @PostConstruct
   public void createControls(Composite parent) {
      Composite composite = new Composite(parent, SWT.NONE);
      composite.setLayout(new FillLayout(SWT.HORIZONTAL));
      styledText = new StyledText(composite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
   }

   public void appendText(String s) {
      this.styledText.append(s);
   }

   public void reset() {
      this.styledText.setText("");
   }

   @PreDestroy
   public void dispose() {
   }

   @Focus
   public void setFocus() {
      this.styledText.setFocus();
   }

}
