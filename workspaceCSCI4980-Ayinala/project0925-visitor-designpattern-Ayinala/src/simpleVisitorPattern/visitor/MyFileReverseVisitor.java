package simpleVisitorPattern.visitor;

import java.io.File;
import java.util.List;

import simpleVisitorPattern.part.Body;
import simpleVisitorPattern.part.Brake;
import simpleVisitorPattern.part.Engine;
import simpleVisitorPattern.part.Wheel;
import util.UtilFile;

public class MyFileReverseVisitor extends CarPartVisitor {

	private String workDir = System.getProperty("user.dir");
	private List<String> contents;

	public MyFileReverseVisitor() {
		contents = UtilFile.readFile(workDir + File.separator + "inputdata.txt");
	}

	@Override
	public void visit(Wheel part) {
		final int LINE_NUM_WHEEL = 0;
		String[] tokens = contents.get(LINE_NUM_WHEEL).split(",");
		// reverseData = reverse(String[] tokens);
		part.setName(reverse(tokens[0].trim()));
		part.setModelNumberWheel(reverse(tokens[1].trim()));
		part.setModelYearWheel(reverse(tokens[2].trim()));
	}

	@Override
	public void visit(Engine part) {
		final int LINE_NUM_ENGINE = 1;
		String[] tokens = contents.get(LINE_NUM_ENGINE).split(",");
		part.setName(reverse(tokens[0].trim()));
		part.setModelNumberEngine(reverse(tokens[1].trim()));
		part.setModelYearEngine(reverse(tokens[2].trim()));
	}

	@Override
	public void visit(Body part) {
		final int LINE_NUM_BODY = 2;
		String[] tokens = contents.get(LINE_NUM_BODY).split(",");
		part.setName(reverse(tokens[0].trim()));
		part.setModelNumberBody(reverse(tokens[1].trim()));
		part.setModelYearBody(reverse(tokens[2].trim()));
	}

	@Override
	public void visit(Brake part) {
		final int LINE_NUM_BRAKE = 3;
		String[] tokens = contents.get(LINE_NUM_BRAKE).split(",");
		part.setName(reverse(tokens[0].trim()));
		part.setModelNumberBrake(reverse(tokens[1].trim()));
		part.setModelYearBrake(reverse(tokens[2].trim()));
	}

	public String reverse(String token) {
		String rev = "";
		String str = token;
		String keys[] = str.split("\\s+");
		for (String key : keys) {
			String reverseString = new StringBuilder(key).reverse().toString();
			rev = rev + reverseString;
		}
		return rev;
	}

}
