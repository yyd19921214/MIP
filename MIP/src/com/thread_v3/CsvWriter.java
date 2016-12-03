package com.thread_v3;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class CsvWriter {

	private static File file;
	private static FileOutputStream out;
	private static OutputStreamWriter osw;
	private static BufferedWriter bw;
	
	private static String[] tags;

	public CsvWriter() {
		// TODO Auto-generated constructor stub
	}

	public static void writerCsv(String path, ArrayList<ArrayList<Double>> data) throws IOException {
		File file = new File(path);
//		init();
		FileOutputStream out = new FileOutputStream(file);
		OutputStreamWriter osw = new OutputStreamWriter(out, "UTF8");

		BufferedWriter bw = new BufferedWriter(osw);

		for (int i = 0; i < data.size(); i++) {
			StringBuffer sb = new StringBuffer();
			for (int j = 0; j < data.size(); j++) {
				if (j < i) {
					sb.append(" " + ",");
				}
				if (j >= i) {
					sb.append(data.get(i).get(j - i) + ",");
				}
			}
			sb.deleteCharAt(sb.length());
			sb.append("\n");
			bw.write(sb.toString());
			sb.delete(0, sb.length());
		}

		bw.close();
		osw.close();
		out.close();

	}

	public static void writerCsv(String path, Map<String, Double> data, int totalArticlesNum) throws IOException {
		File file = new File(path);
		FileOutputStream out = new FileOutputStream(file);
		OutputStreamWriter osw = new OutputStreamWriter(out, "UTF8");
//		HashSet set=new HashSet<>(List)
//		init();
		BufferedWriter bw = new BufferedWriter(osw);

		for (int i = 1; i <= totalArticlesNum; i++) {
			StringBuffer sb = new StringBuffer();
			sb.append(tags[i]);
			for (int j = i+1; j < totalArticlesNum; j++) {
//				if (j <= i) {
//					sb.append("-" + "\t");
//				}
//				if (j > i) {
				sb.append(data.get(i + "-" + j) + "\t");
//				}
			}
			sb.deleteCharAt(sb.length() - 1);
			sb.append("\n");
			bw.write(sb.toString());
		}

		bw.close();
		osw.close();
		out.close();

	}
	
	public static void writerCsv(String path,TfIdfEntity entity) throws IOException {
		double[][] data=entity.resultMat;
		File file = new File(path);
//		file.deleteOnExit();
		FileOutputStream out = new FileOutputStream(file);
		OutputStreamWriter osw = new OutputStreamWriter(out, "GBK");
		BufferedWriter bw=new BufferedWriter(osw);
//		init();
//		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "GBK"));

		StringBuilder sb = new StringBuilder();
//		long d1=System.currentTimeMillis();
		for (int i = 0; i < data.length; i++) {
			String str=Arrays.toString(data[i]);
			bw.write(str.substring(1, str.length()-1)+"\r\n");
//			bw.write(str.substring(1, str.length()-1)+"\r\n");
//			sb.

		}
//		bw.write(sb.toString());
//		long d2=System.currentTimeMillis();
//		System.out.println("写入时间++++++"+(d2-d1));

//		bw.write(sb.toString());
		bw.close();
//		osw.close();
//		out.close();

	}
	
	

//	public static void initial(String path) throws FileNotFoundException, UnsupportedEncodingException {
//		file = new File(path);
//		out = new FileOutputStream(file);
//		osw = new OutputStreamWriter(out, "UTF8");
//		bw = new BufferedWriter(osw);
//	}
//
//	public static void write(String str) throws IOException {
//		bw.write(str);
//	}
//
//	public static void close() throws IOException {
//		bw.close();
//		osw.close();
//		out.close();
//	}

	// public static void main(String[] args) {
	// try {
	// writerCsv("/Users/yangyudong/csvTest.txt");
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	
//	private static void init(){
//		tags=new String[3500];
//		tags[0]="";
//		StringBuffer sb=new StringBuffer();
//		for(int i=1;i<3500;i++){
//			sb.append("-" + "\t");
//			tags[i]=sb.toString();
//		}
//	}
}
