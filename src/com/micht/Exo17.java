package com.micht;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class Exo17 {

	private static final int INPUT = 329;


	private int program1(int input, int nbTurn) {
		List<Integer> list = new ArrayList<>();
		list.add(0);

		int curPos = 0;
		for (int n = 1; n <= nbTurn; n++) {
			int nextPos = (curPos + input) % list.size();

			list.add(nextPos + 1, n);

			curPos = nextPos + 1;
		}

		return list.get(curPos + 1);
	}

	@Test
	public void example1_1() {
		int ret = program1(3, 3);
		Assert.assertEquals(1, ret);
	}

	@Test
	public void example1_2() {
		int ret = program1(3, 2017);
		Assert.assertEquals(638, ret);
	}

	@Test
	public void test1() {
		int ret = program1(INPUT, 2017);
		System.out.println("test1=" + ret);
	}


	private int program2(int input) {
		int curPos = 0;
		int listSize = 1;
		int pos1Value = -1;
		for (int n = 1; n <= 50000000; n++) {
			int nextPos = (curPos + input) % listSize;

			if ((nextPos + 1) == 1) {
				pos1Value = n;
			}

			listSize++;
			curPos = nextPos + 1;
		}

		return pos1Value;
	}

//	@Test
//	public void example2_1() {
//		int ret = program2("");
//		Assert.assertEquals(10, ret);
//	}
//
	@Test
	public void test2() {
		int ret = program2(INPUT);
		System.out.println("test2=" + ret);
	}
}
