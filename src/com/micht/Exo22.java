package com.micht;

import org.junit.Assert;
import org.junit.Test;

public class Exo22 {

	private static final String[] INPUT = new String[] {
			".......##.#..####.#....##",
			"..###....###.####..##.##.",
			"#..####.#....#.#....##...",
			".#....#.#.#....#######...",
			".###..###.#########....##",
			"##...#####..#####.###.#..",
			".#..##.###.#.#....######.",
			".#.##.#..####..#.##.....#",
			"#.#..###..##..#......##.#",
			"##.###.##.#.#...##.#.##..",
			"##...#.######.#..##.#...#",
			"....#.####..#..###.##..##",
			"...#....#.###.#.#..#.....",
			"..###.#.#....#.....#.####",
			".#....##..##.#.#...#.#.#.",
			"...##.#.####.###.##...#.#",
			"##.#.####.#######.##..##.",
			".##...#......####..####.#",
			"#..###.#.###.##.#.#.##..#",
			"#..###.#.#.#.#.#....#.#.#",
			"####.#..##..#.#..#..#.###",
			"##.....#..#.#.#..#.####..",
			"#####.....###.........#..",
			"##...#...####..#####...##",
			".....##.#....##...#.....#"
	};

	private int program1(int nbTurn, String... input) {
		// Init grid
		int length = input.length;
		char[][] grid = new char[length][length];
		for (int i = 0; i < length; i++) {
			grid[i] = input[i].toCharArray();
		}
		int row = length / 2;
		int col = length / 2;

		int nbBurst = 0;
		int dir = 0;
		for (int i = 0; i < nbTurn; i++) {
			char c = grid[row][col];
			if ('#' == c) {
				dir = (dir == 3) ? 0 : dir + 1;
				grid[row][col] = '.';
			} else {
				dir = (dir == 0) ? 3 : dir - 1;
				grid[row][col] = '#';
				nbBurst++;
			}
			switch (dir) {
			case 0:
				row--;
				break;
			case 1:
				col++;
				break;
			case 2:
				row++;
				break;
			case 3:
				col--;
				break;
			}

			if (row < 0 || col < 0 || row >= length || col >= length) {
				grid = grow(grid);
				length += 20;
				row += 10;
				col += 10;
			}
		}

		return nbBurst;
	}

	private char[][] grow(char[][] oldGrid) {
		int length = oldGrid.length + 20;
		char[][] grid = new char[length][length];

		for (int row = 0; row < length; row++) {
			for (int col = 0; col < length; col++) {
				if (row < 10 || col < 10 || row > length - 11 || col > length - 11) {
					grid[row][col] = '.';
				} else {
					grid[row][col] = oldGrid[row - 10][col - 10];
				}
			}
		}

		return grid;
	}

	@Test
	public void example1_0() {
		int ret = program1(3, "..#",
				"#..",
				"...");
		Assert.assertEquals(2, ret);
	}

	@Test
	public void example1_1() {
		int ret = program1(3, ".........",
				".........",
				".........",
				".....#...",
				"...#.....",
				".........",
				".........",
				".........");
		Assert.assertEquals(2, ret);
	}

	@Test
	public void example1_2() {
		int ret = program1(70, ".................",
				".................",
				".................",
				".................",
				".................",
				".................",
				".................",
				".........#.......",
				".......#.........",
				".................",
				".................",
				".................",
				".................",
				".................",
				".................",
				".................");
		Assert.assertEquals(41, ret);
	}

	@Test
	public void test1() {
		int ret = program1(10000, INPUT);
		System.out.println("test1=" + ret);
	}

	private int program2(int nbTurn, String... input) {
		// Init grid
		int length = input.length;
		char[][] grid = new char[length][length];
		for (int i = 0; i < length; i++) {
			grid[i] = input[i].toCharArray();
		}
		int row = length / 2;
		int col = length / 2;

		int nbBurst = 0;
		int dir = 0;
		for (int i = 0; i < nbTurn; i++) {
			char c = grid[row][col];
			if ('#' == c) {
				// infected => flagged
				dir = (dir == 3) ? 0 : dir + 1;
				grid[row][col] = 'F';
			} else if ('F' == c) {
				// flagged => clean
				dir = (dir == 3) ? 0 : dir + 1;
				dir = (dir == 3) ? 0 : dir + 1; // Reverse
				grid[row][col] = '.';
			} else if ('.' == c) {
				// clean => weakened
				dir = (dir == 0) ? 3 : dir - 1;
				grid[row][col] = 'W';
			} else {
				// weakened => infected
				// dir = (dir == 0) ? 3 : dir - 1;
				grid[row][col] = '#';
				nbBurst++;
			}
			switch (dir) {
			case 0:
				row--;
				break;
			case 1:
				col++;
				break;
			case 2:
				row++;
				break;
			case 3:
				col--;
				break;
			}

			if (row < 0 || col < 0 || row >= length || col >= length) {
				grid = grow(grid);
				length += 20;
				row += 10;
				col += 10;
			}
		}

		return nbBurst;
	}

	@Test
	public void example2_1() {
		int ret = program2(100, "..#",
				"#..",
				"...");
		Assert.assertEquals(26, ret);
	}

	@Test
	public void test2() {
		int ret = program2(10000000, INPUT);
		System.out.println("test2=" + ret);
	}
}
