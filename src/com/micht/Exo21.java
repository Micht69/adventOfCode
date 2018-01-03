package com.micht;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;

public class Exo21 {

	private static final String[] INPUT = new String[] {
			"../.. => .#./###/##.",
			"#./.. => ..#/.#./#.#",
			"##/.. => ###/#../...",
			".#/#. => .#./..#/##.",
			"##/#. => ..#/#.#/###",
			"##/## => .##/.##/.#.",
			".../.../... => #.#./..##/##../###.",
			"#../.../... => .###/.##./.##./....",
			".#./.../... => ####/..../..../.#.#",
			"##./.../... => #.../#..#/#.../###.",
			"#.#/.../... => ..##/###./..#./.#..",
			"###/.../... => #.../#.#./..#./#.#.",
			".#./#../... => #.#./..#./.#../...#",
			"##./#../... => ###./.###/#.##/.#..",
			"..#/#../... => .##./.##./####/####",
			"#.#/#../... => ..##/.#.#/##../#.##",
			".##/#../... => ...#/..##/...#/#...",
			"###/#../... => ..../##.#/..#./###.",
			".../.#./... => ###./..##/#..#/#.#.",
			"#../.#./... => #..#/..#./#.##/#..#",
			".#./.#./... => ##.#/..../...#/....",
			"##./.#./... => #.#./.##./.###/####",
			"#.#/.#./... => ####/.##./.#../##.#",
			"###/.#./... => #.##/..../.#.#/.##.",
			".#./##./... => ##.#/#.##/#.##/..##",
			"##./##./... => .###/..../#.../..#.",
			"..#/##./... => ..../.#../..#./##..",
			"#.#/##./... => #.##/##../..##/.#.#",
			".##/##./... => ..../..#./#..#/....",
			"###/##./... => #..#/#.##/##.#/..##",
			".../#.#/... => ..../#.#./.##./.#.#",
			"#../#.#/... => .###/.#.#/#.#./..#.",
			".#./#.#/... => ####/#.../.#../.##.",
			"##./#.#/... => ..##/..#./.#.#/#.#.",
			"#.#/#.#/... => #.##/##../##../#..#",
			"###/#.#/... => .###/.##./.##./.#.#",
			".../###/... => ##.#/..##/...#/..##",
			"#../###/... => ..##/####/..#./.###",
			".#./###/... => #.##/#.##/.##./..##",
			"##./###/... => #.../.#.#/####/..##",
			"#.#/###/... => #.../.###/..../.###",
			"###/###/... => .##./####/##../..#.",
			"..#/.../#.. => #..#/.###/.#.#/##.#",
			"#.#/.../#.. => ###./.##./.##./##..",
			".##/.../#.. => .###/.#../...#/.#.#",
			"###/.../#.. => ###./..##/..##/.#.#",
			".##/#../#.. => ##.#/...#/####/#.##",
			"###/#../#.. => .#.#/...#/.###/#..#",
			"..#/.#./#.. => #.#./.###/##../#...",
			"#.#/.#./#.. => ####/..#./.###/##..",
			".##/.#./#.. => #.#./##../..../#.#.",
			"###/.#./#.. => .#.#/#.#./#.../#.#.",
			".##/##./#.. => ##../.#../...#/..#.",
			"###/##./#.. => ##../#.../.###/..#.",
			"#../..#/#.. => ##../####/##.#/#.##",
			".#./..#/#.. => #..#/..../..#./#...",
			"##./..#/#.. => ..#./..##/#.##/#.##",
			"#.#/..#/#.. => #.##/..#./.#.#/.#..",
			".##/..#/#.. => ###./##../.#.#/##..",
			"###/..#/#.. => #.#./.#.#/.#.#/#..#",
			"#../#.#/#.. => #..#/.#.#/####/.#.#",
			".#./#.#/#.. => #.../#.##/#.../#.#.",
			"##./#.#/#.. => .##./.#../.#.#/..#.",
			"..#/#.#/#.. => ##.#/.###/#..#/#...",
			"#.#/#.#/#.. => .#.#/.###/#..#/.#..",
			".##/#.#/#.. => ..#./####/.#../...#",
			"###/#.#/#.. => .###/.#../.##./.#.#",
			"#../.##/#.. => ..##/##.#/#.#./.###",
			".#./.##/#.. => ####/.##./..../.##.",
			"##./.##/#.. => ...#/##../..##/..##",
			"#.#/.##/#.. => .###/##.#/.###/..#.",
			".##/.##/#.. => ..#./##../..##/...#",
			"###/.##/#.. => ###./.#.#/.###/.###",
			"#../###/#.. => .##./##.#/##.#/..#.",
			".#./###/#.. => ...#/...#/##.#/#.##",
			"##./###/#.. => .#../.#.#/.#.#/..#.",
			"..#/###/#.. => ####/.#.#/..../##.#",
			"#.#/###/#.. => ..../.###/.##./#.#.",
			".##/###/#.. => #.#./..##/.##./##..",
			"###/###/#.. => .###/##.#/#.#./#.##",
			".#./#.#/.#. => ...#/###./..../####",
			"##./#.#/.#. => ..../###./#.##/..##",
			"#.#/#.#/.#. => #.../###./##.#/#...",
			"###/#.#/.#. => #.../##../..#./..#.",
			".#./###/.#. => ###./..../.#.#/..#.",
			"##./###/.#. => ##.#/..../.##./###.",
			"#.#/###/.#. => #.##/##../...#/....",
			"###/###/.#. => .##./####/##../.#..",
			"#.#/..#/##. => .#.#/#.#./##.#/#.##",
			"###/..#/##. => ####/##../..##/####",
			".##/#.#/##. => .#.#/#..#/####/##..",
			"###/#.#/##. => #.##/.#../.###/.#..",
			"#.#/.##/##. => ...#/.#.#/#.#./....",
			"###/.##/##. => ..#./#.#./.###/###.",
			".##/###/##. => .###/.###/.##./.#..",
			"###/###/##. => #.../#.../#.##/.#..",
			"#.#/.../#.# => ..#./..../##../#.##",
			"###/.../#.# => ..#./#.##/####/...#",
			"###/#../#.# => #.../###./#.../...#",
			"#.#/.#./#.# => ..##/#.##/.#.#/.#..",
			"###/.#./#.# => #.../.#.#/#.#./##..",
			"###/##./#.# => ##../.###/.#../...#",
			"#.#/#.#/#.# => ..##/#.#./#.##/##..",
			"###/#.#/#.# => .###/..##/..#./.###",
			"#.#/###/#.# => ##.#/.###/..../.###",
			"###/###/#.# => ##.#/#.##/##../..#.",
			"###/#.#/### => ##../.#../#.#./##.#",
			"###/###/### => .##./##../..#./.###"
	};

	Pattern pattern = Pattern.compile("(.*) => (.*)");
	private class Rule {
		private final int size;
		// Format ex <pre>../.#</pre> || <pre>.#./..#/###</pre>
		private final String input;
		// Format ex <pre>##./#../...</pre> || <pre>#..#/..../..../#..#</pre>
		private final String outputStr;
		final String[] output;

		public Rule(String str) {
			Matcher m = pattern.matcher(str);
			if (!m.find()) {
				System.err.println("Error matching: " + str);
				System.exit(1);
			}

			this.input = m.group(1);
			this.size = this.input.split("/").length;

			this.outputStr = m.group(2);
			this.output = this.outputStr.split("/");
		}

		boolean matches(String[] in) {
			if (in.length != size) {
				return false;
			}

			String[] tryIn = in;
			for (int n = 0; n < 4; n++) {
				String inStr = String.join("/", tryIn);
				boolean ret = this.input.equals(inStr);
				if (ret) {
					return ret;
				}

				// Flip
				inStr = String.join("/", flip(tryIn));
				ret = this.input.equals(inStr);
				if (ret) {
					return ret;
				}

				// rotate
				tryIn = rotate(tryIn);
			}
			return false;
		}

		private String[] flip(String[] in) {
			String[] out = new String[in.length];
			for (int i = 0; i < in.length; i++) {
				out[i] = (new StringBuilder(in[i])).reverse().toString();
			}
			return out;
		}

		private String[] rotate(String[] in) {
			char[][] inChar = new char[in.length][in.length];
			for (int i = 0; i < in.length; i++) {
				inChar[i] = in[i].toCharArray();
			}

			char[][] outChar = rotate(inChar);
			String[] out = new String[in.length];
			for (int i = 0; i < in.length; i++) {
				out[i] = "";
				for (int j = 0; j < outChar[i].length; j++)
					out[i] += outChar[i][j];
			}

			return out;
		}

		private char[][] rotate(final char[][] in) {
			char[][] out = new char[in.length][in.length];

			if (in.length == 2) {
				out[0][0] = in[1][0];
				out[0][1] = in[0][0];
				out[1][0] = in[1][1];
				out[1][1] = in[0][1];
			} else {
				out[0][0] = in[2][0];
				out[0][1] = in[1][0];
				out[0][2] = in[0][0];
				out[1][0] = in[2][1];
				out[1][1] = in[1][1];
				out[1][2] = in[0][1];
				out[2][0] = in[2][2];
				out[2][1] = in[1][2];
				out[2][2] = in[0][2];
			}

			return out;
		}

		@Override
		public String toString() {
			return String.join("/", this.input) + " => " + this.outputStr;
		}
	}

	private int program1(int nbIteration, String... input) {
		// Init rules
		List<Rule> rules = new ArrayList<>(input.length);
		for (int i = 0; i < input.length; i++) {
			rules.add(new Rule(input[i]));
		}

		String[] grid = new String[] { ".#.", "..#", "###" };

		for (int i = 0; i < nbIteration; i++) {
//			System.out.println(String.join("\n", grid));
//			System.out.println();
			int s = grid.length;
			int n = -1, mod=-1;
			String[] newGrid;
			if (s % 2 == 0) {
				// Mod 2
				mod = 2;
				n = s / 2;
			} else {
				// Mod 3
				mod = 3;
				n = s / 3;
			}
			newGrid = new String[s + s / mod];
//			System.out.println("newSize=" + newGrid.length);
			Arrays.fill(newGrid, "");

			for (int j = 0; j < n * n; j++) {
				String[] in = new String[mod];
				for (int k = 0; k < mod; k++) {
					in[k] = grid[(j / n) * mod + k].substring(mod * (j % n), mod * (j % n) + mod);
				}

				List<Rule> matchRules = rules.stream().filter(r -> r.matches(in)).collect(Collectors.toList());
				if (matchRules.size() != 1) {
					System.err.println("Found " + matchRules.size() + " rules for: " + String.join("/", in));
					matchRules.stream().forEach(r -> System.out.println(r));
					System.exit(1);
				}
				Rule rule = matchRules.get(0);
				String[] square = rule.output;
				for (int k = 0; k < square.length; k++) {
					String line = square[k];

					newGrid[k + (j / n) * square.length] += line;
				}
			}
//			for (int k = 0; k < newGrid.length; k++) {
//				if (newGrid[k].length() != newGrid.length) {
//					System.err.println("Wrong length: " + newGrid[k].length() + " vs " + newGrid.length);
//					System.exit(1);
//				}
//			}
			grid = newGrid;
		}
//		System.out.println(String.join("\n", grid));
//		System.out.println("=================================================");

		// Count nb #
		int ret = 0;
		for (int i = 0; i < grid.length; i++) {
			String line = grid[i];
			for (int j = 0; j < line.length(); j++) {
				String c = line.substring(j, j + 1);
				if ("#".equals(c)) {
					ret++;
				}
			}

		}
		return ret;
	}

	@Test
	public void ruleMatchTrueTest() {
		Rule r = new Rule("../.# => ##./#../...");
		Assert.assertTrue("../.# should match", r.matches("../.#".split("/")));
		Assert.assertTrue("#./.. should match", r.matches("#./..".split("/")));
		Assert.assertTrue(".#/.. should match", r.matches(".#/..".split("/")));
		Assert.assertTrue("../#. should match", r.matches("../#.".split("/")));
	}

	@Test
	public void rule3MatchTrueTest() {
		Rule r = new Rule(".#./..#/### => #..#/..../..../#..#");
		Assert.assertTrue(".#./..#/### should match", r.matches(".#./..#/###".split("/")));
		Assert.assertTrue(".#./#../### should match", r.matches(".#./#../###".split("/")));
		Assert.assertTrue("#../#.#/##. should match", r.matches("#../#.#/##.".split("/")));
		Assert.assertTrue("###/..#/.#. should match", r.matches("###/..#/.#.".split("/")));
	}

	@Test
	public void ruleMatchFalseTest() {
		Rule r = new Rule("../.# => ##./#../...");
		Assert.assertFalse("#./.# should NOT match", r.matches("#./.#".split("/")));
	}

	@Test
	public void example1_1() {
		int ret = program1(0, "../.# => ##./#../...",
				".#./..#/### => #..#/..../..../#..#");
		Assert.assertEquals(5, ret);
	}

	@Test
	public void example1_2() {
		int ret = program1(2, "../.# => ##./#../...",
				".#./..#/### => #..#/..../..../#..#");
		Assert.assertEquals(12, ret);
	}

	@Test
	public void test1() {
		int ret = program1(5, INPUT);
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
	@Test
	public void test2() {
		int ret = program1(18, INPUT);
		System.out.println("test2=" + ret);
	}
}
