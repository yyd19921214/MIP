package com.thread_v3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class IDFControl {
	
	private TfIdfEntity entity;

	public IDFControl(TfIdfEntity entity) {
		this.entity=entity;
	}
	
	public void calculateIDF(){
		entity.containWordOfAllDocNumberMap=containWordOfAllDocNumber(entity.allSegsMap);
		List<String> wordsList=new ArrayList<>(entity.containWordOfAllDocNumberMap.keySet());
		int eachSize=wordsList.size()/Constant.idfthreadNum;
		
        ExecutorService es = Executors.newFixedThreadPool(Constant.idfthreadNum);  
		
		List<Callable<Map<String, Double>>> taskPool=new ArrayList<>();
		ArrayList<Future<Map<String, Double>>> futures=new ArrayList<>();
		
		for(int i=1;i<=Constant.idfthreadNum;i++){
			CalculateIdf task = new CalculateIdf(entity,wordsList.subList(eachSize*(i-1), Math.min(eachSize*i, wordsList.size())));
			taskPool.add(task);
		}
		for(int i=0;i<taskPool.size();i++){
			Future<Map<String, Double>> f=es.submit(taskPool.get(i));
			futures.add(f);
			
		}
		for(int i=0;i<futures.size();i++){
			try {
				entity.idfMap.putAll(futures.get(i).get());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		es.shutdown();
		
	}
	
	private static Map<String, Integer> containWordOfAllDocNumber(Map<String, Map<String, Integer>> allSegsMap) {
		Map<String, Integer> containWordOfAllDocNumberMap = new HashMap<String, Integer>();

		if (allSegsMap == null || allSegsMap.size() == 0) {
			return containWordOfAllDocNumberMap;
		}

		Set<String> fileList = allSegsMap.keySet();
		for (String filePath : fileList) {
			Map<String, Integer> fileSegs = allSegsMap.get(filePath);
			// 获取该文件分词为空或为0,进行下一个文件
			if (fileSegs == null || fileSegs.size() == 0) {
				continue;
			}
			// 统计每个分词的idf
			Set<String> segs = fileSegs.keySet();
			for (String seg : segs) {
				if (containWordOfAllDocNumberMap.containsKey(seg)) {
					containWordOfAllDocNumberMap.put(seg, containWordOfAllDocNumberMap.get(seg) + 1);
				} else {
					containWordOfAllDocNumberMap.put(seg, 1);
				}
			}

		}
		return containWordOfAllDocNumberMap;
	}
	

}
