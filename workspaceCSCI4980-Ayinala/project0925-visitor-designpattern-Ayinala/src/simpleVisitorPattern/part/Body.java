package simpleVisitorPattern.part;

import simpleVisitorPattern.visitor.CarPartVisitor;

public class Body implements ICarElement {
   String name;
   String modelNumberBody;
   String modelYearBody;

   public Body(String n) {
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

   public String getModelNumberBody() {
      return modelNumberBody;
   }

   public void setModelNumberBody(String modelNumberBody) {
      this.modelNumberBody = modelNumberBody;
   }

   public String getModelYearBody() {
      return modelYearBody;
   }

   public void setModelYearBody(String modelYearBody) {
      this.modelYearBody = modelYearBody;
   }

}