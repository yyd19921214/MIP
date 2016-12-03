package com.thread_v3;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class CalculateSimilarity2 {

	private TfIdfEntity tfidfentity;

	// 指示当前处理的行下标
	private AtomicInteger rowpoint = new AtomicInteger(0);

	public CalculateSimilarity2(TfIdfEntity tfidfentity) {
		this.tfidfentity = tfidfentity;
	}

	private double calculateTextSimilarity(String first, String second) {

		Map<String, Integer> mapFirst = tfidfentity.allSegsMap.get(first);
		Map<String, Integer> mapSecond = tfidfentity.allSegsMap.get(second);
		Set<String> setFirst = mapFirst.keySet();// 获取文档一的所有词汇
		Set<String> setSecond = mapSecond.keySet();// 获取文档二的所有词汇
		double result = 0.00;
		double firstPro;
		double secondPro;
		double tmp1;
		double tmp2 = tfidfentity.tmp.get(first);
		double tmp3;
		double tmp4 = tfidfentity.tmp.get(second);

		for (String str : setFirst) {
			if (setSecond.contains(str)) {
				int cnt1 = mapFirst.get(str);
				if (cnt1 <= 1000) {

					tmp1 = 1 + Math.log((1 + LogArray.logArray[cnt1]));
				} else {
					tmp1 = 1 + Math.log((1 + Math.log(cnt1)));
				}
				firstPro = tmp1 / tmp2;

				int cnt2 = mapSecond.get(str);
				if (cnt2 <= 1000) {
					tmp3 = 1 + Math.log((1 + LogArray.logArray[cnt2]));
				} else {
					tmp3 = 1 + Math.log((1 + Math.log(cnt2)));
				}
				secondPro = tmp3 / tmp4;

				result += firstPro * secondPro * tfidfentity.idfMap.get(str);
			}
		}

		return result;

	}

	public void doCalculate() {
		int index = 0;
		try {

			while (rowpoint.get() < tfidfentity.resultMat.length) {
				index = rowpoint.getAndIncrement();
				for (int j = index + 1; j < tfidfentity.resultMat.length; j++) {
					tfidfentity.resultMat[index][j] = calculateTextSimilarity(String.valueOf(index + 1),
							String.valueOf(j + 1));
				}
			}
		} catch (Exception e) {
		}

		// TODO Auto-generated method stub

	}

}

class CalculateHelper implements Callable<Boolean> {

	private CalculateSimilarity2 cal;

	private CountDownLatch latch;

	public CalculateHelper(CalculateSimilarity2 cal, CountDownLatch latch) {
		this.cal = cal;
		this.latch = latch;
	}

	@Override
	public Boolean call() throws Exception {
		cal.doCalculate();
		latch.countDown();
		// TODO Auto-generated method stub
		return true;
	}

}
