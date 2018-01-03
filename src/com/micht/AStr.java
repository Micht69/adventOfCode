package com.micht;

import org.junit.Assert;
import org.junit.Test;

public class AStr {

	private static final String INPUT = "hfdlxzhv";


	private int program1(String input) {

		return 0;
	}

	@Test
	public void example1_1() {
		int ret = program1("");
		Assert.assertEquals(24, ret);
	}

	@Test
	public void test1() {
		int ret = program1(INPUT);
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
