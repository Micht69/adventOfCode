package com.micht;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class Exo10 {

	private static final String INPUT = "88,88,211,106,141,1,78,254,2,111,77,255,90,0,54,205";


	private int program1(int size, String input) {
		// Init
		List<Integer> list = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			list.add(i);
		}
//		System.out.println("list  IN=" + list);

		int pos = 0, skipSize = 0;
		String[] inputTab = input.split(",");
		for (String s : inputTab) {
			int v = Integer.parseInt(s);

			if (v > 1) {
				List<Integer> subList = new ArrayList<>(v);
				for (int i = 0; i < v; i++) {
					subList.add(list.get((pos + i) % size));
				}
				Collections.reverse(subList);

				for (int i = 0; i < v; i++) {
					list.set((pos + i) % size, subList.get(i));
				}
			}

			pos += v + skipSize;
			if (pos >= size) {
				pos -= size;
			}
			skipSize++;
		}
//		System.out.println("list OUT=" + list);

		return list.get(0).intValue() * list.get(1).intValue();
	}

	@Test
	public void example1_1() {
		int ret = program1(5, "3,4,1,5");
		Assert.assertEquals(12, ret);
	}

	@Test
	public void test1() {
		int ret = program1(256, INPUT);
		System.out.println("test1=" + ret);
	}

	private String program2(String input) {
		// Init
		int size = 256;
		List<Integer> list = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			list.add(i);
		}

		// Input (transfo ascii + suffixe)
		byte[] inputTab = input.getBytes();
		List<Integer> inputList = new ArrayList<>(inputTab.length + 5);
		for (byte b : inputTab) {
			inputList.add((int) b);
		}
		inputList.add(17);
		inputList.add(31);
		inputList.add(73);
		inputList.add(47);
		inputList.add(23);

		int pos = 0, skipSize = 0;
		// 64 rondes
		for (int round = 0; round < 64; round++) {
			// Pour chaque entrée
			for (int v : inputList) {
				if (v > 1) {
					// Récupération sous-liste
					List<Integer> subList = new ArrayList<>(v);
					for (int i = 0; i < v; i++) {
						subList.add(list.get((pos + i) % size));
					}
					Collections.reverse(subList);

					// MaJ des valeurs
					for (int i = 0; i < v; i++) {
						list.set((pos + i) % size, subList.get(i));
					}
				}

				// MaJ position et taille saut
				pos = (pos + v + skipSize) % size;
				skipSize++;
			}
		}

		// Reduce
		String out = "";
		for (int k = 0; k < 16; k++) {
			int v = list.get(16 * k + 0).intValue();
			for (int j = 1; j < 16; j++) {
				v ^= list.get(16 * k + j).intValue();
			}

			String s = Integer.toHexString(v);
			out += (s.length() == 1 ? "0" : "") + s;
		}

		return out;
	}

	@Test
	public void example2_1() {
		String ret = program2("");
		Assert.assertEquals("a2582a3a0e66e6e86e3812dcb672a272", ret);
//		a2582a3a0e66e6e86e3812dcb672a272
//		a584935a8b40d760013615b44fa61360
	}

	@Test
	public void example2_2() {
		String ret = program2("AoC 2017");
		Assert.assertEquals("33efeb34ea91902bb2f59c9920caa6cd", ret);
	}

	@Test
	public void example2_3() {
		String ret = program2("1,2,3");
		Assert.assertEquals("3efbe78a8d82f29979031a4aa0b16a9d", ret);
	}

	@Test
	public void example2_4() {
		String ret = program2("1,2,4");
		Assert.assertEquals("63960835bcdc130f0b66d7ff4f6a5a8e", ret);
	}

	@Test
	public void test2() {
		String ret = program2(INPUT);
		System.out.println("test2=" + ret);
	}
}
