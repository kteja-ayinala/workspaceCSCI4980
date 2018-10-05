package model.editing;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.ViewerCell;

import analysis.ProjectAnalyzer;
import model.ModelProvider;

public class ProgElemEditingSupport extends EditingSupport {
   protected TableViewer viewer;
   protected TextCellEditor editor;

   public ProgElemEditingSupport(TableViewer viewer) {
      super(viewer);
      this.viewer = viewer;
      this.editor = new TextCellEditor(viewer.getTable());
   }

   @Override
   protected CellEditor getCellEditor(Object element) {
      return this.editor;
   }

   @Override
   protected boolean canEdit(Object element) {
      return true;
   }

   @Override
   protected Object getValue(Object element) {
      return element;
   }

   @Override
   protected void setValue(Object element, Object value) {
   }

   @Override
   protected void saveCellEditorValue(CellEditor cellEditor, ViewerCell cell) {
      super.saveCellEditorValue(cellEditor, cell);
   }

   void refreshViewer() throws CoreException {
      ModelProvider.INSTANCE.clearProgramElements();
      ProjectAnalyzer analyzer = new ProjectAnalyzer();
      analyzer.analyze();
      this.viewer.setInput(ModelProvider.INSTANCE.getProgramElements());
      this.viewer.refresh();
   }
}
