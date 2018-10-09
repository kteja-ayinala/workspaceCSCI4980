package model.editing;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.TableViewer;

import analysis.ProjectAnalyzer;
import analysis.ReplaceMethodNameAnalyzer;
import model.ProgramElement;

public class MethodNameEditingSupport extends ProgElemEditingSupport {

   public MethodNameEditingSupport(TableViewer viewer) {
      super(viewer);
   }

   @Override
   protected Object getValue(Object element) {
      return ((ProgramElement) element).getMethodName();
   }

   @Override
   protected void setValue(Object element, Object value) {
      try {
         ProgramElement p = (ProgramElement) element;
         ProjectAnalyzer analyzer = new ReplaceMethodNameAnalyzer(p, String.valueOf(value));
         analyzer.analyze();
         p.setMethodName((String.valueOf(value)));
         this.viewer.update(element, null);
         refreshViewer();
      } catch (CoreException e) {
         e.printStackTrace();
      }
   }
}
