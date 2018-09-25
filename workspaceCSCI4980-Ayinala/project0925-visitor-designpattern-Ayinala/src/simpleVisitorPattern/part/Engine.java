package simpleVisitorPattern.part;

import simpleVisitorPattern.visitor.CarPartVisitor;

public class Engine implements ICarElement {
   String name;
   String modelNumberEngine;
   String modelYearEngine;

   public Engine(String n) {
      this.name = n;
   }

   public void accept(CarPartVisitor visitor) {
      visitor.visit(this);
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getModelNumberEngine() {
      return modelNumberEngine;
   }

   public void setModelNumberEngine(String modelNumberEngine) {
      this.modelNumberEngine = modelNumberEngine;
   }

   public String getModelYearEngine() {
      return modelYearEngine;
   }

   public void setModelYearEngine(String modelYearEngine) {
      this.modelYearEngine = modelYearEngine;
   }
}