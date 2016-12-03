package com.thread_v4;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

public class CalculateIdf implements Callable<Map<String, Double>>{
	
	private TfIdfEntity tfidfentity;
	
	private List<String> words;
	
	private Map<String, Double> result=new HashMap<>();

	public CalculateIdf(TfIdfEntity tfidfentity,List<String> words) {
		this.tfidfentity=tfidfentity;
		this.words=words;
	}

	
	
	private void idf(List<String> words) {
		Double wordSize = Double.valueOf(tfidfentity.containWordOfAllDocNumberMap.size());
		for (String word : words) {
			Double number;
			number = Double.valueOf(tfidfentity.containWordOfAllDocNumberMap.get(word));
			result.put(word, Math.log(wordSize / (number + 1.0d)));// 考虑到每个线程的words都是不同的，因此不用上锁
		}

	}

	@Override
	public Map<String, Double> call() throws Exception {
		idf(words);
		return result;
	}
	
	

}
