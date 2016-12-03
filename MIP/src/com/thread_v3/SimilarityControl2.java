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

public class SimilarityControl2 {
	
	private TfIdfEntity entity;

	public SimilarityControl2(TfIdfEntity entity) {
		this.entity=entity;
	}
	
	public void calculateSim() throws InterruptedException{
//		long d1=System.currentTimeMillis();
		LogArray.init();
		Set<String> contentSet=entity.allSegsMap.keySet();
		entity.resultMat=new double[contentSet.size()][contentSet.size()];


//		long d2=System.currentTimeMillis();
//		System.out.println("similarity的预备时间为"+(d2-d1));
		ExecutorService es = Executors.newFixedThreadPool(Constant.simthreadNum);  
		
		List<Callable<Boolean>> taskPool=new ArrayList<>();
		CalculateSimilarity2 cs2=new CalculateSimilarity2(entity);
		CountDownLatch latch=new CountDownLatch(Constant.simthreadNum);
		for(int i=1;i<=Constant.simthreadNum;i++){
			CalculateHelper task=new CalculateHelper(cs2,latch);
			taskPool.add(task);
		}
		for(int i=0;i<taskPool.size();i++){
			es.submit(taskPool.get(i));
		}
		
		latch.await();
		es.shutdown();
//		System.out.println("here");
		

	}

}
