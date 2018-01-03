package com.micht;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class Exo06 {

	private static final String INPUT = "4	1	15	12	0	9	9	5	5	8	7	3	14	5	12	3";

	private class Bank {
		int count;

		public Bank(int count) {
			this.count = count;
		}

		@Override
		public String toString() {
			return "" + count + "";
		}
	}

	private String toString(Bank[] banks) {
		StringBuilder sb = new StringBuilder();
		for (Bank bank : banks) {
			if (sb.length() > 0)
				sb.append(" ");
			sb.append(bank.count);
		}
		return sb.toString();
	}

	private int program1(String input) {
		// Init banks
		String[] inputTab = input.split("\t");
		Bank[] banks = new Bank[inputTab.length];
		for (int i=0; i<inputTab.length; i++) {
			banks[i] = new Bank(Integer.parseInt(inputTab[i]));
		}

		List<String> states = new ArrayList<>();
		states.add(toString(banks));
		int steps = 0;
		while (true) {
			steps++;
			
			// Max bank
			int max = -1, idx = -1;
			for (int i = 0; i < banks.length; i++) {
				Bank bank = banks[i];
				if (bank.count > max) {
					max = bank.count;
					idx = i;
				}
			}

			// Realloc
			banks[idx].count = 0;
			while (max > 0) {
				idx++;
				if (idx >= banks.length)
					idx = 0;

				banks[idx].count++;

				max--;
			}

			String banksStr = toString(banks);
			if (states.contains(banksStr)) {
				break;
			}
			states.add(banksStr);
		}
		return steps;
	}

	@Test
	public void example1_1() {
		int ret = program1("0	2	7	0");
		Assert.assertEquals(5, ret);
	}

	@Test
	public void test1() {
		int ret = program1(INPUT);
		System.out.println("test1=" + ret);
	}

	private int program2(String input) {
		// Init banks
		String[] inputTab = input.split("\t");
		Bank[] banks = new Bank[inputTab.length];
		for (int i = 0; i < inputTab.length; i++) {
			banks[i] = new Bank(Integer.parseInt(inputTab[i]));
		}

		List<String> states = new LinkedList<>();
		states.add(toString(banks));
		int steps = 0;
		while (true) {
			steps++;

			// Max bank
			int max = -1, idx = -1;
			for (int i = 0; i < banks.length; i++) {
				Bank bank = banks[i];
				if (bank.count > max) {
					max = bank.count;
					idx = i;
				}
			}

			// Realloc
			banks[idx].count = 0;
			while (max > 0) {
				idx++;
				if (idx >= banks.length)
					idx = 0;

				banks[idx].count++;

				max--;
			}

			String banksStr = toString(banks);
			if (states.contains(banksStr)) {
				int idxState = states.indexOf(banksStr);
				steps -= idxState;
				break;
			}

			// Add new
			states.add(banksStr);
		}
		return steps;
	}

	@Test
	public void example2_1() {
		int ret = program2("0	2	7	0");
		Assert.assertEquals(4, ret);
	}

	@Test
	public void test2() {
		int ret = program2(INPUT);
		System.out.println("test2=" + ret);
	}
}
