package com.thread_v3;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class TFControl {
	
	private TfIdfEntity entity;

	public TFControl(TfIdfEntity entity) {
		this.entity=entity;
	}
	
	public void calculateTF(){
		
		entity.articles=ProcessText.readTxtFileAndProcess(Constant.totalTextPath, false);//读入文本
		StopDic.init();//初始化停词
		
		int articleSize=entity.articles.size();
		int eachSize=articleSize/Constant.tfthreadNum;
		
		ExecutorService es = Executors.newFixedThreadPool(Constant.tfthreadNum);  
		
		List<Callable<ArrayList<Object>>> taskPool=new ArrayList<>();
		ArrayList<Future<ArrayList<Object>>> futures=new ArrayList<>();
		
		for(int i=1;i<=Constant.tfthreadNum;i++){
			CalculateTF task = new CalculateTF(1+(i-1)*eachSize, eachSize*i, entity);
			taskPool.add(task);
		}
		for(int i=0;i<taskPool.size();i++){
			Future<ArrayList<Object>> f=es.submit(taskPool.get(i));
			futures.add(f);
		}
		for(int i=0;i<futures.size();i++){
			try {
				ArrayList<Object> temp=futures.get(i).get();
				entity.allSegsMap.putAll((Map<String, Map<String, Integer>>) temp.get(0));
				entity.lengthOfDoc.putAll((Map<String, Integer>) temp.get(1));
				entity.totalLength+=(int)temp.get(2);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		
		es.shutdown();
		entity.avgl=(entity.totalLength+0d)/entity.articles.size();//计算平均长度
		for(String str:entity.lengthOfDoc.keySet()){
			entity.tmp.put(str, 1-Constant.s1+Constant.s1*entity.lengthOfDoc.get(str)/entity.avgl);
		}
	}

}
