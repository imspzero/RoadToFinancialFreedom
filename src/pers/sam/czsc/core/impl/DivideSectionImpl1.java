package pers.sam.czsc.core.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import pers.sam.czsc.core.DivideSectionInterface;
import pers.sam.czsc.dto.MergeLineDTO;
import pers.sam.czsc.util.ZenTheoryUtil;
import pers.sam.dto.StockKLinePriceDTO;
import pers.sam.util.StockDateUtil;

/**
 * 分笔接口--实现1
 * @author Administrator
 *
 */
public class DivideSectionImpl1 implements DivideSectionInterface {
    
	private static Logger logger=Logger.getLogger(DivideSectionImpl1.class);
	
	/**
	 * 分笔
	 * @throws Exception 
	 */
	public boolean[] divideSection(List<MergeLineDTO> mergeSticksList) throws Exception {
		boolean [] pointArray = new boolean[mergeSticksList.size()];
		for(int i =0;i<pointArray.length;i++){
			pointArray[i] = false;
		}
		
		/**
		 * 1.预处理，处理后相邻的都是非同一种分型
		 */
		MergeLineDTO lastPoint = null;
		for(int i = 0;i<mergeSticksList.size();i++){
			
			MergeLineDTO thisPoint = mergeSticksList.get(i);
			
			if(thisPoint.getIsBottom().equals("N")
					&&thisPoint.getIsPeak().equals("N")){
				continue;
			}
			
			if(lastPoint == null){
				lastPoint = thisPoint;
				pointArray[i] = true;
				continue;
			}
			
			if(lastPoint.getIsPeak().equals("Y")
					&&thisPoint.getIsPeak().equals("Y")){
				//同为顶，取最高的
				if(thisPoint.getHigh()>=lastPoint.getHigh()){
					lastPoint = thisPoint;
					pointArray[i] = true;
				}
				
			}else if(lastPoint.getIsBottom().equals("Y")
					&&thisPoint.getIsBottom().equals("Y")){
				//同为底，取最低的
				if(thisPoint.getLow()<=lastPoint.getLow()){
					lastPoint = thisPoint;
					pointArray[i] = true;
				}
				
			}else{
				lastPoint = thisPoint;
				pointArray[i] = true;
			}
		}
		
		/**
		 * 2.动态规划，找出任意[k,k+i]是否能成一笔
		 */
		List <Integer>pointIndexList = new ArrayList<Integer>();
		for(int i=0;i<pointArray.length;i++){
			if(pointArray[i]==true){
				pointIndexList.add(new Integer(i));
			}
		}
		//得出N个分型下标的数组
		Integer[] pointIndexArray = (Integer[]) pointIndexList
				.toArray(new Integer[pointIndexList.size()]);
		
		boolean [][] pointIndexMatrix = 
			new boolean[pointIndexArray.length][pointIndexArray.length];
		
		for(int i = 0;i<pointIndexArray.length;i++){
			for(int j = 0;j<pointIndexArray.length;j++){
				pointIndexMatrix[i][j] = false;
			}
		}
		
		findValidPointByDP(mergeSticksList,pointIndexArray,pointIndexMatrix);
		
		/**
		 * 3.根据上一步得到的结果，得出最后合理的分型
		 */
		boolean [] resultArray = new boolean [pointIndexArray.length];
		boolean hasResult = false;
		for(int i = pointIndexArray.length-1;i>0;i--){
			
			for(int j = 0;j<resultArray.length;j++){
				resultArray[j]=false;
			}
			
			hasResult = 
				checkFinalTwigPartition(mergeSticksList,
						pointIndexArray,pointIndexMatrix,
						resultArray,i);
			if(hasResult==true){
				break;
			}
		}
		
		/**
		 * 4.输出分笔结果
		 */
		System.out.println("分笔结果 : "+hasResult);
		if(!hasResult){
			throw new Exception("按照目前的划分规则，没有找到分笔的结果.");
		}
		
		
		for(int i = 0;i<resultArray.length;i++){
			if(resultArray[i]==true){
				MergeLineDTO dto = mergeSticksList.get(pointIndexArray[i]);
				if(dto.getIsPeak().equals("Y")){
					logger.info(pointIndexArray[i]+" "+StockDateUtil.SDF_TIME.format(dto.getBeginTime())+"\t"+
							StockDateUtil.SDF_TIME.format(dto.getEndTime())+"\t"+
							"合并["+dto.getStickNumber()+"]条K线"+"\t"+
							"顶["+dto.getLow()+"]["+dto.getHigh()+"]");
				}
				if(dto.getIsBottom().equals("Y")){
					logger.info(pointIndexArray[i]+" "+StockDateUtil.SDF_TIME.format(dto.getBeginTime())+"\t"+
							StockDateUtil.SDF_TIME.format(dto.getEndTime())+"\t"+
							"合并["+dto.getStickNumber()+"]条K线"+"\t"+
							"底 ["+dto.getLow()+"]["+dto.getHigh()+"]");
				}
			}
		}
		
		/**
		 * 5.组装结果集，返回分笔结果
		 */
		boolean [] actualResultArray = new boolean[mergeSticksList.size()];
		
		for(int i = 0;i<resultArray.length;i++){
			if(resultArray[i]==true){
				actualResultArray[pointIndexArray[i]] = true;
			}
		}
		
		return actualResultArray;
	}
	
	
	/**
	 * 查看最终正确的笔划分
	 * 
	 * @param mergeSticksList
	 * @param pointIndexArray
	 * @param pointIndexMatrix
	 * @return
	 */
	public static boolean checkFinalTwigPartition(List<MergeLineDTO> mergeSticksList,
			Integer[] pointIndexArray, boolean[][] pointIndexMatrix,
			boolean [] resultArray,int endIndex){
		
		return searchFinalTwigPartition(mergeSticksList,
				pointIndexArray,pointIndexMatrix,
				resultArray,0,endIndex);
		
	}
	
	public static boolean searchFinalTwigPartition(List<MergeLineDTO> mergeSticksList,
			Integer[] pointIndexArray, boolean[][] pointIndexMatrix,
			boolean [] resultArray,int index,int endIndex){
		
		if(index==endIndex){
			return true;
		}else{
			for(int i = index+1;i<=endIndex;i++){
				if(pointIndexMatrix[index][i]==true){
					resultArray[index]=true;
					resultArray[i]=true;
					if(searchFinalTwigPartition(mergeSticksList,
							pointIndexArray,pointIndexMatrix,
							resultArray,i,endIndex)){
						return true;
					}else{
						resultArray[index]=false;
						resultArray[i]=false;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * 通过动态规划，查找局部解，结果在pointIndexMatrix中
	 * @param mergeSticksList
	 * @param pointIndexArray
	 * @param pointIndexMatrix
	 */
	public static void findValidPointByDP(List<MergeLineDTO> mergeSticksList,
			Integer[] pointIndexArray, boolean[][] pointIndexMatrix) {
		
		for(int dist = 1;dist<pointIndexArray.length;dist=dist+2){
			//取奇数 ，根据第一阶段的预处理，间隔为偶数的都是同类分型
			processByDist(mergeSticksList, pointIndexArray, pointIndexMatrix,
					dist);
		}
	}
	
	/**
	 * 处理间隔为dist的分型组合，是否能成一笔
	 * @param mergeSticksList
	 * @param pointIndexArray
	 * @param pointIndexMatrix
	 * @param dist
	 */
	public static void processByDist(List<MergeLineDTO> mergeSticksList,
			Integer[] pointIndexArray, boolean[][] pointIndexMatrix,int dist){
		
		for(int i = 0;i<pointIndexArray.length-dist;i++){
			boolean checkResult=
				check2PointIsMultiLine(mergeSticksList, pointIndexArray, pointIndexMatrix,
					i, i + dist);
			
			if(checkResult == true){
				pointIndexMatrix[i][i + dist] = false;
			}else{
				if(validatePeakAndBottom(mergeSticksList,
						pointIndexArray[i],pointIndexArray[i + dist])){
					pointIndexMatrix[i][i + dist]=true;
				}else{
					pointIndexMatrix[i][i + dist]=false;
				}
			}
		}
	}
	
	/**
	 * 递归处理，逐步检查[startIndex，endIndex]是否能划分成多笔
	 * @param mergeSticksList
	 * @param pointIndexArray
	 * @param pointIndexMatrix
	 * @param startIndex
	 * @param endIndex
	 * @return
	 */
	public static boolean check2PointIsMultiLine(List<MergeLineDTO> mergeSticksList,
			Integer[] pointIndexArray, boolean[][] pointIndexMatrix,
			int startIndex, int endIndex) {
		
		if(startIndex==endIndex){
			return true;
		}else{
			for(int i = startIndex+1;i<=endIndex;i++){
				if(pointIndexMatrix[startIndex][i]==true){
					boolean result = 
						check2PointIsMultiLine(mergeSticksList,pointIndexArray,
							pointIndexMatrix,
							i,endIndex);
					if(result == true){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * 校验是否满足一笔
	 */
	private static boolean validatePeakAndBottom(
			List<MergeLineDTO> mergeSticksList,int startIndex,int endIndex){
		
		//1.不满足顶必须接着底、或底必须接着顶
		MergeLineDTO startDTO = mergeSticksList.get(startIndex);
		MergeLineDTO endDTO = mergeSticksList.get(endIndex);
		if(startIndex==0){
			if(endDTO.getIsPeak().equals("N")&&
					!endDTO.getIsBottom().equals("N")){
				return false;
			}
		}else if((startDTO.getIsPeak().equals("Y")&&endDTO.getIsPeak().equals("Y"))
					||(startDTO.getIsBottom().equals("Y")&&endDTO.getIsBottom().equals("Y"))){
			return false;
		}
		
		//2.顶分型与底分型经过包含处理后，不允许共用K线
		if(endIndex - startIndex<3){
			return false;
		}
		
		//3.顶分型中最高K线和底分型的最低K线之间（不包括这两K线），不考虑包含关系，至少有3根（包括3根）以上K线
		int kLineNumber=0;
		
		//最高或者最低间的k线
		for (int i = 0; i < startDTO.getMemberList().size(); i++) {
			StockKLinePriceDTO dto = startDTO.getMemberList().get(i);
			if(startDTO.getIsPeak().equals("Y")&&
					dto.getHigh().equals(startDTO.getHigh())){//顶，并且是顶元素
				kLineNumber+=(startDTO.getMemberList().size()-i-1);
				break;
			}else if(startDTO.getIsBottom().equals("Y")&&
					dto.getLow().equals(startDTO.getLow())){//底，并且是底元素
				kLineNumber+=(startDTO.getMemberList().size()-i-1);
				break;
			}
		}
		for (int i = 0; i < endDTO.getMemberList().size(); i++) {
			StockKLinePriceDTO dto = endDTO.getMemberList().get(i);
			if(endDTO.getIsBottom().equals("Y")&&
					dto.getLow().equals(endDTO.getLow())){//底,并且是底元素
				kLineNumber+=(i);
				break;
			}else if(endDTO.getIsPeak().equals("Y")&&
					dto.getHigh().equals(endDTO.getHigh())){//顶，并且是顶元素
				kLineNumber+=(i);
				break;
			}
		}
		//分型中间元素的k线合计
		for(int i = startIndex+1;i<endIndex;i++){
			MergeLineDTO dto = mergeSticksList.get(i);
			kLineNumber+=dto.getStickNumber();
		}
		if(kLineNumber<3){
			return false;
		}
		
		//4.顶底分别是笔中(包括组成分型的元素)的最高和最低
		MergeLineDTO peakDTO = null;
		MergeLineDTO bottomDTO = null;
		if(startDTO.getIsPeak().equals("Y")){
			peakDTO = startDTO;
			bottomDTO = endDTO;
		}else{
			peakDTO = endDTO;
			bottomDTO = startDTO;
		}
		
		for(int i = (startIndex==0)?0:startIndex-1;i<= endIndex+1;i++){
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
		
		//5.不允许中间有其他的顶和底
		
		
		//6.或许还需要判断顶底的区间是否不能重叠
		
		return true;
	}
}
