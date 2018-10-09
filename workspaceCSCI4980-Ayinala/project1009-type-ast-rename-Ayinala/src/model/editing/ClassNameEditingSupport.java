package model.editing;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.TableViewer;

import analysis.ReplaceClassNameAnalyzer;
import model.ProgramElement;

public class ClassNameEditingSupport extends ProgElemEditingSupport {

   public ClassNameEditingSupport(TableViewer viewer) {
      super(viewer);
   }

   @Override
   protected Object getValue(Object element) {
      return ((ProgramElement) element).getClassName();
   }

   @Override
   protected void setValue(Object element, Object value) {
      try {
         ProgramElement p = (ProgramElement) element;
         ReplaceClassNameAnalyzer analyzer = new ReplaceClassNameAnalyzer(p, String.valueOf(value));
         analyzer.analyze();
         p.setClassName((String.valueOf(value)));
         this.viewer.update(element, null);
         refreshViewer();
      } catch (CoreException e) {
         e.printStackTrace();
      }
   }
}
