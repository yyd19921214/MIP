package com.thread_v3;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

public class CalculateSimilarity implements Callable<Map<String,Double>>{
	
	private TfIdfEntity tfidfentity;
	
	private Set<String> pairs;

//	private double s1=0.2d;
	
	private Map<String,Double> result=new HashMap<>();
	
	public CalculateSimilarity(TfIdfEntity tfidfentity,Set<String> pairs) {
		this.tfidfentity=tfidfentity;
		this.pairs=pairs;
	}

	
	@Override
	public Map<String, Double> call() throws Exception {
		for(String str:pairs){
			String s1=str.split("-")[0];
			String s2=str.split("-")[1];
			double d=calculateTextSimilarity(s1,s2);
			result.put(str, d);
		}
		return result;
	}



	
	
	
private  double calculateTextSimilarity(String first, String second) {
	
	
		
		Map<String, Integer> mapFirst=tfidfentity.allSegsMap.get(first);
		Map<String, Integer> mapSecond=tfidfentity.allSegsMap.get(second);
		Set<String> setFirst=mapFirst.keySet();//获取文档一的所有词汇
		Set<String> setSecond=mapSecond.keySet();//获取文档二的所有词汇
		double result=0.00;
		double firstPro;
		double secondPro;
		double tmp1;
		double tmp2=tfidfentity.tmp.get(first);
		double tmp3;
		double tmp4=tfidfentity.tmp.get(second);
		
		
		
		for(String str:setFirst){
			if(setSecond.contains(str)){
				int cnt1=mapFirst.get(str);
				if(cnt1<=1000){
					
					tmp1=1+Math.log((1+LogArray.logArray[cnt1]));
				}
				else{
					tmp1=1+Math.log((1+Math.log(cnt1)));
				}
				firstPro=tmp1/tmp2;
				
				int cnt2=mapSecond.get(str);
				if(cnt2<=1000){
					tmp3=1+Math.log((1+LogArray.logArray[cnt2]));
				}
				else{
					tmp3=1+Math.log((1+Math.log(cnt2)));
				}
				secondPro=tmp3/tmp4;
				
				result+=firstPro*secondPro*tfidfentity.idfMap.get(str);
			}
		}
		
		return result;
	}

}
