package com.cg.ars.utility;

public class MyUtil {

	private MyUtil() {
		super();
	}

	public static double calculatefare(int noOfPassengers, double baseFare) {
		return noOfPassengers * baseFare;
	}
}