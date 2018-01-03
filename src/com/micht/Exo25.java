package com.micht;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

import org.junit.Assert;
import org.junit.Test;

public class Exo25 {

	private static final String INPUT_FILE = "./src/exo25.txt";

	private class State {
		String code;
		Rule rule0;
		Rule rule1;

		public State(String c) {
			this.code = c;
		}

		@Override
		public String toString() {
			return this.code;
		}
	}

	private class Rule {
		int curValue;
		int newValue;
		String direction;
		String nextState;

		public Rule(int v) {
			this.curValue = v;
		}

		@Override
		public String toString() {
			return "" + curValue + " ==> " + newValue + ", " + direction + ", " + nextState;
		}
	}

	private int currentPosition = 0;
	private String currentState;
	private int nbSteps;
	private Map<String, State> states;
	private int[] values;

	private int program1(String file) throws Exception {
		Scanner scan = new Scanner(new File(file));
		String line;
		int pos1, pos2;

		states = new HashMap<>();

		// init state
		line = scan.nextLine();
		currentState = "A";
		// Nb st
		line = scan.nextLine();
		pos1 = line.indexOf("after ");
		pos2 = line.indexOf(" ", pos1 + 6);
		nbSteps = Integer.parseInt(line.substring(pos1 + 6, pos2));

		values = new int[nbSteps];
		Arrays.fill(values, 0);
		currentPosition = nbSteps / 2;

		try {
			while (true) {
				// Skip line
				line = scan.nextLine();
				// State
				line = scan.nextLine();
				pos1 = line.indexOf("state ") + 6;
				pos2 = line.indexOf(":", pos1);
				State state = new State(line.substring(pos1, pos2));
				states.put(state.code, state);
				
				line = scan.nextLine();
				pos1 = line.indexOf(" is ")+4;
				pos2 = line.indexOf(":", pos1);
				Rule r0 = new Rule(Integer.parseInt(line.substring(pos1, pos2)));
				state.rule0 = r0;
				line = scan.nextLine();
				pos1 = line.indexOf("value ") + 6;
				pos2 = line.indexOf(".", pos1);
				r0.newValue = Integer.parseInt(line.substring(pos1, pos2));
				line = scan.nextLine();
				pos1 = line.indexOf("to the ") + 7;
				pos2 = line.indexOf(".", pos1);
				r0.direction = line.substring(pos1, pos2);
				line = scan.nextLine();
				pos1 = line.indexOf("state ") + 6;
				pos2 = line.indexOf(".", pos1);
				r0.nextState = line.substring(pos1, pos2);

				line = scan.nextLine();
				pos1 = line.indexOf(" is ") + 4;
				pos2 = line.indexOf(":", pos1);
				Rule r1 = new Rule(Integer.parseInt(line.substring(pos1, pos2)));
				state.rule1 = r1;
				line = scan.nextLine();
				pos1 = line.indexOf("value ") + 6;
				pos2 = line.indexOf(".", pos1);
				r1.newValue = Integer.parseInt(line.substring(pos1, pos2));
				line = scan.nextLine();
				pos1 = line.indexOf("to the ") + 7;
				pos2 = line.indexOf(".", pos1);
				r1.direction = line.substring(pos1, pos2);
				line = scan.nextLine();
				pos1 = line.indexOf("state ") + 6;
				pos2 = line.indexOf(".", pos1);
				r1.nextState = line.substring(pos1, pos2);
			}
		} catch (NoSuchElementException nsee) {
			// File end
		}

		for (int turn = 0; turn < nbSteps; turn++) {
			int val = values[currentPosition];
			State state = states.get(currentState);

			Rule r = null;
			if (state.rule0.curValue == val) {
				r = state.rule0;
			} else if (state.rule1.curValue == val) {
				r = state.rule1;
			}

			values[currentPosition] = r.newValue;
			currentState = r.nextState;
			if ("right".equals(r.direction)) {
				currentPosition = (currentPosition + 1) % nbSteps;
			} else {
				currentPosition--;
				if (currentPosition < 0) {
					currentPosition = nbSteps - 1;
				}
			}
		}

		return (int) Arrays.stream(values).filter(i -> i == 1).count();
	}

	@Test
	public void parseInput() throws Exception {
		program1("./src/exo25_test.txt");
		Assert.assertEquals("A", currentState);
		Assert.assertEquals(6, nbSteps);

		State a = states.get("A");
		Assert.assertEquals("A", a.code);
		Assert.assertEquals(0, a.rule0.curValue);
		Assert.assertEquals(1, a.rule0.newValue);
		Assert.assertEquals("right", a.rule0.direction);
		Assert.assertEquals("B", a.rule0.nextState);
		State b = states.get("B");
		Assert.assertEquals("B", b.code);
	}

	@Test
	public void example1_1() throws Exception {
		int ret = program1("./src/exo25_test.txt");
		Assert.assertEquals(3, ret);
	}

	@Test
	public void test1() throws Exception {
		int ret = program1(INPUT_FILE);
		System.out.println("test1=" + ret);
	}


//	private int program2(String input) {
//	}
//
//	@Test
//	public void example2_1() {
//		int ret = program2("");
//		Assert.assertEquals(10, ret);
//	}
//
//	@Test
//	public void test2() {
//		int ret = program2(INPUT);
//		System.out.println("test2=" + ret);
//	}
}
