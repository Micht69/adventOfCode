package com.micht;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Test;

public class Exo13 {

	private static final String[] INPUT = new String[] {
			"0: 3",
			"1: 2",
			"2: 4",
			"4: 4",
			"6: 5",
			"8: 8",
			"10: 6",
			"12: 6",
			"14: 8",
			"16: 6",
			"18: 6",
			"20: 8",
			"22: 12",
			"24: 8",
			"26: 8",
			"28: 12",
			"30: 8",
			"32: 12",
			"34: 9",
			"36: 14",
			"38: 12",
			"40: 12",
			"42: 12",
			"44: 14",
			"46: 14",
			"48: 10",
			"50: 14",
			"52: 12",
			"54: 14",
			"56: 12",
			"58: 17",
			"60: 10",
			"64: 14",
			"66: 14",
			"68: 12",
			"70: 12",
			"72: 18",
			"74: 14",
			"78: 14",
			"82: 14",
			"84: 24",
			"86: 14",
			"94: 14"
	};

	private class Layer {
		int pos = -1;
		int depth = -1;
		int scanPos = 0;
		boolean inc = true;

		public Layer(int l, int d) {
			this.pos = l;
			this.depth = d;
		}

		public void moveScanner() {
			if (inc) {
				scanPos++;
				if (scanPos == (depth - 1)) {
					inc = false;
				}
			} else {
				scanPos--;
				if (scanPos == 0) {
					inc = true;
				}
			}
		}

		int scanPosSave = 0;
		boolean incSave = true;

		public void store() {
			this.scanPosSave = this.scanPos;
			this.incSave = this.inc;
		}

		public void restore() {
			this.scanPos = this.scanPosSave;
			this.inc = this.incSave;
		}

		@Override
		public String toString() {
			return "" + pos + ": " + depth + " [" + scanPos + "]";
		}
	}


	private int program1(String... input) {
		Map<Integer, Layer> layers = new HashMap<>();

		// Each row
		int maxLayer = -1;
		Pattern pattern = Pattern.compile("([0-9]*): ([0-9]*)");
		for (String line : input) {
			Matcher m = pattern.matcher(line);

			if (m.find()) {
				int layer = Integer.parseInt(m.group(1));
				int depth = Integer.parseInt(m.group(2));

				layers.put(layer, new Layer(layer, depth));
				if (layer > maxLayer) {
					maxLayer = layer;
				}
			} else {
				System.err.println("ERROR matching line : " + line);
				System.exit(1);
			}
		}
		maxLayer++;

		int sum = 0;
		for (int i=0; i<maxLayer; i++) {
			if (layers.containsKey(i)) {
				// Check caught
				Layer l = layers.get(i);
				if (l.scanPos == 0) {
					sum += l.pos * l.depth;
				}
			}
			
			// Inc
			layers.values().forEach((layer) -> layer.moveScanner());
		}

		return sum;
	}

	@Test
	public void example1_1() {
		int ret = program1("0: 3",
				"1: 2",
				"4: 4",
				"6: 4");
		Assert.assertEquals(24, ret);
	}

	@Test
	public void test1() {
		int ret = program1(INPUT);
		System.out.println("test1=" + ret);
	}


	private int program2(String... input) {
		Map<Integer, Layer> layers = new HashMap<>();

		// Each row
		int maxLayer = -1;
		Pattern pattern = Pattern.compile("([0-9]*): ([0-9]*)");
		for (String line : input) {
			Matcher m = pattern.matcher(line);

			if (m.find()) {
				int layer = Integer.parseInt(m.group(1));
				int depth = Integer.parseInt(m.group(2));

				layers.put(layer, new Layer(layer, depth));
				if (layer > maxLayer) {
					maxLayer = layer;
				}
			} else {
				System.err.println("ERROR matching line : " + line);
				System.exit(1);
			}
		}
		maxLayer++;

		int sum = -1;
		boolean caught = true;
		while (caught) {
			sum++;
			caught = false;

			layers.values().forEach((layer) -> layer.store());
			// Simulation
			for (int i = 0; i < maxLayer; i++) {
				if (layers.containsKey(i)) {
					// Check caught
					Layer l = layers.get(i);
					if (l.scanPos == 0) {
						caught = true;
						break;
					}
				}

				// Inc
				layers.values().forEach((layer) -> layer.moveScanner());
			}
			if (!caught) {
				break;
			}
			layers.values().forEach((layer) -> layer.restore());

			layers.values().forEach((layer) -> layer.moveScanner());
		}

		return sum;
	}

	@Test
	public void example2_1() {
		int ret = program2("0: 3",
				"1: 2",
				"4: 4",
				"6: 4");
		Assert.assertEquals(10, ret);
	}

	@Test
	public void test2() {
		int ret = program2(INPUT);
		System.out.println("test2=" + ret);
	}
}
