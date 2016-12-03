package com.thread_v4;

public class LogArray {
	
	public  static double[] logArray = new double[1001];

	public LogArray() {
		// TODO Auto-generated constructor stub
	}
	
	public static void init(){
		logArray[0] = 0d;
		for (int i = 1; i <= 1000; i++) {
			logArray[i] = Math.log(i);
		}
	}

}
