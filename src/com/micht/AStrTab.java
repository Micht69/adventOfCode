package com.micht;

import org.junit.Assert;
import org.junit.Test;

public class AStrTab {

	private static final String[] INPUT = new String[] {
	};

	private int program1(String... input) {

		int ret = 0;
		return ret;
	}

	@Test
	public void example1_1() {
		int ret = program1();
		Assert.assertEquals(0, ret);
	}

	@Test
	public void test1() {
		int ret = program1(INPUT);
		System.out.println("test1=" + ret);
	}

//	private int program2(String... input) {
//
//		int ret = 0;
//		return ret;
//	}
//
//	@Test
//	public void example2_1() {
//		int ret = program2();
//		Assert.assertEquals(0, ret);
//	}
//
//	@Test
//	public void test2() {
//		int ret = program2(INPUT);
//		System.out.println("test2=" + ret);
//	}
}
