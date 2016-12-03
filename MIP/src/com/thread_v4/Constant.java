package com.thread_v4;

public class Constant {

	public Constant() {
		// TODO Auto-generated constructor stub
	}
	
	public static final int tfthreadNum=3;//计算tf值的线程数目;
	public static final int idfthreadNum=4;//计算idf值的线程数目;
	public static final int simthreadNum=10;//计算相似度的线程数目;
	
//	public static final String testTextPath="/Users/yangyudong/1998-01-small-new-processed.txt";//小规模测试文本的路径
	public static final String totalTextPath="src/1998-01-new-processed.txt";     //正式文本的路径（原始文本经处理所得）
	
	public static final String resultTextPath="src/csvResult.txt"; //结果放置的地方
	
	public static final String originalPath="src/1998-01.txt";//原始文本放置的路径
	
	public static final String stopWordPath="src/stopword2.txt";

	public static double s1=0.2d;
}
