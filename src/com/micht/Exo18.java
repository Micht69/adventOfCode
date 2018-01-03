package com.micht;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class Exo18 {

	private static final String[] INPUT = new String[] {
			"set i 31",
			"set a 1",
			"mul p 17",
			"jgz p p",
			"mul a 2",
			"add i -1",
			"jgz i -2",
			"add a -1",
			"set i 127",
			"set p 826",
			"mul p 8505",
			"mod p a",
			"mul p 129749",
			"add p 12345",
			"mod p a",
			"set b p",
			"mod b 10000",
			"snd b",
			"add i -1",
			"jgz i -9",
			"jgz a 3",
			"rcv b",
			"jgz b -1",
			"set f 0",
			"set i 126",
			"rcv a",
			"rcv b",
			"set p a",
			"mul p -1",
			"add p b",
			"jgz p 4",
			"snd a",
			"set a b",
			"jgz 1 3",
			"snd b",
			"set f 1",
			"add i -1",
			"jgz i -11",
			"snd a",
			"jgz f -16",
			"jgz a -19"
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

	private long mult(Long val, Long val2) {
		long l = val * val2;
		return l;
	}

	private Long program1(String... input) {
		Long lastSound = 0l;
		boolean found = false;

		for (int i = 0; i < input.length;) {
			String instr = input[i];
//			if (input.length > 12)
//				System.out.println(instr);
			String cmd = instr.substring(0, 3);
			String params = instr.substring(4);

			if ("set".equals(cmd)) {
				String[] tmp = params.split(" ");
				
				values.put(tmp[0], getValue(tmp[1]));
			} else if ("add".equals(cmd)) {
				String[] tmp = params.split(" ");
				
				Long val = getValue(tmp[0]);
				values.put(tmp[0], add(val, getValue(tmp[1])));
			} else if ("mul".equals(cmd)) {
				String[] tmp = params.split(" ");

				Long val = getValue(tmp[0]);
				values.put(tmp[0], mult(val, getValue(tmp[1])));

			} else if ("mod".equals(cmd)) {
				String[] tmp = params.split(" ");

				Long val = getValue(tmp[0]);
				values.put(tmp[0], val % getValue(tmp[1]));

			} else if ("snd".equals(cmd)) {
				lastSound = getValue(params);
				// System.out.println("sound=" + lastSound);

			} else if ("rcv".equals(cmd)) {
				Long val = getValue(params);
				if (val != 0) {
					found = true;
					break;
				}

			} else if ("jgz".equals(cmd)) {
				String[] tmp = params.split(" ");

				Long val1 = getValue(tmp[0]);
				Long val2 = getValue(tmp[1]);
				if (val1 > 0) {
					i += val2;
					continue;
				}
			} else {
				System.err.println("Command not found: " + instr);
			}

			i++;
		}
		if (!found) {
			System.err.println("Sound not found");
		}

		return lastSound;
	}

	@Test
	public void example1_1() {
		Long ret = program1("set a 1",
				"add a 2",
				"mul a a",
				"mod a 5",
				"snd a",
				"set a 0",
				"rcv a",
				"jgz a -1",
				"set a 1",
				"jgz a -2");
		Assert.assertEquals(Long.valueOf(4), ret);
	}

	@Test
	public void example1_2() {
		Long ret = program1("jgz a 3",
				"set a 1",
				"jgz a -2",
				"snd a",
				"rcv a");
		Assert.assertEquals(Long.valueOf(1), ret);
	}

	@Test
	public void example1_3() {
		Long ret = program1("set b 2",
				"set a 1",
				"add a b",
				"mul a b",
				"set b 5",
				"mod a b",
				"snd a",
				"rcv a");
		Assert.assertEquals(Long.valueOf(1), ret);
	}

	@Test
	public void test1() {
		Long ret = program1(INPUT);
		System.out.println("test1=" + ret);
	}

	// ==================================================

	private class Program {
		final int id;
		final String[] instructions;
		int index;
		int sendCount = 0;
		Program other;

		Map<String, Long> register = new HashMap<>();
		List<Long> inQueue = new LinkedList<>();

		public Program(int pId, String[] pInstr) {
			this.id = pId;
			this.instructions = pInstr;
			this.index = 0;
			register.put("p", pId * 1l);
		}

		private Long getValue(String valStr) {
			Long val = null;
			try {
				val = Long.parseLong(valStr);
			} catch (NumberFormatException nfe) {
				val = register.get(valStr);
			}
			if (val == null) {
				val = Long.valueOf(0);
			}

			return val;
		}

		public boolean work() {
			String instr = instructions[this.index];

			String cmd = instr.substring(0, 3);
			String params = instr.substring(4);

			if ("set".equals(cmd)) {
				String[] tmp = params.split(" ");

				register.put(tmp[0], getValue(tmp[1]));
			} else if ("add".equals(cmd)) {
				String[] tmp = params.split(" ");

				Long val = getValue(tmp[0]);
				register.put(tmp[0], add(val, getValue(tmp[1])));
			} else if ("mul".equals(cmd)) {
				String[] tmp = params.split(" ");

				Long val = getValue(tmp[0]);
				register.put(tmp[0], mult(val, getValue(tmp[1])));

			} else if ("mod".equals(cmd)) {
				String[] tmp = params.split(" ");

				Long val = getValue(tmp[0]);
				register.put(tmp[0], val % getValue(tmp[1]));

			} else if ("snd".equals(cmd)) {
				Long val = getValue(params);
				this.sendCount++;
				this.other.inQueue.add(val);

			} else if ("rcv".equals(cmd)) {
				if (inQueue.isEmpty()) {
					return false;
				}
				Long val = inQueue.remove(0);
				register.put(params, val);

			} else if ("jgz".equals(cmd)) {
				String[] tmp = params.split(" ");

				Long val1 = getValue(tmp[0]);
				Long val2 = getValue(tmp[1]);
				if (val1 > 0) {
					index += val2;
					return true;
				}
			} else {
				System.err.println("Command not found: " + instr);
			}

			index++;
			return true;
		}

		@Override
		public String toString() {
			return "#" + id + ": index=" + index + ", sendCount=" + sendCount + ", inQueue=" + inQueue + "";
		}
	}


	private int program2(String... input) {
		Program p0 = new Program(0, input);
		Program p1 = new Program(1, input);
		p0.other = p1;
		p1.other = p0;

		int deadLocks = 0;
		while (deadLocks < 5) {
			boolean out0 = p0.work();
			boolean out1 = p1.work();
			
			boolean deadLock = (!out0 && !out1);
			if (deadLock) {
				deadLocks++;
			} else {
				deadLocks = 0;
			}
		}
		
		return p1.sendCount;
	}

	@Test
	public void example2_1() {
		int ret = program2("snd 1",
				"snd 2",
				"snd p",
				"rcv a",
				"rcv b",
				"rcv c",
				"rcv d");
		Assert.assertEquals(3, ret);
	}

	@Test
	public void test2() {
		int ret = program2(INPUT);
		System.out.println("test2=" + ret);
	}
}
