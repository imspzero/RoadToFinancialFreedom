package pers.sam.czsc.test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import pers.sam.czsc.core.DivideSectionInterface;
import pers.sam.czsc.core.FindPeakAndBottomInterface;
import pers.sam.czsc.core.FindSegmentInterface;
import pers.sam.czsc.core.impl.DivideSectionImpl1;
import pers.sam.czsc.core.impl.FindPeakAndBottomImpl2;
import pers.sam.czsc.core.impl.FindSegmentImpl3;
import pers.sam.czsc.dto.MergeLineDTO;
import pers.sam.czsc.dto.TouchDTO;
import pers.sam.czsc.util.ZenTheoryUtil;
import pers.sam.dto.StockKLinePriceDTO;
import pers.sam.util.GetStockDataFromSqliteUtil;
import pers.sam.util.InsertStockDataToSqliteUtil;
import pers.sam.util.StockDateUtil;

/**
 * 999999 30分钟分笔
 * @author Administrator
 */
public class Test999999_30min {
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String stockCode ="999999";
		
		//插入数据
		String filePath = "C:\\Documents and Settings\\Administrator\\桌面\\国信数据导出\\999999_30min.csv";
		InsertStockDataToSqliteUtil.insert30MinStockData(stockCode, filePath);
		
		List<StockKLinePriceDTO> priceList = 
			GetStockDataFromSqliteUtil.getStock30MinDataByDay(stockCode,"2014-04-10","2015-05-16");
		
		System.out.println(priceList.size());
		
		/**
		 * K线合并、顶底分型信息
		 */
		List<MergeLineDTO> mergeLineList = new ArrayList();
		
		
//		//补充虚拟的顶和底，减少后续的迭代量
//		MergeLineDTO firstMLineDTO = new MergeLineDTO();
//		firstMLineDTO.setStickNumber(1);
//		firstMLineDTO.setBeginTime(sdf.parse("1990-1-1"));
//		firstMLineDTO.setEndTime(sdf.parse("1990-1-1"));
//		firstMLineDTO.setHigh(0.0);
//		firstMLineDTO.setLow(0.0);
//		firstMLineDTO.setIsBottom("Y");
//		firstMLineDTO.setIsPeak("N");
//		mergeLineList.add(firstMLineDTO);
		
		//从1990-01-01开始
		StockKLinePriceDTO priceDTO = priceList.get(0);
		MergeLineDTO mergeLineDTO = new MergeLineDTO();
		mergeLineDTO.setStickNumber(1);
		mergeLineDTO.setBeginTime(priceDTO.getBeginTime());
		mergeLineDTO.setEndTime(priceDTO.getEndTime());
		mergeLineDTO.setHigh(priceDTO.getHigh());
		mergeLineDTO.setLow(priceDTO.getLow());
		mergeLineDTO.getMemberList().add(priceDTO);
		mergeLineList.add(mergeLineDTO);
		
		String trend = "up";
		
		/**
		 * 开始模拟，交易过程
		 */
		
		FindPeakAndBottomInterface findPeakAndBottomIntf
	 		= new FindPeakAndBottomImpl2();
		
		for(int i = 1;i<priceList.size();i++){
			
			StockKLinePriceDTO today = priceList.get(i);
//			System.out.println(today.getBeginTime()+" "+today.getEndTime());
			
			//1.K线合并,假如存在包含关系
			MergeLineDTO lastMLineDTO = ZenTheoryUtil.getLastMergeLineDTO(mergeLineList);
			if(ZenTheoryUtil.isInclusive(lastMLineDTO,
					today.getHigh(),today.getLow())){
				ZenTheoryUtil.merge(lastMLineDTO,today,trend);
			}else{//无合并，则放到resultList中
				if(ZenTheoryUtil.isUp(lastMLineDTO,today.getHigh(),today.getLow())){
				    trend ="up";
				}else if(ZenTheoryUtil.isDown(lastMLineDTO,today.getHigh(),today.getLow())){
				   	trend ="down";
				}
				MergeLineDTO thisMLineDTO = new MergeLineDTO();
				thisMLineDTO.setStickNumber(1);
				thisMLineDTO.setBeginTime(today.getBeginTime());
				thisMLineDTO.setEndTime(today.getEndTime());
				thisMLineDTO.setHigh(today.getHigh());
				thisMLineDTO.setLow(today.getLow());
				thisMLineDTO.getMemberList().add(today);
				mergeLineList.add(thisMLineDTO);
			}
			
			//2.处理顶底分型
			findPeakAndBottomIntf.findPeakAndBottom(mergeLineList);
//			ZenTheoryUtil.findPeakAndBottomByModel2(mergeLineList);
		}
		
		System.out.println(mergeLineList.size());
		
		for(int i =0;i<mergeLineList.size();i++){
			MergeLineDTO dto = mergeLineList.get(i);
			
			System.out.println(i+": "+StockDateUtil.SDF_TIME.format(dto.getBeginTime())+" -- "+
					StockDateUtil.SDF_TIME.format(dto.getEndTime())+"**"+dto.getIsBottom()+"**"+dto.getIsPeak()+"**"+dto.getStickNumber());
		}
		
		//3.分笔
		DivideSectionInterface divideSectionIntf = new DivideSectionImpl1();
		boolean sectionResultArray[] = divideSectionIntf.divideSection(mergeLineList);
		
//		boolean sectionResultArray[] = ZenTheoryUtil.divideSection(mergeLineList);
		
		List <TouchDTO>touchList = new ArrayList();
		TouchDTO touch = null;
		for(int i = 0;i<mergeLineList.size();i++){
			if(sectionResultArray[i]==true){
				if(touch == null){
					touch = new TouchDTO();
					touch.setStartMLine(mergeLineList.get(i));
				}else{
					touch.setEndMLine(mergeLineList.get(i));
					
					if(touch.getStartMLine().getIsBottom().equals("Y")
							&&touch.getEndMLine().getIsPeak().equals("Y")){
						touch.setDirection("up");
					}else if(touch.getStartMLine().getIsPeak().equals("Y")
							&&touch.getEndMLine().getIsBottom().equals("Y")){
						touch.setDirection("down");
					}
					touchList.add(touch);
					
					touch = new TouchDTO();
					touch.setStartMLine(mergeLineList.get(i));
				}
			}
		}
		
		System.out.println("--------------分笔-------------");
		for(int i = 0;i<touchList.size();i++){
			TouchDTO touchDTO = touchList.get(i);
			System.out.println(StockDateUtil.SDF_TIME.format(touchDTO.getStartMLine().getBeginTime()) + "  "
					+ StockDateUtil.SDF_TIME.format(touchDTO.getEndMLine().getEndTime()) + " "
					+ touchDTO.getDirection());
		}
		
		System.out.println("--------------分段-------------");
//		ZenTheoryUtil.findSegment(touchList);
		
		FindSegmentInterface findSegmentIntf = new FindSegmentImpl3();
		findSegmentIntf.findSegment(touchList);
		
	}

}
