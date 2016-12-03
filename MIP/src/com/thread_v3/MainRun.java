package com.thread_v3;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;



public class MainRun {

	public MainRun() {
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String[] args) {
		

		long d1=System.currentTimeMillis();
		File f=new File(Constant.totalTextPath);
		if(!f.exists()){
			ProcessText.init();
		}
		TfIdfEntity entity=new TfIdfEntity();
		TFControl tc=new TFControl(entity);
		tc.calculateTF();
		
		long d2=System.currentTimeMillis();
		System.out.println("tf的计算所用时间为"+(d2-d1));
		IDFControl idfc=new IDFControl(entity);
		idfc.calculateIDF();
		long d3=System.currentTimeMillis();
		System.out.println("idf的计算所用时间为"+(d3-d2));
//		SimilarityControl sc=new SimilarityControl(entity);
		SimilarityControl2 sc2=new SimilarityControl2(entity);
		try {
			sc2.calculateSim();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long d4=System.currentTimeMillis();
//		System.out.println("实例的数值为:"+entity.resultMat[1][5]);
//		System.out.println("用于计算的时间为"+(d4-d2)/1000+"s");
		System.out.println("similarity的计算所用时间为"+(d4-d3));
		try {
			CsvWriter.writerCsv(Constant.resultTextPath,entity);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long d5=System.currentTimeMillis();
		System.out.println("写入文本所用时间为"+(d5-d4));
		System.out.println("总所用时间为"+(d5-d1));

		
		
		
		
		
		
	}
	
	

}
