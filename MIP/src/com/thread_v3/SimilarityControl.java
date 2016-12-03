package com.thread_v3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SimilarityControl {
	
	private TfIdfEntity entity;

	public SimilarityControl(TfIdfEntity entity) {
		this.entity=entity;
	}
	
	public void calculateSim(){
		long d1=System.currentTimeMillis();
		LogArray.init();
		Set<String> contentSet=entity.allSegsMap.keySet();
		ArrayList<String> contentList = new ArrayList<>(contentSet);
		Collections.sort(contentList, new Comparator<String>() {
			public int compare(String obj1, String obj2) {
				return Integer.valueOf(obj1)-Integer.valueOf(obj2);
			}
		});
		List<Set<String>> setList=new ArrayList<>();
		for(int i=1;i<=Constant.simthreadNum;i++){
			Set<String> set=new HashSet<>(contentList.size()/Constant.simthreadNum+10);
			setList.add(set);
		}
		
		int cnt=0;
		for(int i=0;i<contentList.size();i++){
			for(int j=i+1;j<contentList.size();j++){
				setList.get(cnt%Constant.simthreadNum).add(contentList.get(i)+"-"+contentList.get(j));
				cnt++;
			}
		}

//		long d2=System.currentTimeMillis();
//		System.out.println("similarity的预备时间为"+(d2-d1));
		ExecutorService es = Executors.newFixedThreadPool(Constant.simthreadNum);  
		
		List<Callable<Map<String,Double>>> taskPool=new ArrayList<>();
		ArrayList<Future<Map<String,Double>>> futures=new ArrayList<>();
		
//		List<Thread> threadPoll=new ArrayList<>();
//		CountDownLatch latch=new CountDownLatch(Constant.simthreadNum);
		for(int i=1;i<=Constant.simthreadNum;i++){
			CalculateSimilarity task=new CalculateSimilarity(entity, setList.get(i-1));
			taskPool.add(task);
		}
		for(int i=0;i<taskPool.size();i++){
			Future<Map<String,Double>> f=es.submit(taskPool.get(i));
			futures.add(f);
		}
		for(int i=0;i<futures.size();i++){
			try {
				entity.resultMap.putAll(futures.get(i).get());
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		
		es.shutdown();
	}

}
