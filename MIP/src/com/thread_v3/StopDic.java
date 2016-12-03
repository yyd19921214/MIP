package com.thread_v3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class StopDic {
	
//	  String stopWordPath = Constant.stopWordPath;

	public static Set<String> stopWordSet = new HashSet<String>();


	public StopDic() {
		// TODO Auto-generated constructor stub
	}
	
	public static void init(){
		BufferedReader StopWordFileBr;
		try {
			StopWordFileBr = new BufferedReader(
					new InputStreamReader(new FileInputStream(new File(Constant.stopWordPath)), "GBK"));

			String stopWord = null;
			for (; (stopWord = StopWordFileBr.readLine()) != null;) {
				stopWord = stopWord.trim();
				stopWordSet.add(stopWord);
			}
			StopWordFileBr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
