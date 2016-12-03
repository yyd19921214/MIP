package com.thread_v4;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

public class CalculateTF implements Callable<TfHelper> {
	
	private int startIndex;//用来标记需要本线程处理的文章的下标
	
	private int endIndex;//用来标记需要
	
	private TfIdfEntity tfidfentity;
	
//	private Map<Integer, Map<String, Integer>> resultSegMap;//用来存储segMap
//	
//	private Map<Integer, Integer> resultlengthOfDoc;//用来存储lengthOfDoc（记录某一篇文章的长度）
//	
//	private int resultlenOfContent;//用来存储lenOfContent(记录整个文本集合的长度)
	
//	ArrayList<Object> result;
	private TfHelper helper=new TfHelper();
	

	public CalculateTF(int startIndex,int endIndex,TfIdfEntity tfidfentity) {
		this.startIndex=startIndex;
		this.endIndex=endIndex;
		this.tfidfentity=tfidfentity;
		
		helper.resultSegMap=new HashMap<Integer, Map<String, Integer>>(tfidfentity.articles.size()/Constant.tfthreadNum+10);
		helper.resultlengthOfDoc=new HashMap<>(tfidfentity.articles.size()/Constant.tfthreadNum+10);
//		result=new ArrayList<>();
	}


	

	public TfHelper call() throws Exception {
		for (int i = startIndex; i <= endIndex; i++) {
			helper.resultSegMap.put(i, segString(i, tfidfentity.articles.get(i - 1)));// 注意这里是i-1
		}
		
		return helper;
	}
	
	private  Map<String, Integer> segString(int contIndex,String content) {
		// 分词
		Reader input = new StringReader(content);
		// 智能分词关闭（对分词的精度影响很大）
		IKSegmenter iks = new IKSegmenter(input, true);
		Lexeme lexeme = null;
		Map<String, Integer> words = new HashMap<String, Integer>();
		int lenOfContent=0;//该篇文章本身的长度
		try {
			String tmp = null;
			try {
				while ((lexeme = iks.next()) != null) {
					tmp = lexeme.getLexemeText();
					if (StopDic.stopWordSet.contains(tmp)) {
						continue;
					} else {
						if (words.containsKey(lexeme.getLexemeText())) {
							words.put(tmp, words.get(tmp) + 1);
						} else {
							words.put(tmp, 1);
						}
						helper.resultlenOfContent++;//增加整个文本集合的长度
						lenOfContent++;
					}
				}
				helper.resultlengthOfDoc.put(contIndex, lenOfContent);
			} catch (NullPointerException npex) {
				helper.resultlengthOfDoc.put(contIndex, lenOfContent);
				return words;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return words;

	}

	


}

class TfHelper{
    public Map<Integer, Map<String, Integer>> resultSegMap;//用来存储segMap
	
	public Map<Integer, Integer> resultlengthOfDoc;//用来存储lengthOfDoc（记录某一篇文章的长度）
	
	public int resultlenOfContent;//用来存储lenOfContent(记录整个文本集合的长度)
}
