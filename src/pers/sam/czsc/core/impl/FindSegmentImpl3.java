package pers.sam.czsc.core.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import pers.sam.czsc.core.FindSegmentInterface;
import pers.sam.dto.FeatureElementDTO;
import pers.sam.dto.TouchDTO;
import pers.sam.util.StockDateUtil;

/**
 * 线段划分--实现三
 * 修正第一第二元素的问题、修正第一第二种情况下的分法
 * 
 * @author Administrator
 *
 */
public class FindSegmentImpl3 implements FindSegmentInterface {
	
	public void findSegment(List<TouchDTO> touchList) {
		
		List<Integer> resultIndexList = new ArrayList<Integer>();
		
		LinkedList<TouchDTO> processList = new LinkedList<TouchDTO>();
		
		for(int i=0;i<touchList.size();i++){
			processList.add((touchList.get(i)).clone());
		}
		
		//取得一开始线段的方向
		String segmentDirection = "";
		String nextSegmentDirection="";
		TouchDTO startTouchDTO = touchList.get(0);
		if(startTouchDTO.getDirection().equals("up")){
			segmentDirection = "up";
		}else{
			segmentDirection = "down";
		}
		
		//上一线段的结束位置,初始为0
		int lastSegmentEndIndex = 0;
		
		//逐一检视特征序列
		boolean flag = true;
		while(flag){//一条线段，迭代一次。
			
			flag = false;
			
			if(lastSegmentEndIndex+3>processList.size()){
				flag = false;
				break;
			}
			
			//一线段至少有三笔，所以从三个后的元素开始检查
			for(int i=lastSegmentEndIndex+3;i<processList.size();i=i+2){
				//假设i是分界点
				
				//第一元素：第一元素就是以该假设转折点前线段的最后一个特征元素（次高）
				List<FeatureElementDTO> beforeElementList = mergeFeatureElement(
						processList, segmentDirection.equals("up") ? "down"
								: "up", lastSegmentEndIndex, i-1);
				
				FeatureElementDTO firstElement = getFirstElement(
						beforeElementList, segmentDirection);
				
				// 找到第二第三元素（特征序列合并后，即标准特征序列）
				List<FeatureElementDTO> afterElementList = mergeFeatureElement(
						processList, segmentDirection.equals("up") ? "down"
								: "up", i, processList.size()-1);
				if(afterElementList.size()<2){
					flag = false;
					break;
				}
				
				FeatureElementDTO secondElement = afterElementList.get(0);
				FeatureElementDTO thirdElement = afterElementList.get(1);
				
				//是否存在分型
				if(segmentDirection.equals("up")){
					//顶分型
					if(!(firstElement.getHigh()<secondElement.getHigh()
							&&thirdElement.getHigh()<secondElement.getHigh()
							&&thirdElement.getLow()<secondElement.getLow())){
						flag = false;
						continue;//不存在直接跳出
					}
				}else if(segmentDirection.equals("down")){
					//底分型
					if(!(firstElement.getLow()>secondElement.getLow()
							&&thirdElement.getLow()>secondElement.getLow()
							&&thirdElement.getHigh()>secondElement.getHigh())){
						flag = false;
						continue;//不存在直接跳出
					}
				}
				
				//区分第一和第二种情况
				if(!existsGapBetweenFirstAndSecondElement(segmentDirection,firstElement,processList.get(i))){
					//是第一种情况，第一元素和第二元素无缺口
					//存在并且划分成功
					flag= true;
					
					//合并第二第三元素中有包含关系的分笔，从后往前处理
					mergeProcessList(processList, thirdElement,
							segmentDirection.equals("up") ? "down" : "up");
					mergeProcessList(processList, secondElement,
							segmentDirection.equals("up") ? "down" : "up");
					
					resultIndexList.add(i);
					lastSegmentEndIndex = i;
					
					nextSegmentDirection = segmentDirection.equals("up")?"down":"up";
					
					System.out.println("线段端点: "+
							StockDateUtil.SDF_TIME.format(secondElement.getBeginTime()));
					
//					System.out.println("线段端点: "+
//							StockDateUtil.SDF_TIME.format(touchList.get(i).getStartMLine().getBeginTime())+"~"+
//							StockDateUtil.SDF_TIME.format(touchList.get(i).getEndMLine().getEndTime())+" point ");
					
				}else{
					//是第二种情况，第一元素和第二元素有缺口
					//需要见识第二特征序列是否出现分型
					String secondSegmentDirection = segmentDirection.equals("up")?"down":"up";
					
					// 获取第二特征序列
					List<FeatureElementDTO> secondElementList = mergeFeatureElement(
							touchList,
							secondSegmentDirection.equals("up") ? "down" : "up",
							i, touchList.size() - 1);
					
					if(secondElementList.size()<3){//少于三个，分型无从考究
						flag = false;
						continue;
					}
					
					for(int j = 1;j<secondElementList.size()-1;j++){
						FeatureElementDTO aDTO = secondElementList.get(j-1);
						FeatureElementDTO bDTO = secondElementList.get(j);
						FeatureElementDTO cDTO = secondElementList.get(j+1);
						
						if(secondSegmentDirection.equals("down")){
							//第二特征分型是底分型
							if(bDTO.getLow()<aDTO.getLow()&&bDTO.getLow()<cDTO.getLow()
								&&bDTO.getHigh()<aDTO.getHigh()&&bDTO.getHigh()<cDTO.getHigh()){
								
								
								flag = true;
							}
						}else if(secondSegmentDirection.equals("up")){
							//第二特征分型是顶分型
							if(bDTO.getLow()>aDTO.getLow()&&bDTO.getLow()>cDTO.getLow()
								&&bDTO.getHigh()>aDTO.getHigh()&&bDTO.getHigh()>cDTO.getHigh()){
								flag = true;
							}
						}
						
						if(flag == true){
							
							//合并第二第三元素中有包含关系的分笔，从后往前处理
							mergeProcessList(
									processList,
									cDTO,
									secondSegmentDirection.equals("up") ? "down"
											: "up");
							mergeProcessList(
									processList,
									bDTO,
									secondSegmentDirection.equals("up") ? "down"
											: "up");
							
							resultIndexList.add(i);
							System.out.println("线段端点: "+
									StockDateUtil.SDF_TIME.format(secondElement.getBeginTime()));
							
							resultIndexList.add(j);
							System.out.println("线段端点: "+
									StockDateUtil.SDF_TIME.format(bDTO.getBeginTime()));
							
							//
							lastSegmentEndIndex = j;
							nextSegmentDirection = segmentDirection;
							break;
						}
					}
				}
				
				if(flag == true){//找到线段,第一种情况新线段反向，第二种请，新线段与原线段同向
					segmentDirection = nextSegmentDirection;
					break;
				}
			}
			
			if(flag == false){//
				break;
			}
		}
		
		//输出结果
//		for(int i = 0;i<resultIndexList.size();i++){
//			int resutIndex = (Integer)resultIndexList.get(i);
//			
//			TouchDTO touchDTO = touchList.get(resutIndex);
//			System.out.println("线段于 "+
//					StockDateUtil.SDF_TIME.format(touchDTO.getStartMLine().getBeginTime())+"~"+
//					StockDateUtil.SDF_TIME.format(touchDTO.getEndMLine().getEndTime())+" point ");
//		}
		
	}
	
	/**
	 * 合并第二第三元素中有包含关系的分笔，从后往前处理
	 */
	public static void mergeProcessList(LinkedList<TouchDTO> processList,FeatureElementDTO featureElement,String featureDirection){
		
		List<Integer> touchIndexList = featureElement.getTouchIndexList();
		
		int startIndex = touchIndexList.get(0);
		int endIndex = touchIndexList.get(touchIndexList.size()-1);
		
		TouchDTO headDTO = processList.get(startIndex);
		TouchDTO tailDTO = processList.get(endIndex);
		
		TouchDTO newDTO = new TouchDTO();
		newDTO.setDirection(featureDirection);
		newDTO.setStartMLine(headDTO.getStartMLine());
		newDTO.setEndMLine(tailDTO.getEndMLine());
		
		//从后往前删
		for(int i = endIndex;i>= startIndex;i--){
			processList.remove(i);
		}
		
		processList.add(startIndex, newDTO);
		
	}
	
	/*
	 * 判断第一第二元素间是否有缺口（不考虑合并关系）
	 */
	private boolean existsGapBetweenFirstAndSecondElement(
			String segmentDirection, FeatureElementDTO firstElement,
			TouchDTO secondDTO) {
		return (
			(segmentDirection.equals("up")&&firstElement.getHigh()<Math.min(secondDTO.getEndMLine().getLow(), secondDTO.getStartMLine().getLow()))||
			(segmentDirection.equals("down")&&firstElement.getLow()>Math.max(secondDTO.getEndMLine().getHigh(), secondDTO.getStartMLine().getHigh()))
			);
	}
	
	/**
	 * 获取第一元素
	 * 选择次高点/次低点
	 * 
	 * 
	 * @param beforeElementList
	 * @return
	 */
	public FeatureElementDTO getFirstElement(List<FeatureElementDTO> beforeElementList,String segmentDirection){
		
		FeatureElementDTO firstElement = beforeElementList
				.get(beforeElementList.size() - 1);
		
		if(segmentDirection.equals("up")){
			//向上的线段，特征序列是向下笔，取最高点的元素
			for(int i = beforeElementList.size()-1;i>=0;i--){
				FeatureElementDTO dto = beforeElementList.get(i);
				if(dto.getHigh()>firstElement.getHigh()){
					firstElement = dto;
				}
			}
		}else if(segmentDirection.equals("down")){
			//向下的线段，特征序列是向上笔，取最低点的元素
			for(int i = beforeElementList.size()-1;i>=0;i--){
				FeatureElementDTO dto = beforeElementList.get(i);
				if(dto.getLow()<firstElement.getLow()){
					firstElement = dto;
				}
			}
		}
		return firstElement;
	}
	
	/**
	 * 处理特征序列的合并关系
	 * 
	 * @return
	 */
	public static List<FeatureElementDTO> mergeFeatureElement(List<TouchDTO> touchList,
			String featureDirection, int startIndex, int endIndex) {
		
		//由分笔中抓出特征序列
		List<FeatureElementDTO> featureElementList = new ArrayList<FeatureElementDTO>();
		for(int i = startIndex;i<=endIndex;i++){
			TouchDTO touchDTO = touchList.get(i);
			if(touchDTO.getDirection().equals(featureDirection)){
				FeatureElementDTO elementDTO = new FeatureElementDTO();
				elementDTO.setBeginTime(touchDTO.getStartMLine().getBeginTime());
				elementDTO.setEndTime(touchDTO.getEndMLine().getEndTime());
				if(touchDTO.getDirection().equals("up")){
					elementDTO.setHigh(touchDTO.getEndMLine().getHigh());
					elementDTO.setLow(touchDTO.getStartMLine().getLow());
				}else if(touchDTO.getDirection().equals("down")){
					elementDTO.setHigh(touchDTO.getStartMLine().getHigh());
					elementDTO.setLow(touchDTO.getEndMLine().getLow());	
				}
				elementDTO.getTouchIndexList().add(i);
				featureElementList.add(elementDTO);
			}
		}
		
		boolean flag = true;
		while(flag){
			FeatureElementDTO  mergeDTO = new FeatureElementDTO();
			List<FeatureElementDTO> headList = new ArrayList<FeatureElementDTO>();
			List<FeatureElementDTO> tailLsit = new ArrayList<FeatureElementDTO>();
			
			flag = false;
			for(int i = 1;i<featureElementList.size();i++){
				FeatureElementDTO lastDTO = featureElementList.get(i-1);
				FeatureElementDTO thisDTO = featureElementList.get(i);
				
				//包含关系
				if((lastDTO.getHigh()>=thisDTO.getHigh()&&lastDTO.getLow()<=thisDTO.getLow())
					||(thisDTO.getHigh()>=lastDTO.getHigh()&&thisDTO.getLow()<=lastDTO.getLow())
					){
					
					//合并
					mergeDTO.setBeginTime(lastDTO.getBeginTime());
					mergeDTO.setEndTime(thisDTO.getEndTime());
					mergeDTO.getTouchIndexList().addAll(lastDTO.getTouchIndexList());//添加前序列分笔编号
					mergeDTO.getTouchIndexList().addAll(thisDTO.getTouchIndexList());//添加后序列分笔编号
					
					if(featureDirection.equals("up")){
						mergeDTO.setHigh(Math.min(lastDTO.getHigh(), thisDTO.getHigh()));
						mergeDTO.setLow(Math.min(lastDTO.getLow(), thisDTO.getLow()));
					}else if(featureDirection.equals("down")){
						mergeDTO.setHigh(Math.max(lastDTO.getHigh(), thisDTO.getHigh()));
						mergeDTO.setLow(Math.max(lastDTO.getLow(), thisDTO.getLow()));
					}
					
					flag=true;
					if(i!=0){
						headList = featureElementList.subList(0, i-1);
					}
					
					if(i!=featureElementList.size()-1){
						tailLsit = featureElementList.subList(i+1, featureElementList.size());
					}
					break;
				}
			}
			
			if(flag){
				featureElementList = new ArrayList<FeatureElementDTO>();
				featureElementList.addAll(headList);
				featureElementList.add(mergeDTO);
				featureElementList.addAll(tailLsit);
			}else{
				flag = false;
			}
		}
		
		return featureElementList;
	}
}
