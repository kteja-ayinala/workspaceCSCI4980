package simpleVisitorPattern.visitor;

import simpleVisitorPattern.part.Body;
import simpleVisitorPattern.part.Brake;
import simpleVisitorPattern.part.Engine;
import simpleVisitorPattern.part.Wheel;

public abstract class CarPartVisitor {
	public abstract void visit(Wheel wheel);

	public abstract void visit(Engine engine);

	public abstract void visit(Body body);

	public abstract void visit(Brake brake);
}