package simpleVisitorPattern.part;

import simpleVisitorPattern.visitor.CarPartVisitor;

public interface ICarElement {
	void accept(CarPartVisitor visitor);
}
