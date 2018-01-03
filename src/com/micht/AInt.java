package com.micht;

import org.junit.Assert;
import org.junit.Test;

public class AInt {

	private static final int INPUT = 0;


	private int program1(int input) {

		return 0;
	}

	@Test
	public void example1_1() {
		int ret = program1(0);
		Assert.assertEquals(24, ret);
	}

	@Test
	public void test1() {
		int ret = program1(INPUT);
		System.out.println("test1=" + ret);
	}


//	private int program2(int input) {
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
