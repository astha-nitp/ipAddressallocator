package com.codingExcercise.util;

import java.util.Random;

public class IpGenerator {

	public static String generateIP(String ipLower, String ipUpper) {

		Random r = new Random();
		// String ipLower = "222.190.120.1";
		// String ipLower = "224.195.130.11";
		String[] lowerList = ipLower.split("\\.");
		String[] upperList = ipUpper.split("\\.");

		int p1lower = extracted(lowerList[0]);
		int p1upper = extracted(upperList[0]);
		int p2lower = extracted(lowerList[1]);
		int p2upper = extracted(upperList[1]);
		int p3lower = extracted(lowerList[2]);
		int p3upper = extracted(upperList[2]);
		int p4lower = extracted(lowerList[3]);
		int p4upper = extracted(upperList[3]);
		int p1 = r.ints(p1lower, p1upper + 1).findAny().getAsInt();
		int max = 255;
		int min = 0;
		if (p1 == p1lower && p1 == p1upper) {
			min = p2lower;
			max = p2upper;
		} else if (p1 == p1lower)
			min = p2lower;
		else if (p1 == p1upper)
			max = p2upper;
		int p2 = r.ints(min, max + 1).findAny().getAsInt();
		max = 256;
		min = 0;
		if (p2 == p2lower && p2 == p2upper) {
			min = p3lower;
			max = p3upper;
		} else if (p2 == p2lower) {
			min = p3lower;
		} else if (p2 == p2upper) {
			max = p3upper;
		}
		int p3 = r.ints(min, max + 1).findAny().getAsInt();
		max = 256;
		min = 0;
		if (p3 == p3lower && p3 == p3upper) {
			min = p4lower;
			max = p4upper;
		} else if (p3 == p3lower) {
			min = p4lower;
		} else if (p3 == p3upper) {
			max = p4upper;
		}
		int p4 = r.ints(min, max + 1).findAny().getAsInt();

		String s = p1 + "." + p2 + "." + p3 + "." + p4;
		return s;
	}

	private static int extracted(String val) {
		return Integer.parseInt(val);
	}
}