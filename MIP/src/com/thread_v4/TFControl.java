package com.thread_v4;

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
		
		List<Callable<TfHelper>> taskPool=new ArrayList<>();
		ArrayList<Future<TfHelper>> futures=new ArrayList<>();
		
		for(int i=1;i<=Constant.tfthreadNum;i++){
			CalculateTF task = new CalculateTF(1+(i-1)*eachSize, eachSize*i, entity);
			taskPool.add(task);
		}
		for(int i=0;i<taskPool.size();i++){
			Future<TfHelper> f=es.submit(taskPool.get(i));
			futures.add(f);
		}
		for(int i=0;i<futures.size();i++){
			try {
				TfHelper temp=futures.get(i).get();
				entity.allSegsMap.putAll(temp.resultSegMap);
				entity.lengthOfDoc.putAll(temp.resultlengthOfDoc);
				entity.totalLength+=temp.resultlenOfContent;
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
		for(int index:entity.lengthOfDoc.keySet()){
			entity.tmp.put(index, 1-Constant.s1+Constant.s1*entity.lengthOfDoc.get(index)/entity.avgl);
		}
	}

}
