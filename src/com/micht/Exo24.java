package com.micht;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;

public class Exo24 {

	private static final String[] INPUT = new String[] {
			"31/13",
			"34/4",
			"49/49",
			"23/37",
			"47/45",
			"32/4",
			"12/35",
			"37/30",
			"41/48",
			"0/47",
			"32/30",
			"12/5",
			"37/31",
			"7/41",
			"10/28",
			"35/4",
			"28/35",
			"20/29",
			"32/20",
			"31/43",
			"48/14",
			"10/11",
			"27/6",
			"9/24",
			"8/28",
			"45/48",
			"8/1",
			"16/19",
			"45/45",
			"0/4",
			"29/33",
			"2/5",
			"33/9",
			"11/7",
			"32/10",
			"44/1",
			"40/32",
			"2/45",
			"16/16",
			"1/18",
			"38/36",
			"34/24",
			"39/44",
			"32/37",
			"26/46",
			"25/33",
			"9/10",
			"0/29",
			"38/8",
			"33/33",
			"49/19",
			"18/20",
			"49/39",
			"18/39",
			"26/13",
			"19/32"
	};

	private class Compo {
		final int end1;
		final int end2;

		public Compo(int id, int e1, int e2) {
			this.end1 = e1;
			this.end2 = e2;
		}

		@Override
		public String toString() {
			return this.end1 + "/" + this.end2;
		}
	}

	private int program1(String... input) {
		// Init
		List<Compo> compos = new ArrayList<>(input.length);
		for (int i = 0; i < input.length; i++) {
			String[] str = input[i].split("/");

			compos.add(new Compo(i, Integer.parseInt(str[0]), Integer.parseInt(str[1])));
		}

		// Computes
		int mavValue = Integer.MIN_VALUE;
		List<Compo> starts = compos.stream().filter(c -> c.end1 == 0 || c.end2 == 0).collect(Collectors.toList());
		for (Compo start : starts) {
			List<Compo> used = new ArrayList<>(input.length);
			used.add(start);
			int value = parcour1(compos, used, start, 0, 0);
			if (value > mavValue) {
				mavValue = value;
			}
		}

		return mavValue;
	}

	private int parcour1(List<Compo> compos, List<Compo> used, Compo cur, int usedEnd, int curValue) {
		int otherEnd = cur.end1 == usedEnd ? cur.end2 : cur.end1;

		// Find
		List<Compo> potentials = compos.stream().filter(c -> !used.contains(c) && (c.end1 == otherEnd || c.end2 == otherEnd))
				.collect(Collectors.toList());
		if (potentials.isEmpty()) {
			return curValue + otherEnd;
		}

		int maxVal = Integer.MIN_VALUE;
		for (Compo compo : potentials) {
			used.add(compo);
			int val = parcour1(compos, used, compo, otherEnd, curValue + otherEnd * 2);
			used.remove(compo);
			if (val > maxVal) {
				maxVal = val;
			}
		}
		
		return maxVal;
	}

	@Test
	public void example1_1() {
		int ret = program1("0/2",
				"2/2",
				"2/3",
				"3/4",
				"3/5",
				"0/1",
				"10/1",
				"9/10");
		Assert.assertEquals(31, ret);
	}

	@Test
	public void test1() {
		int ret = program1(INPUT);
		System.out.println("test1=" + ret);
	}

	private int program2(String... input) {
		// Init
		List<Compo> compos = new ArrayList<>(input.length);
		for (int i = 0; i < input.length; i++) {
			String[] str = input[i].split("/");

			compos.add(new Compo(i, Integer.parseInt(str[0]), Integer.parseInt(str[1])));
		}

		// Computes
		Ret maxVal = new Ret(1, Integer.MIN_VALUE);
		List<Compo> starts = compos.stream().filter(c -> c.end1 == 0 || c.end2 == 0).collect(Collectors.toList());
		for (Compo start : starts) {
			List<Compo> used = new ArrayList<>(input.length);
			used.add(start);
			Ret val = parcour2(compos, used, start, 0, new Ret(1, 0));
			if (val.l > maxVal.l || (val.l == maxVal.l && val.v > maxVal.v)) {
				maxVal = val;
			}
		}

		return maxVal.v;
	}

	private class Ret {
		int l;
		int v;

		public Ret(int l, int v) {
			this.l = l;
			this.v = v;
		}
	}

	private Ret parcour2(List<Compo> compos, List<Compo> used, Compo cur, int usedEnd, Ret curValue) {
		int otherEnd = cur.end1 == usedEnd ? cur.end2 : cur.end1;

		// Find
		List<Compo> potentials = compos.stream().filter(c -> !used.contains(c) && (c.end1 == otherEnd || c.end2 == otherEnd))
				.collect(Collectors.toList());
		if (potentials.isEmpty()) {
			return new Ret(curValue.l, curValue.v + otherEnd);
		}

		Ret maxVal = new Ret(curValue.l, Integer.MIN_VALUE);
		for (Compo compo : potentials) {
			used.add(compo);
			Ret val = parcour2(compos, used, compo, otherEnd, new Ret(curValue.l + 1, curValue.v + otherEnd * 2));
			used.remove(compo);
			if (val.l > maxVal.l || (val.l == maxVal.l && val.v > maxVal.v)) {
				maxVal = val;
			}
		}

		return maxVal;
	}

	@Test
	public void example2_1() {
		int ret = program2("0/2",
				"2/2",
				"2/3",
				"3/4",
				"3/5",
				"0/1",
				"10/1",
				"9/10");
		Assert.assertEquals(19, ret);
	}

	@Test
	public void test2() {
		int ret = program2(INPUT);
		System.out.println("test2=" + ret);
	}
}
