package simpleVisitorPattern.visitor;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import simpleVisitorPattern.part.Body;
import simpleVisitorPattern.part.Brake;
import simpleVisitorPattern.part.Engine;
import simpleVisitorPattern.part.Wheel;

public class MyFileSaveVisitor extends CarPartVisitor {

	String file = "/Users/krishnatejaayinala/Desktop/outputdata.csv";
	String stmnts = "";

	public MyFileSaveVisitor() {

	}

	@Override
	public void visit(Wheel part) {
		String tokens = part.getName() + "\t " + part.getModelNumberWheel() + "\t" + part.getModelYearWheel();
		stmnts = stmnts + tokens + "\n";
	}

	@Override
	public void visit(Engine part) {
		String tokens = part.getName() + "\t" + part.getModelNumberEngine() + " \t" + part.getModelYearEngine();
		stmnts = stmnts + tokens + "\n";
	}

	@Override
	public void visit(Body part) {
		String tokens = part.getName() + "\t " + part.getModelNumberBody() + "\t " + part.getModelYearBody();
		stmnts = stmnts + tokens + "\n";
	}

	@Override
	public void visit(Brake part) {
		String tokens = part.getName() + "\t " + part.getModelNumberBrake() + "\t " + part.getModelYearBrake();
		stmnts = stmnts + tokens + "\n";
	}

	public void saveToFile() {
		FileWriter fileWriter;
		try {
			fileWriter = new FileWriter(file);
			PrintWriter printWriter = new PrintWriter(fileWriter);
			printWriter.println(stmnts);
			printWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
