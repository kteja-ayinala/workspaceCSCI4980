package simpleVisitorPattern.part;

import simpleVisitorPattern.visitor.CarPartVisitor;

public class Wheel implements ICarElement {
   String name;
   String modelNumberWheel;
   String modelYearWheel;

   public Wheel(String n) {
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

   public String getModelNumberWheel() {
      return modelNumberWheel;
   }

   public void setModelNumberWheel(String modelNumberWheel) {
      this.modelNumberWheel = modelNumberWheel;
   }

   public String getModelYearWheel() {
      return modelYearWheel;
   }

   public void setModelYearWheel(String modelYearWheel) {
      this.modelYearWheel = modelYearWheel;
   }
}