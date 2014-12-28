package pers.sam.czsc.test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import pers.sam.dto.MergeLineDTO;
import pers.sam.dto.SegmentDTO;
import pers.sam.dto.StockKLinePriceDTO;
import pers.sam.util.SqliteDataUtil;

public class ZenTheoryTest {
	
	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	public static void main(String args[]){
		
		String stockCode ="000100";
		
		List<StockKLinePriceDTO> priceList = SqliteDataUtil.getDayStockData(stockCode);
		
		/**
		 * 资产初始化
		 */
		String lastAction = "sell";
		
		Double totalMoney = new Double(10000.00);
		Double assetValue = new Double(10000.00);
		
		Double stockValue = new Double(0);
		Double stockNum = new Double(0);
		Double cashValue = new Double(0);
		
		String trend = "down";//up
		
		/**
		 * K线合并、顶底分型信息
		 */
		List<MergeLineDTO> mergeLineList = new ArrayList();
		
		//从2004-2-6开始
		StockKLinePriceDTO priceDTO = priceList.get(5);
		MergeLineDTO mergeLineDTO = new MergeLineDTO();
		mergeLineDTO.setStickNumber(1);
		mergeLineDTO.setBeginTime(priceDTO.getBeginTime());
		mergeLineDTO.setEndTime(priceDTO.getEndTime());
		mergeLineDTO.setHigh(priceDTO.getHigh());
		mergeLineDTO.setLow(priceDTO.getLow());
		mergeLineList.add(mergeLineDTO);
		
		/**
		 * 开始模拟，交易过程
		 */
		for(int i = 6;i<priceList.size();i++){
			
			StockKLinePriceDTO today = priceList.get(i);
//			System.out.println(today.getBeginTime()+" "+today.getEndTime());
			
			//1.K线合并,假如存在包含关系
			MergeLineDTO lastMLineDTO = getLastMergeLineDTO(mergeLineList);
			if(isInclusive(lastMLineDTO,
					today.getHigh(),today.getLow())){
				merge(lastMLineDTO,today,trend);
			}else{//无合并，则放到resultList中
				if(isUp(lastMLineDTO,today.getHigh(),today.getLow())){
				    trend ="up";
				}else if(isDown(lastMLineDTO,today.getHigh(),today.getLow())){
				   	trend ="down";
				}
				MergeLineDTO thisMLineDTO = new MergeLineDTO();
				thisMLineDTO.setStickNumber(1);
				thisMLineDTO.setBeginTime(today.getBeginTime());
				thisMLineDTO.setEndTime(today.getEndTime());
				thisMLineDTO.setHigh(today.getHigh());
				thisMLineDTO.setLow(today.getLow());
				mergeLineList.add(thisMLineDTO);
			}
			
			//2.处理顶底分型
			findPeakAndBottomByModel1(mergeLineList);
			
			//3.笔 划分
//			divideSection(mergeLineList);
			
			//4.线段划分
//			List segmentList = getSegment(mergeLineList);
		}
		
		System.out.println(mergeLineList.size());
		divideSection(mergeLineList);
		
//		for(int i =0;i<mergeLineList.size();i++){
//			MergeLineDTO dto = mergeLineList.get(i);
//			
//			System.out.println(dto.getBeginTime()+"**"+dto.getIsBottom()+"**"+dto.getIsPeak());
//			
//			if(dto.getIsPeak().equals("Y")){
//				System.out.println(sdf.format(dto.getBeginTime())+"\t"+
//						sdf.format(dto.getEndTime())+"\t"+
//						"合并["+dto.getStickNumber()+"]条K线"+"\t"+
//						"顶["+dto.getLow()+"]["+dto.getHigh()+"]");
//			}
//			if(dto.getIsBottom().equals("Y")){
//				System.out.println(sdf.format(dto.getBeginTime())+"\t"+
//						sdf.format(dto.getEndTime())+"\t"+
//						"合并["+dto.getStickNumber()+"]条K线"+"\t"+
//						"底 ["+dto.getLow()+"]["+dto.getHigh()+"]");
//			}
//		}
	}
	
	/**
	 * 笔划分
	 * 寻找真正的顶分型，底分型
	 * 
	 */
	public static void divideSection(List<MergeLineDTO> mergeSticksList){
		
		String trend ="";
		
		boolean [] sectionArray = new boolean[mergeSticksList.size()];
		
		for(int i =0;i<sectionArray.length;i++){
			sectionArray[i] = false;
		}
		
		boolean flag = breathFirstSearch(mergeSticksList,sectionArray,0);
		
		System.out.println(flag);
		
		for(int i = 0;i<sectionArray.length;i++  ){
			if(sectionArray[i]==true){
				MergeLineDTO dto = mergeSticksList.get(i);
				if(dto.getIsPeak().equals("Y")){
					System.out.println(sdf.format(dto.getBeginTime())+"\t"+
							sdf.format(dto.getEndTime())+"\t"+
							"合并["+dto.getStickNumber()+"]条K线"+"\t"+
							"顶["+dto.getLow()+"]["+dto.getHigh()+"]");
				}
				if(dto.getIsBottom().equals("Y")){
					System.out.println(sdf.format(dto.getBeginTime())+"\t"+
							sdf.format(dto.getEndTime())+"\t"+
							"合并["+dto.getStickNumber()+"]条K线"+"\t"+
							"底 ["+dto.getLow()+"]["+dto.getHigh()+"]");
				}
			}
		}
		
	}
	
	/**
	 * 宽度优先搜索
	 * @param sectionArray
	 * @param index
	 */
	private static boolean breathFirstSearch(List<MergeLineDTO> mergeSticksList,
			boolean [] sectionArray,int index){
		
		if(index==sectionArray.length-1){
			//退出的两种情况：没有唯一解、或者已经找到分解
			
			if(!isRightPartition(mergeSticksList,sectionArray,index-1)){
				return false;
			}
			
			sectionArray[index]=true;
			if(isRightPartition(mergeSticksList,sectionArray,index)){
				return true;
			}
			
			sectionArray[index]=false;
			if(isRightPartition(mergeSticksList,sectionArray,index)){
				return true;
			}
			
			return false;
		}else if(isRightPartition(mergeSticksList,sectionArray,index-1)
				||index==0||index==1){
			//减枝
			sectionArray[index]=true;
			breathFirstSearch(mergeSticksList,sectionArray,index+1);
			
			sectionArray[index]=false;
			breathFirstSearch(mergeSticksList,sectionArray,index+1);
			
		}
		
		return false;
	}
	
	/**
	 * 判断是否到startIndex为止都是正确的分解
	 * @param mergeSticksList
	 * @param sectionArray
	 * @param startIndex
	 * @return
	 */
	private static boolean isRightPartition(List<MergeLineDTO> mergeSticksList,
			boolean [] sectionArray,int endIndex){
		
		MergeLineDTO thisValidDto = null;
		
		int lastIndex = 0;
		MergeLineDTO lastValidDto = null;
		
		for(int i = 0;i<=endIndex;i++){
			if(sectionArray[i]==true){
				thisValidDto = (MergeLineDTO)mergeSticksList.get(i);
			}else{
				continue;
			}
			
			if(lastValidDto!=null&&
					!validatePeakAndBottom(mergeSticksList,lastIndex,i)){
				return false;
			}
			lastValidDto = thisValidDto;
			lastIndex = i;
		}
		
		return true;
	}
	
	/**
	 * 校验是否满足一笔
	 */
	private static boolean validatePeakAndBottom(
			List<MergeLineDTO> mergeSticksList,int startIndex,int endIndex){
		
		//1.顶和底之间没有至少一K线，此处有争议
		if(endIndex - startIndex<4){
			return false;
		}
		
		//2.不满足顶必须接着底、或底必须接着顶
		MergeLineDTO startDTO = mergeSticksList.get(startIndex);
		MergeLineDTO endDTO = mergeSticksList.get(endIndex);
		if((startDTO.getIsPeak().equals("Y")&&endDTO.getIsPeak().equals("Y"))
				||(startDTO.getIsBottom().equals("Y")&&endDTO.getIsBottom().equals("Y"))){
			return false;
		}
		
		//3.顶底分别是笔中的最高和最低
		MergeLineDTO peakDTO = null;
		MergeLineDTO bottomDTO = null;
		if(startDTO.getIsPeak().equals("Y")){
			peakDTO = startDTO;
			bottomDTO = endDTO;
		}else{
			peakDTO = endDTO;
			bottomDTO = startDTO;
		}
		
		for(int i = startIndex;i<= endIndex;i++){
			MergeLineDTO dto = mergeSticksList.get(i);
			
			//存在更高的点位
			if(dto.getHigh()>peakDTO.getHigh()
					&&dto.getBeginTime()!=peakDTO.getBeginTime()){
				return false;
			}
			
			//存在更低的点位
			if(dto.getLow()<bottomDTO.getLow()
					&&dto.getBeginTime()!=bottomDTO.getBeginTime()){
				return false;
			}
		}
		
		//4.或许还需要判断顶底的区间是否不能重叠
		
		return true;
	}
	
	/**
	 * 获取上一条合并M Line
	 */
	public static MergeLineDTO getLastMergeLineDTO(List<MergeLineDTO> mergeLineList){
		
		return mergeLineList.get(mergeLineList.size()-1);
	}
	
	/**
	 * 获取上一条线段
	 */
	public static SegmentDTO getLastSegment(List<SegmentDTO> segmentList){
		return segmentList.get(segmentList.size()-1);
	}
	
	/**
	 * 线段划分
	 * @param mergeSticksList
	 * @return
	 */
	public static List getSegment(List<MergeLineDTO> mergeLineList){
		
		if(mergeLineList.size()<3){
			return null;
		}
		
		List segmentList = new ArrayList();
//		MergeLineDTO firstMergeDTO = (MergeLineDTO)mergeLineList.get(0);
//		
//		SegmentDTO firstSegmentDTO = new SegmentDTO();
//		firstSegmentDTO.setBeginTime(firstMergeDTO.getBeginTime());
//		firstSegmentDTO.setEndTime(firstMergeDTO.getEndTime());
//		firstSegmentDTO.setHigh(firstMergeDTO.getHigh());
//		firstSegmentDTO.setLow(firstMergeDTO.getLow());
//		firstSegmentDTO.setTrend("down");
//		segmentList.add(firstSegmentDTO);
//		
//		for(int i = 0;i<mergeLineList.size();i++){
//			MergeLineDTO dto = mergeLineList.get(i);
//			if(){
//				
//			}
//		}
//		
		return segmentList;
	}
		
	
	/**
	 * 寻找顶分型，底分型
	 * 
	 */
	public static void findPeakAndBottomByModel1(List<MergeLineDTO> mergeSticksList){
		
		if(mergeSticksList.size()<3){
			return;
		}
		
		//顶和底不能共用K线,dist作为跳步
		int dist = 1;
		for(int i = 1;i<mergeSticksList.size()-1;i++){
			
			if(dist == 1){
				MergeLineDTO firstDTO = (MergeLineDTO) mergeSticksList.get(i-1);
				MergeLineDTO middleDTO = (MergeLineDTO) mergeSticksList.get(i);
				MergeLineDTO lastDTO = (MergeLineDTO) mergeSticksList.get(i+1);
				
				if(middleDTO.getHigh()>Math.max(
					  firstDTO.getHigh(), lastDTO.getHigh())
				   &&middleDTO.getLow()>Math.max(
						firstDTO.getLow(), lastDTO.getLow())){
					middleDTO.setIsPeak("Y");	
				   dist = 3;
				}
				if(middleDTO.getHigh()<Math.min(
						  firstDTO.getHigh(), lastDTO.getHigh())
				   &&middleDTO.getLow()<Math.min(
								firstDTO.getLow(), lastDTO.getLow())){
					middleDTO.setIsBottom("Y");
					dist = 3;
				}
			}else{
				dist--;
				continue;
			}
		}
	}
	
	/**
	 * 存在包含关系，则合并
	 * 方向向上：高点的高点，低点的高点
	 * 方向向下：高点的低点，低点的低点
	 * @param mergeSticksDTO
	 * @param nextDTO
	 */
	public static void merge(MergeLineDTO mergeLineDTO,StockKLinePriceDTO todayDTO,String trend){
		
		if(trend.equals("up")){
			mergeLineDTO.setHigh(
					Math.max(mergeLineDTO.getHigh(), todayDTO.getHigh()));
			mergeLineDTO.setLow(
					Math.max(mergeLineDTO.getLow(), todayDTO.getLow()));
			mergeLineDTO.setEndTime(todayDTO.getEndTime());
			
		}else if(trend.equals("down")){
			mergeLineDTO.setHigh(
					Math.min(mergeLineDTO.getHigh(), todayDTO.getHigh()));
			mergeLineDTO.setLow(
					Math.min(mergeLineDTO.getLow(), todayDTO.getLow()));
			mergeLineDTO.setEndTime(todayDTO.getEndTime());
		}
		mergeLineDTO.setEndTime(todayDTO.getEndTime());
		mergeLineDTO.setStickNumber(mergeLineDTO.getStickNumber()+1);
		
	}	
	
	/**
	 * 是否有包含关系
	 */
	public static boolean isInclusive(MergeLineDTO dto,Double highPrice,Double lowPrice){
		
		if((dto.getHigh().compareTo(highPrice)>=0
				&&dto.getLow().compareTo(lowPrice)<=0)
		   ||(dto.getHigh().compareTo(highPrice)<=0
					&&dto.getLow().compareTo(lowPrice)>=0))
			return true;
		
		return false;
	}
	
	/**
	 * 向下
	 * @param dto
	 * @param highPrice
	 * @param lowPrice
	 * @return
	 */
	public static boolean isDown(MergeLineDTO dto,Double highPrice,Double lowPrice){
		
		if(dto.getHigh()>highPrice&&
				dto.getLow()>lowPrice)
		   return true;
		else
		   return false;
	}
	
	/**
	 * 向上
	 * @param dto
	 * @param highPrice
	 * @param lowPrice
	 * @return
	 */
	public static boolean isUp(MergeLineDTO dto,Double highPrice,Double lowPrice){
		if(dto.getHigh()<highPrice&&
				dto.getLow()<lowPrice)
		   return true;
		else
		   return false;
	}
	
}
