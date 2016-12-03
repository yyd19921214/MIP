package com.thread_v3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TfIdfEntity {
	
	public  Map<String, Map<String, Double>> allTfMap = new HashMap<String, Map<String, Double>>();

	/**
	 * 所有文件分词结果.key:文件名,value:该文件分词统计
	 */
	public  Map<String, Map<String, Integer>> allSegsMap = new HashMap<String, Map<String, Integer>>();

	/**
	 * 所有词语的idf结果.key:词语名称,value:词w在整个文档集合中的逆向文档频率idf (Inverse Document
	 * Frequency)，即文档总数n与词w所出现文件数docs(w, D)比值的对数
	 */
	public  Map<String, Double> idfMap = new HashMap<String, Double>();

	/**
	 * 统计包含单词的文档数 key:单词 value:包含该词的文档数
	 */
	public   Map<String, Integer> containWordOfAllDocNumberMap = new HashMap<String, Integer>();

	/**
	 * 统计单词的TF-IDF key:文件名 value:该文件tf-idf
	 */
	public  Map<String, Map<String, Double>> tfIdfMap = new HashMap<String, Map<String, Double>>();


//
	
	public volatile long totalLength;//总共的词数
	
	public volatile double avgl;//平均长度
	
	public  Map<String,Integer> lengthOfDoc=new HashMap<>();//用来记录每篇文章的长度
	
	public List<String> articles =new ArrayList<>();
	
	public Map<String,Double> resultMap=new HashMap<>();
	
	public Map<String,Double> tmp=new HashMap<>();//用来存储分母
	
	public double[][] resultMat;

	
	public TfIdfEntity() {
		// TODO Auto-generated constructor stub
	}
	

}
