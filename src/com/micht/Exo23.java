package com.micht;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class Exo23 {

	private static final String[] INPUT = new String[] {
			"set b 81",
			"set c b",
			"jnz a 2",
			"jnz 1 5",
			"mul b 100",
			"sub b -100000",
			"set c b",
			"sub c -17000",
			"set f 1",
			"set d 2",
			"set e 2",
			"set g d",
			"mul g e",
			"sub g b",
			"jnz g 2",
			"set f 0",
			"sub e -1",
			"set g e",
			"sub g b",
			"jnz g -8",
			"sub d -1",
			"set g d",
			"sub g b",
			"jnz g -13",
			"jnz f 2",
			"sub h -1",
			"set g b",
			"sub g c",
			"jnz g 2",
			"jnz 1 3",
			"sub b -17",
			"jnz 1 -23"
	};

	Map<String, Long> values = new HashMap<>();

	private Long getValue(String valStr) {
		Long val = null;
		try {
			val = Long.parseLong(valStr);
		} catch (NumberFormatException nfe) {
			val = values.get(valStr);
		}
		if (val == null) {
			val = Long.valueOf(0);
		}

		return val;
	}

	private long add(Long val, Long val2) {
		long l = val + val2;
		return l;
	}

	private long sub(Long val, Long val2) {
		long l = val - val2;
		return l;
	}

	private long mult(Long val, Long val2) {
		long l = val * val2;
		return l;
	}

	private int program1(String... input) {
		int nbMul = 0;

		for (int i = 0; i < input.length;) {
			String instr = input[i];
			String cmd = instr.substring(0, 3);
			String params = instr.substring(4);

			if ("set".equals(cmd)) {
				String[] tmp = params.split(" ");
				
				values.put(tmp[0], getValue(tmp[1]));

			} else if ("add".equals(cmd)) {
				String[] tmp = params.split(" ");
				
				Long val = getValue(tmp[0]);
				values.put(tmp[0], add(val, getValue(tmp[1])));

			} else if ("sub".equals(cmd)) {
				String[] tmp = params.split(" ");

				Long val = getValue(tmp[0]);
				values.put(tmp[0], sub(val, getValue(tmp[1])));

			} else if ("mul".equals(cmd)) {
				String[] tmp = params.split(" ");

				Long val = getValue(tmp[0]);
				values.put(tmp[0], mult(val, getValue(tmp[1])));
				nbMul++;

			} else if ("jnz".equals(cmd)) {
				String[] tmp = params.split(" ");

				Long val1 = getValue(tmp[0]);
				Long val2 = getValue(tmp[1]);
				if (val1 != 0) {
					i += val2;
					continue;
				}
				
			} else {
				System.err.println("Command not found: " + instr);
			}

			i++;
		}

		return nbMul;
	}

	@Test
	public void example1_1() {
		int ret = program1("set a 1",
				"add a 2",
				"mul a a");
		Assert.assertEquals(1, ret);
	}

	@Test
	public void test1() {
		int ret = program1(INPUT);
		System.out.println("test1=" + ret);
	}

	// ==================================================

//	private class Program {
//		final int id;
//		final String[] instructions;
//		int index;
//		int sendCount = 0;
//		Program other;
//
//		Map<String, Long> register = new HashMap<>();
//		List<Long> inQueue = new LinkedList<>();
//
//		public Program(int pId, String[] pInstr) {
//			this.id = pId;
//			this.instructions = pInstr;
//			this.index = 0;
//			register.put("p", pId * 1l);
//		}
//
//		private Long getValue(String valStr) {
//			Long val = null;
//			try {
//				val = Long.parseLong(valStr);
//			} catch (NumberFormatException nfe) {
//				val = register.get(valStr);
//			}
//			if (val == null) {
//				val = Long.valueOf(0);
//			}
//
//			return val;
//		}
//
//		public boolean work() {
//			String instr = instructions[this.index];
//
//			String cmd = instr.substring(0, 3);
//			String params = instr.substring(4);
//
//			if ("set".equals(cmd)) {
//				String[] tmp = params.split(" ");
//
//				register.put(tmp[0], getValue(tmp[1]));
//			} else if ("add".equals(cmd)) {
//				String[] tmp = params.split(" ");
//
//				Long val = getValue(tmp[0]);
//				register.put(tmp[0], add(val, getValue(tmp[1])));
//			} else if ("mul".equals(cmd)) {
//				String[] tmp = params.split(" ");
//
//				Long val = getValue(tmp[0]);
//				register.put(tmp[0], mult(val, getValue(tmp[1])));
//
//			} else if ("mod".equals(cmd)) {
//				String[] tmp = params.split(" ");
//
//				Long val = getValue(tmp[0]);
//				register.put(tmp[0], val % getValue(tmp[1]));
//
//			} else if ("snd".equals(cmd)) {
//				Long val = getValue(params);
//				this.sendCount++;
//				this.other.inQueue.add(val);
//
//			} else if ("rcv".equals(cmd)) {
//				if (inQueue.isEmpty()) {
//					return false;
//				}
//				Long val = inQueue.remove(0);
//				register.put(params, val);
//
//			} else if ("jgz".equals(cmd)) {
//				String[] tmp = params.split(" ");
//
//				Long val1 = getValue(tmp[0]);
//				Long val2 = getValue(tmp[1]);
//				if (val1 > 0) {
//					index += val2;
//					return true;
//				}
//			} else {
//				System.err.println("Command not found: " + instr);
//			}
//
//			index++;
//			return true;
//		}
//
//		@Override
//		public String toString() {
//			return "#" + id + ": index=" + index + ", sendCount=" + sendCount + ", inQueue=" + inQueue + "";
//		}
//	}
//
	
	@Test
	public void test2() {
		int a = 1, b = 0, c = 0, d = 0, e = 0, f = 0, g = 1, h = 0;
		b = 81;
		c = b;
		if (a != 0) {
			b = b * 100 + 100000;
			c = b + 17000;
		}
		while (g != 0) {
			f = 1;
			d = 2;
			e = 2;
			for (d = 2; d * d <= b; d++) {
				if ((b % d == 0)) {
					f = 0;
					break;
				}
			}
			if (f == 0) // not a prime
				h++;
			g = b - c;
			b += 17;
		}

		System.out.println("test2=" + h);
	}
}
