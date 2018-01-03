package com.micht;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class Exo14 {

	private static final String INPUT = "hfdlxzhv";

	private String hash(String input) {
		// Init
		int size = 256;
		List<Integer> list = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			list.add(i);
		}

		// Input (transfo ascii + suffixe)
		byte[] inputTab = input.getBytes();
		List<Integer> inputList = new ArrayList<>(inputTab.length);
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

	private String toBinary(String rowHash) {
		StringBuilder binary = new StringBuilder();
		for (int c = 0; c < rowHash.length(); c++) {
			String str = rowHash.substring(c, c + 1);
			int val = Integer.parseInt(str, 16);
			String bin = Integer.toBinaryString(val);
			while (bin.length() < 4) {
				bin = "0" + bin;
			}
			binary.append(bin);
		}
		return binary.toString();
	}

	private int program1(String input) {
		int[][] grid = new int[128][128];

		int nbUsed = 0;
		for (int row = 0; row < 128; row++) {
			String rowBin = toBinary(hash(input + "-" + row));
			Assert.assertEquals("Length error on line " + row, 128, rowBin.length());
			for (int c = 0; c < rowBin.length(); c++) {
				int used = Integer.parseInt(rowBin.substring(c, c + 1));
				grid[row][c] = used;
				if (used == 1) {
					nbUsed++;
				}
			}
		}
//		for (int row = 0; row < 128; row++) {
//			for (int col = 0; col < 128; col++) {
//				System.out.print(grid[row][col] == 1 ? "#" : ".");
//			}
//			System.out.println();
//		}

		return nbUsed;
	}

	@Test
	public void toBinaryTestZero() {
		String ret = toBinary("0");
		Assert.assertEquals("0000", ret);
	}

	@Test
	public void toBinaryTestF() {
		String ret = toBinary("f");
		Assert.assertEquals("1111", ret);
	}

	@Test
	public void toBinaryTest() {
		String ret = toBinary("a0c2017");
		Assert.assertEquals("1010000011000010000000010111", ret);
	}

	@Test
	public void binHashRow0Test() {
		String rowBin = toBinary(hash("flqrgnkx-0"));
		Assert.assertEquals("11010100", rowBin.substring(0, 8));
	}

	@Test
	public void binHashRow1Test() {
		String rowBin = toBinary(hash("flqrgnkx-1"));
		Assert.assertEquals("01010101", rowBin.substring(0, 8));
	}

	@Test
	public void binHashRow2Test() {
		String rowBin = toBinary(hash("flqrgnkx-2"));
		Assert.assertEquals("00001010", rowBin.substring(0, 8));
	}

	@Test
	public void binHashRow3Test() {
		String rowBin = toBinary(hash("flqrgnkx-3"));
		Assert.assertEquals("10101101", rowBin.substring(0, 8));
	}

	@Test
	public void binHashRow4Test() {
		String rowBin = toBinary(hash("flqrgnkx-4"));
		Assert.assertEquals("01101000", rowBin.substring(0, 8));
	}

	@Test
	public void binHashRow7Test() {
		String rowBin = toBinary(hash("flqrgnkx-7"));
		Assert.assertEquals("11010110", rowBin.substring(0, 8));
	}

	@Test
	public void example1_1() {
		int ret = program1("flqrgnkx");
		Assert.assertEquals(8108, ret);
	}

	@Test
	public void test1() {
		int ret = program1(INPUT);
		System.out.println("test1=" + ret);
	}

	int nbRegion = 0;
	private int program2(String input) {
		int[][] grid = new int[128][128];

		for (int row = 0; row < 128; row++) {
			String rowBin = toBinary(hash(input + "-" + row));
			Assert.assertEquals("Length error on line " + row, 128, rowBin.length());
			for (int c = 0; c < rowBin.length(); c++) {
				int used = Integer.parseInt(rowBin.substring(c, c + 1));
				grid[row][c] = used;
			}
		}

		int[][] regions = new int[128][128];
		for (int row = 0; row < 128; row++) {
			for (int col = 0; col < 128; col++) {
				regions[row][col] = -1;
			}
		}

		for (int row = 0; row < 128; row++) {
			for (int col = 0; col < 128; col++) {
				checkRegion(grid, regions, row, col);
			}
		}

		return nbRegion;
	}

	private void checkRegion(int[][] grid, int[][] regions, int row, int col) {
		if (grid[row][col] == 0) {
			regions[row][col] = 0;
			return;
		}
		if (regions[row][col] > -1) {
			// Already check
			return;
		}

		// Get connected region
		int reg = -1;
		if (row > 0) {
			// Check top
			if (grid[row - 1][col] == 1) {
				reg = regions[row - 1][col];
			}
		}
		if (reg == -1 && row < grid.length - 1) {
			// Check bottom
			if (grid[row + 1][col] == 1) {
				reg = regions[row + 1][col];
			}
		}
		if (reg == -1 && col > 0) {
			// Check left
			if (grid[row][col - 1] == 1) {
				reg = regions[row][col - 1];
			}
		}
		if (reg == -1 && col < grid.length - 1) {
			// Check right
			if (grid[row][col + 1] == 1) {
				reg = regions[row][col + 1];
			}
		}

		if (reg == -1) {
			// New region
			nbRegion++;
			reg = nbRegion;
		}
		regions[row][col] = reg;

		// Parse linked cols
		if (row > 0) {
			// Check top
			checkRegion(grid, regions, row - 1, col);
		}
		if (row < grid.length - 1) {
			// Check bottom
			checkRegion(grid, regions, row + 1, col);
		}
		if (col > 0) {
			// Check left
			checkRegion(grid, regions, row, col - 1);
		}
		if (col < grid.length - 1) {
			// Check right
			checkRegion(grid, regions, row, col + 1);
		}
	}

	@Test
	public void example2_1() {
		int ret = program2("flqrgnkx");
		Assert.assertEquals(1242, ret);
	}

	@Test
	public void checkRegionTest() {
		int[][] grid = new int[][] {
				{ 0, 1, 0, 1, 0 },
				{ 1, 1, 0, 1, 0 },
				{ 0, 0, 0, 0, 1 },
				{ 0, 0, 0, 1, 1 },
				{ 4, 1, 0, 1, 0 },
		};
		int[][] regions = new int[][] {
				{ 0, -1, -1, -1, -1 },
				{ -1, -1, -1, -1, -1 },
				{ -1, -1, -1, -1, -1 },
				{ -1, -1, -1, -1, -1 },
				{ -1, -1, -1, -1, -1 },
		};
		;

		checkRegion(grid, regions, 0, 1);
		Assert.assertEquals(1, regions[1][0]);
	}

	@Test
	public void test2() {
		int ret = program2(INPUT);
		System.out.println("test2=" + ret);
	}
}
