package pers.sam.czsc.core.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;
import pers.sam.czsc.core.util.GetTestDataUtil;
import pers.sam.czsc.dto.FeatureElementDTO;
import pers.sam.czsc.dto.StrokeDTO;
import pers.sam.util.StockDateUtil;

public class FindSegmentImpl3Test extends TestCase {
	
	private FindSegmentImpl3  findSegmentImpl3= new FindSegmentImpl3();
	
	
	public static void main(String args[]){
		
		List<Integer> list = new LinkedList<Integer>();
		
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		list.add(5);
		
//		list.remove(1);
		list.add(4, 6);
		
		System.out.println(list);
		
	}
	
//	public void atestFindSegment() throws ParseException{
//		
//		
//		String fileName = System.getProperty("user.dir")+"/resource/79_01/79_01.txt";
//		
//		List <TouchDTO>touchList = GetDataUtil.getTestData(fileName);
//		
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		for(int i = 0;i<touchList.size();i++){
//			TouchDTO touchDTO = touchList.get(i);
//			
//			System.out.println(
//					sdf.format(touchDTO.getStartMLine().getBeginTime())+" "+
//					sdf.format(touchDTO.getEndMLine().getBeginTime())+" "+touchDTO.getStartMLine().getHigh()+" "
//					+touchDTO.getEndMLine().getHigh()
//			);
//			
//		}
//		
//		findSegmentImpl3.findSegment(touchList);
//	}
	
	
	public void test67_01() throws ParseException{

		System.out.println("----------begin test67_01----------");
		
		String fileName = System.getProperty("user.dir")+"/resource/67_01/67_01.txt";
		
		List <StrokeDTO>touchList = GetTestDataUtil.getTestData(fileName);
		
		List resultList = findSegmentImpl3.findSegment(touchList);
		
		for(int i = 0;i<resultList.size();i++){
			System.out.println(resultList.get(i));
		}

		assertEquals(1, resultList.size());
		assertEquals("1905-01-01 00:00:00", resultList.get(0));
		
		System.out.println("----------end----------");
		System.out.println("");
		
	}
	
	public void test67_02() throws ParseException{

		System.out.println("----------begin test67_02----------");
		
		String fileName = System.getProperty("user.dir")+"/resource/67_02/67_02.txt";
		
		List <StrokeDTO>touchList = GetTestDataUtil.getTestData(fileName);
		
		List<String> resultList = findSegmentImpl3.findSegment(touchList);
		assertEquals(2, resultList.size());
		assertEquals("1905-01-01 00:00:00", resultList.get(0));
		assertEquals("1910-01-01 00:00:00", resultList.get(1));
		
		System.out.println("----------end----------");
		System.out.println("");
		
	}
	
	
	public void test67_03() throws ParseException{

		System.out.println("----------begin test67_03----------");
		
		String fileName = System.getProperty("user.dir")+"/resource/67_03/67_03.txt";
		
		List <StrokeDTO>touchList = GetTestDataUtil.getTestData(fileName);
		
		List<String> resultList = findSegmentImpl3.findSegment(touchList);
		assertEquals(0, resultList.size());
		System.out.println("----------end----------");
		System.out.println("");
		
	}	
	
	public void test67_04() throws ParseException{

		System.out.println("----------begin test67_04----------");
		
		String fileName = System.getProperty("user.dir")+"/resource/67_04/67_04.txt";
		
		List <StrokeDTO>touchList = GetTestDataUtil.getTestData(fileName);
		
		List<String> resultList =findSegmentImpl3.findSegment(touchList);
		
		assertEquals(2, resultList.size());
		assertEquals("1903-01-01 00:00:00", resultList.get(0));
		assertEquals("1908-01-01 00:00:00", resultList.get(1));
		System.out.println("----------end----------");
		System.out.println("");
		
	}
	
	public void test79_01() throws ParseException{

		System.out.println("----------begin test79_01----------");
		
		String fileName = System.getProperty("user.dir")+"/resource/79_01/79_01.txt";
		
		List <StrokeDTO>touchList = GetTestDataUtil.getTestData(fileName);
		
		List<String> resultList = findSegmentImpl3.findSegment(touchList);
		assertEquals(2, resultList.size());
		assertEquals("1903-01-01 00:00:00", resultList.get(0));
		assertEquals("1908-01-01 00:00:00", resultList.get(1));
		System.out.println("----------end----------");
		System.out.println("");
	}
	
	public void test79_02() throws ParseException{

		System.out.println("----------begin test79_02----------");
		
		String fileName = System.getProperty("user.dir")+"/resource/79_02/79_02.txt";
		
		List <StrokeDTO>touchList = GetTestDataUtil.getTestData(fileName);
		
		List<String> resultList = findSegmentImpl3.findSegment(touchList);
		assertEquals(1, resultList.size());
		assertEquals("1903-01-01 00:00:00", resultList.get(0));
		System.out.println("----------end----------");
		System.out.println("");
	}
	
	
	public void testFirstElement() throws ParseException{

		System.out.println("----------begin testFirstElement----------");
		
		
		String fileName = System.getProperty("user.dir")+"/resource/FirstElementTest/次高点测试.txt";
		
		List <StrokeDTO>touchList = GetTestDataUtil.getTestData(fileName);
		
		List<String> resultList = findSegmentImpl3.findSegment(touchList);
		assertEquals(1, resultList.size());
		assertEquals("1905-01-01 00:00:00", resultList.get(0));
		System.out.println("----------end----------");
		System.out.println("");
		
	}
	
	/**
	 * 测试特征序列合并逻辑
	 * mergeFeatureElement()
	 * @throws ParseException
	 */
	public void testMergeTest_01() throws ParseException{
		
		System.out.println("testMergeTest_01");
		
		String fileName = System.getProperty("user.dir")+"/resource/mergeTest_01/merge_01.txt";
		
		List <StrokeDTO>touchList = GetTestDataUtil.getTestData(fileName);
		
		List<FeatureElementDTO> featureList = 
			findSegmentImpl3.mergeFeatureElement(touchList, "down", 0, 12);
		
		System.out.println(featureList);
		for (int i = 0; i < featureList.size(); i++) {
			FeatureElementDTO dto = featureList.get(i);
			System.out.println(StockDateUtil.SDF_TIME.format(dto.getBeginTime())
							+ " "
							+ StockDateUtil.SDF_TIME.format(dto.getEndTime()));
		}
		
		System.out.println("----------end----------");
		System.out.println("");
		
	}
	
	/**
	 * 测试特征序列合并逻辑
	 * mergeFeatureElement()
	 * @throws ParseException
	 */	
	public void testMergeTest_02() throws ParseException{
		
		System.out.println("testMergeTest_02");
		
		String fileName = System.getProperty("user.dir")+"/resource/mergeTest_02/merge_02.txt";
		
		List <StrokeDTO>touchList = GetTestDataUtil.getTestData(fileName);
		
		List<FeatureElementDTO> featureList = 
			findSegmentImpl3.mergeFeatureElement(touchList, "up", 0, 8);
		
		System.out.println(featureList);
		for (int i = 0; i < featureList.size(); i++) {
			FeatureElementDTO dto = featureList.get(i);
			System.out.println(StockDateUtil.SDF_TIME.format(dto.getBeginTime())
							+ " "
							+ StockDateUtil.SDF_TIME.format(dto.getEndTime()));
		}
		System.out.println("----------end----------");
		System.out.println("");
		
	}
	
	/**
	 * 测试网络上图片的
	 * mergeFeatureElement()
	 * @throws ParseException
	 */	
	public void testWeb_01() throws ParseException{
		
		System.out.println("----------begin testWeb_01----------");
		
		String fileName = System.getProperty("user.dir")+"/resource/web_01/web_01.txt";
		
		List <StrokeDTO>touchList = GetTestDataUtil.getTestData(fileName);
		
		List<String> resultList = findSegmentImpl3.findSegment(touchList);
		assertEquals(1, resultList.size());
		assertEquals("1905-01-01 00:00:00", resultList.get(0));
		
		System.out.println("----------end----------");
		System.out.println("");
	}
	
	
	/**
	 * 测试网络上图片的
	 * mergeFeatureElement()
	 * @throws ParseException
	 */	
	public void testWeb_02() throws ParseException{
		
		System.out.println("----------begin testWeb_02----------");
		
		String fileName = System.getProperty("user.dir")+"/resource/web_02/web_02.txt";
		
		List <StrokeDTO>touchList = GetTestDataUtil.getTestData(fileName);
		
		List<String> resultList = findSegmentImpl3.findSegment(touchList);
		assertEquals(2, resultList.size());
		assertEquals("1903-01-01 00:00:00", resultList.get(0));
		assertEquals("1906-01-01 00:00:00", resultList.get(1));
		
		System.out.println("----------end----------");
		System.out.println("");
	}
	
	/**
	 * 测试网络上图片的
	 * mergeFeatureElement()
	 * @throws ParseException
	 */	
	public void testWeb_03() throws ParseException{
		
		System.out.println("----------begin testWeb_03----------");
		
		String fileName = System.getProperty("user.dir")+"/resource/web_03/web_03.txt";
		
		List <StrokeDTO>touchList = GetTestDataUtil.getTestData(fileName);
		
		List<String> resultList = findSegmentImpl3.findSegment(touchList);
		assertEquals(0, resultList.size());
		
		System.out.println("----------end----------");
		System.out.println("");
	}
	
	/**
	 * 测试网络上图片的
	 * mergeFeatureElement()
	 * @throws ParseException
	 */	
	public void testWeb_04() throws ParseException{
		
		System.out.println("----------begin testWeb_04----------");
		
		String fileName = System.getProperty("user.dir")+"/resource/web_04/web_04.txt";
		
		List <StrokeDTO>touchList = GetTestDataUtil.getTestData(fileName);
		
		List<String> resultList = findSegmentImpl3.findSegment(touchList);
		assertEquals(3, resultList.size());
		assertEquals("1903-01-01 00:00:00", resultList.get(0));
		assertEquals("1906-01-01 00:00:00", resultList.get(1));
		assertEquals("1913-01-01 00:00:00", resultList.get(2));
		
		System.out.println("----------end----------");
		System.out.println("");
	}
	
	
	/**
	 * 测试网络上图片的
	 * mergeFeatureElement()
	 * @throws ParseException
	 */	
	public void testWeb_05() throws ParseException{
		
		System.out.println("----------begin testWeb_05----------");
		
		String fileName = System.getProperty("user.dir")+"/resource/web_05/web_05.txt";
		
		List <StrokeDTO>touchList = GetTestDataUtil.getTestData(fileName);
		
		List<String> resultList = findSegmentImpl3.findSegment(touchList);
		assertEquals(1, resultList.size());
		assertEquals("1905-01-01 00:00:00", resultList.get(0));
		System.out.println("----------end----------");
		System.out.println("");
	}
	
	/**
	 * 测试网络上图片的
	 * mergeFeatureElement()
	 * @throws ParseException
	 */	
	public void testWeb_06() throws ParseException{
		
		System.out.println("----------begin testWeb_06----------");
		
		String fileName = System.getProperty("user.dir")+"/resource/web_06/web_06.txt";
		
		List <StrokeDTO>touchList = GetTestDataUtil.getTestData(fileName);
		
		List<String> resultList = findSegmentImpl3.findSegment(touchList);
		assertEquals(1, resultList.size());
		assertEquals("1905-01-01 00:00:00", resultList.get(0));
		System.out.println("----------end----------");
		System.out.println("");
	}
	
	
	/**
	 * 测自己的分笔数据
	 * mergeFeatureElement()
	 * @throws ParseException
	 */	
	public void test600031_test_01() throws ParseException{
		
		System.out.println("----------begin test600031_test_01----------");
		
		String fileName = System.getProperty("user.dir")+"/resource/600031_test_01/600031_test_01.txt";
		
		List <StrokeDTO>touchList = GetTestDataUtil.getTestData(fileName);
		
		List<String> resultList = findSegmentImpl3.findSegment(touchList);
		
		System.out.println("----------end----------");
		System.out.println("");
	}
	
	/**
	 * 测试一段601318 30分钟走势的分段
	 * findSegment
	 * @throws ParseException
	 */	
	public void test601318_30min_test_01() throws ParseException{
		
		System.out.println("----------begin 601318_30min_test_01----------");
		
		String fileName = System.getProperty("user.dir")+"/resource/601318_30min_test_01/601318_30min_test_01.txt";
		
		List <StrokeDTO>touchList = GetTestDataUtil.getTestData(fileName);
		
		List<String> resultList = findSegmentImpl3.findSegment(touchList);
//		assertEquals(1, resultList.size());
//		assertEquals("1905-01-01 00:00:00", resultList.get(0));
		System.out.println("----------end----------");
		System.out.println("");
	}
}
