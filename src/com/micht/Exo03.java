package com.micht;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class Exo03 {

	private int program1(int input) {
		double size = Math.ceil(Math.sqrt(input));

		int n = Double.valueOf(size).intValue();
		if (n % 2 == 0)
			n++;

		int xExit = 0, yExit = 0;
		int[][] spiral = new int[n][n];
		int value = n * n;
		int minCol = 0;
		int maxCol = n - 1;
		int minRow = 0;
		int maxRow = n - 1;
		while (value >= 1) {
			for (int i = minCol; i <= maxCol; i++) {
				spiral[minRow][i] = value;
				value--;
			}

			for (int i = minRow + 1; i <= maxRow; i++) {
				spiral[i][maxCol] = value;
				value--;
			}

			for (int i = maxCol - 1; i >= minCol; i--) {
				spiral[maxRow][i] = value;
				value--;
			}

			for (int i = maxRow - 1; i >= minRow + 1; i--) {
				spiral[i][minCol] = value;
				value--;
			}
			if (value == 1) {
				xExit = maxRow - 1;
				yExit = maxCol - 1;
			}

			minCol++;
			minRow++;
			maxCol--;
			maxRow--;
		}

		int distance = 0;
		for (int i = 0; i < spiral.length; i++) {
			for (int j = 0; j < spiral.length; j++) {
				if (input == spiral[i][j]) {
					// Value found
					distance = Math.abs(xExit - i) + Math.abs(yExit - j);
					break;
				}
			}

		}

		return distance;
	}

	@Test
	public void example1_1() {
		int ret = program1(1);
		Assert.assertEquals(0, ret);
	}

	@Test
	public void example1_2() {
		int ret = program1(12);
		Assert.assertEquals(3, ret);
	}

	@Test
	public void example1_3() {
		int ret = program1(23);
		Assert.assertEquals(2, ret);
	}

	@Test
	public void example1_4() {
		int ret = program1(1024);
		Assert.assertEquals(31, ret);
	}

	@Test
	public void test1() {
		int ret = program1(368078);
		System.out.println("test1=" + ret);
	}

	private int program2(int input) {
		double size = Math.ceil(Math.sqrt(input));

		int n = Double.valueOf(size).intValue();
		if (n % 2 == 0)
			n++;
		
		// Create first spiral
		Map<Integer, Coord> coordonates = new HashMap<>(n * n);
		int[][] spiral = new int[n][n];
		int value = n * n;
		int minCol = 0;
		int maxCol = n - 1;
		int minRow = 0;
		int maxRow = n - 1;
		while (value >= 1) {
			for (int i = minCol; i <= maxCol; i++) {
				coordonates.put(value, new Coord(minRow, i));
				spiral[minRow][i] = value;
				value--;
			}

			for (int i = minRow + 1; i <= maxRow; i++) {
				coordonates.put(value, new Coord(i, maxCol));
				spiral[i][maxCol] = value;
				value--;
			}

			for (int i = maxCol - 1; i >= minCol; i--) {
				coordonates.put(value, new Coord(maxRow, i));
				spiral[maxRow][i] = value;
				value--;
			}

			for (int i = maxRow - 1; i >= minRow + 1; i--) {
				coordonates.put(value, new Coord(i, minCol));
				spiral[i][minCol] = value;
				value--;
			}

			minCol++;
			minRow++;
			maxCol--;
			maxRow--;
		}
		
		// Recompute values
		int ret = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				spiral[i][j] = 0;
			}
		}
		for (int i = 1; i <= n * n; i++) {
			Coord c = coordonates.get(i);
			int v = 0;
			if (i == 1) {
				v = 1;
			} else if (i > 1) {
				// Sum
				if (c.x < n - 1) {
					v += spiral[c.x + 1][c.y];
					if (c.y < n - 1) {
						v += spiral[c.x + 1][c.y + 1];
					}
					if (c.y > 0) {
						v += spiral[c.x + 1][c.y - 1];
					}
				}
				if (c.x > 0) {
					v += spiral[c.x - 1][c.y];
					if (c.y < n - 1) {
						v += spiral[c.x - 1][c.y + 1];
					}
					if (c.y > 0) {
						v += spiral[c.x - 1][c.y - 1];
					}
				}
				if (c.y < n - 1) {
					v += spiral[c.x][c.y + 1];
				}
				if (c.y > 0) {
					v += spiral[c.x][c.y - 1];
				}
			}
			spiral[c.x][c.y] = v;
			if (v >= input) {
				ret = v;
				break;
			}
		}

//		for (int i = 0; i < spiral.length; i++) {
//			for (int j = 0; j < spiral.length; j++) {
//				System.out.print(spiral[i][j] + "\t");
//			}
//			System.out.println();
//		}

		return ret;
	}

	@Test
	public void example2_1() {
		int ret = program2(12);
		Assert.assertEquals(23, ret);
	}

	@Test
	public void example2_2() {
		int ret = program2(363);
		Assert.assertEquals(747, ret);
	}

	@Test
	public void test2() {
		int ret = program2(368078);
		System.out.println("test2=" + ret);
	}

	private class Coord {
		public final int x;
		public final int y;

		public Coord(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public String toString() {
			return "Coord [x=" + x + ", y=" + y + "]";
		}

	}
}
