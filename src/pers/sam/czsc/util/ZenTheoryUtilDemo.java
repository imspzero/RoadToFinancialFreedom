package pers.sam.czsc.util;

import java.util.List;

import org.apache.log4j.Logger;

import pers.sam.czsc.dto.TouchDTO;
import pers.sam.util.PeriodUtil;

/**
 * 分笔、分段使用的demo
 * @author lizandeng(Sam Lee)
 * @version 2015-5-31 下午08:39:27
 */
public class ZenTheoryUtilDemo {
	
	private static Logger logger=Logger.getLogger(ZenTheoryUtilDemo.class);
	
	public static void main(String args[]){
			
		String stockCode ="601600";
		String period = PeriodUtil.PERIOD_30_MIN;
		String startDay = "2013-10-11";
		String endDay = "2015-04-13";
		logger.info("*******************ZenTheoryUtilDemo begin***********************************************");
		logger.info("获取"+stockCode+" "+period+"数据"+",查询区间["+startDay+"~"+endDay+"]");
		
		List<TouchDTO> touchList = ZenTheoryUtil.getTouchListByBruceForce(stockCode,period,startDay,endDay);
		
		logger.info("*******************ZenTheoryUtilDemo end***********************************************");
		
	}
}
