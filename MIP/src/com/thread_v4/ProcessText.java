package com.thread_v4;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import com.thread_v3.Constant;

/**
 * 
 * @author yangyudong 将文本读入并且去除文本中的空格和词性标注
 */
public class ProcessText {

	public ProcessText() {
		// TODO Auto-generated constructor stub
	}

	public static List<String> readTxtFileAndProcess(String filePath, boolean ifProcess) {
		List<String> res = new ArrayList<>();
		try {
			String encoding = "GBK";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					if (lineTxt != null && !lineTxt.equals("")) {
						if (ifProcess == true) {
							res.add(process(lineTxt));// 对文本进行简单的清洗
						}
						else{
							res.add(lineTxt);
						}
					}
				}
				read.close();
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
		return res;
	}
	
	public static List<String> readTxtFileAndProcess2(String filePath, boolean ifProcess) {
		List<String> res = new ArrayList<>();
		try {
			String encoding = "GBK";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				StringBuffer sb=new StringBuffer();
				while ((lineTxt = bufferedReader.readLine()) != null) {
					if (lineTxt != null && !lineTxt.equals("")) {
							sb.append(process(lineTxt));
					}
					else{
						if(sb.length()!=0){
							res.add(sb.toString());
							sb.delete(0,sb.length());
						}
						
					}
				}
				res.add(sb.toString());
				read.close();
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
		return res;
	}

	private static String process(String line) {
		String[] splits = line.split("  ");
		StringBuffer sb = new StringBuffer();
		for (int i = 1; i < splits.length; i++) {
			sb.append(splits[i].split("/")[0]);
		}
		return sb.toString();
	}

	public static void writeFile(String filePath, String content) {
		File file = new File(filePath);
		file.delete();
		try {
			file.createNewFile();
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "GBK"));
			writer.write(content);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		List<String> strs = readTxtFileAndProcess2("src/1998-01.txt", true);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < strs.size(); i++) {
			sb.append(i + 1 + ":").append(strs.get(i).replace("〓", "")).append("\r\n");
		}
		writeFile("src/1998-01-new-processed.txt", sb.toString());
	}
	
	public static void init() {
		List<String> strs = readTxtFileAndProcess2(Constant.originalPath, true);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < strs.size(); i++) {
			sb.append(i + 1 + ":").append(strs.get(i).replace("〓", "")).append("\r\n");
		}
		writeFile(Constant.totalTextPath, sb.toString());
	}

}
