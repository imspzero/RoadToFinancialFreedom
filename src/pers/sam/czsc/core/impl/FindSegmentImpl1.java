package pers.sam.czsc.core.impl;

import java.util.ArrayList;
import java.util.List;

import pers.sam.czsc.core.FindSegmentInterface;
import pers.sam.dto.FeatureElementDTO;
import pers.sam.dto.TouchDTO;
import pers.sam.util.StockDateUtil;

/**
 * 线段划分--实现一
 * @author Administrator
 *
 */
public class FindSegmentImpl1 implements FindSegmentInterface {
	
	public void findSegment(List<TouchDTO> touchList) {
		// TODO Auto-generated method stub
		//取得一开始线段的方向
		String segmentDirection = "";
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
			
			if(lastSegmentEndIndex+3>touchList.size()){
				flag = false;
				break;
			}
			
			//一线段至少有三笔，所以从三个后的元素开始检查
			for(int i=lastSegmentEndIndex+3;i<touchList.size();i=i+2){
				//假设i是分界点
				//找到第一元素
				List<FeatureElementDTO> beforeElementList = mergeFeatureElement(
						touchList, segmentDirection.equals("up") ? "down"
								: "up", lastSegmentEndIndex, i-1);
				FeatureElementDTO firstElement = beforeElementList
						.get(beforeElementList.size() - 1);

				// 找到第二第三元素
				List<FeatureElementDTO> afterElementList = mergeFeatureElement(
						touchList, segmentDirection.equals("up") ? "down"
								: "up", i, touchList.size()-1);
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
				if(!(
					(segmentDirection.equals("up")&&firstElement.getHigh()<secondElement.getLow())||
					(segmentDirection.equals("down")&&firstElement.getLow()>secondElement.getHigh())
					)){
					//是第一种情况，第一元素和第二元素无缺口
					//存在并且划分成功
					flag= true;
					//break;
				}else{
					//是第二种情况，第一元素和第二元素有缺口
					//需要见识第二特征序列是否出现分型
					String secondSegmentDirection = segmentDirection.equals("up")?"down":"up";
					
					//获取第二特征序列
					List<FeatureElementDTO> secondElementList = mergeFeatureElement(touchList,segmentDirection,
							i, touchList.size()-1);
					
					if(secondElementList.size()<3){//少于三个，分型无从考究
						flag = false;
						continue;
					}
					
					for(int j = 1;j<secondElementList.size()-1;j++){
						FeatureElementDTO aDTO = secondElementList.get(i-1);
						FeatureElementDTO bDTO = secondElementList.get(i);
						FeatureElementDTO cDTO = secondElementList.get(i+1);
						
						if(segmentDirection.equals("up")){
							//第二特征分型是底分型
							if(bDTO.getLow()<aDTO.getLow()&&bDTO.getLow()<cDTO.getLow()
								&&bDTO.getHigh()<aDTO.getHigh()&&bDTO.getHigh()<cDTO.getHigh()){
								flag = true;
								break;
							}
						}else if(segmentDirection.equals("down")){
							//第二特征分型是顶分型
							if(bDTO.getLow()>aDTO.getLow()&&bDTO.getLow()>cDTO.getLow()
								&&bDTO.getHigh()>aDTO.getHigh()&&bDTO.getHigh()>cDTO.getHigh()){
								flag = true;
								break;
							}							
						}
					}
				}
				if(flag == true){
					
					TouchDTO touchDTO = touchList.get(lastSegmentEndIndex);
					if(segmentDirection.equals("up")){
						System.out.println("线段于 "+
								StockDateUtil.SDF_TIME.format(touchDTO.getStartMLine().getBeginTime())+"~"+
								StockDateUtil.SDF_TIME.format(touchDTO.getStartMLine().getEndTime())+" point "+
								touchDTO.getEndMLine().getHigh());
					}else if(segmentDirection.equals("down")){
						System.out.println("线段于 "+
								StockDateUtil.SDF_TIME.format(touchDTO.getStartMLine().getBeginTime())+"~"+
								StockDateUtil.SDF_TIME.format(touchDTO.getStartMLine().getEndTime())+" point "+
								touchDTO.getEndMLine().getLow());
					}
					
					lastSegmentEndIndex = i;
					segmentDirection = segmentDirection.equals("up")?"down":"up";
					break;
				}
			}
			if(flag == false){
				break;
			}
		}
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
					
					if(featureDirection.equals("up")){
						mergeDTO.setHigh(Math.max(lastDTO.getHigh(), thisDTO.getHigh()));
						mergeDTO.setLow(Math.max(lastDTO.getLow(), thisDTO.getLow()));
					}else if(featureDirection.equals("down")){
						mergeDTO.setHigh(Math.min(lastDTO.getHigh(), thisDTO.getHigh()));
						mergeDTO.setLow(Math.min(lastDTO.getLow(), thisDTO.getLow()));
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
