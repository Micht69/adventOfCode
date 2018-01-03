package com.micht;

import org.junit.Assert;
import org.junit.Test;

public class Exo15 {

	private static final int INPUT_A = 618;
	private static final int INPUT_B = 814;

	private String toBinary(int val) {
		String bin = Integer.toBinaryString(val);
		while (bin.length() < 32) {
			bin = "0" + bin;
		}
		return bin;
	}

	private boolean rightEquals(String valA, String valB) {
		Assert.assertEquals("32 bits chain", 32, valA.length());
		Assert.assertEquals("32 bits chain", 32, valB.length());
		return valA.substring(16, 32).equals(valB.substring(16, 32));
	}

//	private class Generator {
//		final int factor;
//		int value;
//
//		public Generator(int factor, int startValue) {
//			super();
//			this.factor = factor;
//			this.value = startValue;
//		}
//
//		public int getNextValue() {
//			long tmp = (this.value * 1l) * (this.factor * 1l);
//			this.value = (int) (tmp % 2147483647);
//			return this.value;
//		}
//	}
//
//	private int program1(int inputA, int inputB) {
//		Generator genA = new Generator(16807, inputA);
//		Generator genB = new Generator(48271, inputB);
//
//		int count = 0;
//		for (int i = 0; i < 40000000; i++) {
//			String valA = toBinary(genA.getNextValue());
//			String valB = toBinary(genB.getNextValue());
//			if (rightEquals(valA, valB)) {
//				count++;
//			}
//		}
//
//		return count;
//	}
//
//	@Test
//	public void getNextValueATest() {
//		Generator g = new Generator(16807, 65);
//
//		int ret = g.getNextValue();
//		Assert.assertEquals(1092455, ret);
//		ret = g.getNextValue();
//		Assert.assertEquals(1181022009, ret);
//		ret = g.getNextValue();
//		Assert.assertEquals(245556042, ret);
//		ret = g.getNextValue();
//		Assert.assertEquals(1744312007, ret);
//		ret = g.getNextValue();
//		Assert.assertEquals(1352636452, ret);
//	}
//
//	@Test
//	public void getNextValueBTest() {
//		Generator g = new Generator(48271, 8921);
//
//		int ret = g.getNextValue();
//		Assert.assertEquals(430625591, ret);
//		ret = g.getNextValue();
//		Assert.assertEquals(1233683848, ret);
//		ret = g.getNextValue();
//		Assert.assertEquals(1431495498, ret);
//		ret = g.getNextValue();
//		Assert.assertEquals(137874439, ret);
//		ret = g.getNextValue();
//		Assert.assertEquals(285222916, ret);
//	}
//
//	@Test
//	public void toBinaryTest() {
//		String ret = toBinary(1092455);
//		Assert.assertEquals("00000000000100001010101101100111", ret);
//	}
//
//	@Test
//	public void rightEqualsTest() {
//		Assert.assertFalse(rightEquals("00000000000100001010101101100111", "00011001101010101101001100110111"));
//		Assert.assertTrue(rightEquals("00001110101000101110001101001010", "01010101010100101110001101001010"));
//	}
//
//	@Test
//	public void example1_1() {
//		int ret = program1(65, 8921);
//		Assert.assertEquals(588, ret);
//	}
//
//	@Test
//	public void test1() {
//		int ret = program1(INPUT_A, INPUT_B);
//		System.out.println("test1=" + ret);
//	}

	private class Generator2 {
		final int factor;
		int value;
		final int cond;

		public Generator2(int factor, int startValue, int cond) {
			super();
			this.factor = factor;
			this.value = startValue;
			this.cond = cond;
		}

		public int getNextValue() {
			boolean found = false;
			while (!found) {
				long tmp = (this.value * 1l) * (this.factor * 1l);
				this.value = (int) (tmp % 2147483647);
				found = (this.value % this.cond) == 0;
			}
			return this.value;
		}
	}

	private int program2(int inputA, int inputB) {
		Generator2 genA = new Generator2(16807, inputA, 4);
		Generator2 genB = new Generator2(48271, inputB, 8);

		int count = 0;
		for (int i = 0; i < 5000000; i++) {
			String valA = toBinary(genA.getNextValue());
			String valB = toBinary(genB.getNextValue());
			if (rightEquals(valA, valB)) {
				count++;
			}
		}

		return count;
	}

	@Test
	public void getNextValueAWithCondTest() {
		Generator2 g = new Generator2(16807, 65, 4);

		int ret = g.getNextValue();
		Assert.assertEquals(1352636452, ret);
		ret = g.getNextValue();
		Assert.assertEquals(1992081072, ret);
		ret = g.getNextValue();
		Assert.assertEquals(530830436, ret);
		ret = g.getNextValue();
		Assert.assertEquals(1980017072, ret);
		ret = g.getNextValue();
		Assert.assertEquals(740335192, ret);
	}

	@Test
	public void example2_1() {
		int ret = program2(65, 8921);
		Assert.assertEquals(309, ret);
	}

	@Test
	public void test2() {
		int ret = program2(INPUT_A, INPUT_B);
		System.out.println("test2=" + ret);
	}
}
