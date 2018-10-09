package model;

import java.util.ArrayList;
import java.util.List;

public enum ModelProvider {
   INSTANCE;
   private List<ProgramElement> progElements = new ArrayList<ProgramElement>();;

   public void addProgramElements(String pkgName, String className, String methodName, boolean isRetVoid, int parmSize, List<?> parm) {
      progElements.add(new ProgramElement(pkgName, className, methodName, isRetVoid, parmSize, parm));
   }

   public List<ProgramElement> getProgramElements() {
      return progElements;
   }

   public void clearProgramElements() {
      progElements.clear();
   }
}
