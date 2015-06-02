package pers.sam.czsc.util;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import pers.sam.czsc.dto.MergeLineDTO;
import pers.sam.czsc.dto.StrokeDTO;
import pers.sam.czsc.dto.PartingResultDTO;
import pers.sam.data.InsertPartingInfoDAO;
import pers.sam.dto.PartingInfoDTO;
import pers.sam.util.PeriodUtil;
import pers.sam.util.StockDateUtil;

/**
 * 分笔、分段使用的demo
 * @author lizandeng(Sam Lee)
 * @version 2015-5-31 下午08:39:27
 */
public class ZenTheoryUtilDemo {
	
	private static Logger logger=Logger.getLogger(ZenTheoryUtilDemo.class);
	
	public static void main(String args[]) throws ClassNotFoundException, SQLException, ParseException{
			
//		String stockCode ="601600";
//		String period = PeriodUtil.PERIOD_30_MIN;
//		String startDay = "2013-10-11";
//		String endDay = "2015-04-13";
		
		Date currentDate = new Date();
		
		String stockCode ="600000";
		String period = PeriodUtil.PERIOD_DAY;
//		String startDay = "2014-04-08";
		String startDay = "2014-10-27";
		String endDay = "2015-06-02";
		
		logger.info("*******************ZenTheoryUtilDemo begin***********************************************");
		logger.info("获取"+stockCode+" "+period+"数据"+",查询区间["+startDay+"~"+endDay+"]");
		
		String partingDirection = "";
		String partingStatus = "";
		
		//得到结果
		PartingResultDTO strokeResultDTO = ZenTheoryUtil.getStrokeResultByBruceForce(stockCode, period,
				startDay, endDay);
		
		List<StrokeDTO> strokeList = strokeResultDTO.getStrokeList();
		List<MergeLineDTO> mergeLineList = strokeResultDTO.getMergeLineList();
		boolean sectionResultArray[] = strokeResultDTO.getSectionResultArray() ;
		
		StrokeDTO lastStroke = strokeList.get(strokeList.size()-1);
		
		int partingIndex = 0;
		for(int i = mergeLineList.size()-1;i>=0;i--){
			if(sectionResultArray[i]==true){
				partingIndex = i;
				break;
			}
		}
		
		MergeLineDTO mLine = mergeLineList.get(partingIndex);
		MergeLineDTO rLine = mergeLineList.get(partingIndex+1);
		
		if(currentDate.compareTo(rLine.getEndTime())>0){
			//延续中
			partingStatus = PartingInfoDTO.PARTING_EXTENDING;
			if(mLine.getIsPeak().equals("Y")){
				partingDirection = PartingInfoDTO.DIRECTION_DOWN;
			}else{
				partingDirection = PartingInfoDTO.DIRECTION_UP;
			}
		}else if(currentDate.compareTo(mLine.getEndTime())>0){
			//分型构成中
			partingStatus = PartingInfoDTO.PARTING_FORMING;
			if(mLine.getIsPeak().equals("Y")){
				partingDirection = PartingInfoDTO.DIRECTION_UP;
			}else{
				partingDirection = PartingInfoDTO.DIRECTION_DOWN;
			}
		}
		
		//插入当前分笔状态
		PartingInfoDTO partingInfo = new PartingInfoDTO();
		partingInfo.setStockCode(stockCode);
		partingInfo.setPeriod(period);
		partingInfo.setPartingDate(StockDateUtil.SDF_TIME
				.parse(StockDateUtil.SDF_TIME.format(mLine.getBeginTime())));
		partingInfo.setPartingDirection(partingDirection);
		partingInfo.setPartingStatus(partingStatus);
		
		InsertPartingInfoDAO.insert(partingInfo);
		
		logger.info("*******************ZenTheoryUtilDemo end***********************************************");
		
	}
}
