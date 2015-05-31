package pers.sam.czsc.core.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import pers.sam.czsc.core.util.GetTestDataUtil;
import pers.sam.czsc.dto.MergeLineDTO;
import pers.sam.czsc.dto.StrokeDTO;

public class FindSegmentImpl2Test extends TestCase {
	
	private FindSegmentImpl2  findSegmentImpl2= new FindSegmentImpl2();
	
	public void atestFindSegment() throws ParseException{
		
		List <StrokeDTO>touchList = new ArrayList();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		StrokeDTO touchDTO = null; 
		MergeLineDTO startMLine = null;
		MergeLineDTO endMLine = null;
		
		//0~1
		touchDTO = new StrokeDTO();
		startMLine = new MergeLineDTO();
		startMLine.setBeginTime(sdf.parse("2014-1-1"));
		startMLine.setEndTime(sdf.parse("2014-1-1"));
		startMLine.setHigh(1.0);
		startMLine.setLow(1.0);
		
		endMLine = new MergeLineDTO();
		endMLine.setBeginTime(sdf.parse("2014-2-1"));
		endMLine.setEndTime(sdf.parse("2014-2-1"));
		endMLine.setHigh(3.0);
		endMLine.setLow(3.0);
		
		touchDTO.setStartMLine(startMLine);
		touchDTO.setEndMLine(endMLine);
		touchDTO.setDirection("up");
		
		touchList.add(touchDTO);
		
		//1~2
		touchDTO = new StrokeDTO();
		startMLine = new MergeLineDTO();
		startMLine.setBeginTime(sdf.parse("2014-2-1"));
		startMLine.setEndTime(sdf.parse("2014-2-1"));
		startMLine.setHigh(3.0);
		startMLine.setLow(3.0);
		
		endMLine = new MergeLineDTO();
		endMLine.setBeginTime(sdf.parse("2014-3-1"));
		endMLine.setEndTime(sdf.parse("2014-3-1"));
		endMLine.setHigh(2.0);
		endMLine.setLow(2.0);
		
		touchDTO.setStartMLine(startMLine);
		touchDTO.setEndMLine(endMLine);
		touchDTO.setDirection("down");
		
		touchList.add(touchDTO);
		
		//2~3
		touchDTO = new StrokeDTO();
		startMLine = new MergeLineDTO();
		startMLine.setBeginTime(sdf.parse("2014-3-1"));
		startMLine.setEndTime(sdf.parse("2014-3-1"));
		startMLine.setHigh(2.0);
		startMLine.setLow(2.0);
		
		endMLine = new MergeLineDTO();
		endMLine.setBeginTime(sdf.parse("2014-4-1"));
		endMLine.setEndTime(sdf.parse("2014-4-1"));
		endMLine.setHigh(2.5);
		endMLine.setLow(2.5);
		
		touchDTO.setStartMLine(startMLine);
		touchDTO.setEndMLine(endMLine);
		touchDTO.setDirection("up");
		
		touchList.add(touchDTO);
		
		//3~4
		touchDTO = new StrokeDTO();
		startMLine = new MergeLineDTO();
		startMLine.setBeginTime(sdf.parse("2014-4-1"));
		startMLine.setEndTime(sdf.parse("2014-4-1"));
		startMLine.setHigh(2.5);
		startMLine.setLow(2.5);
		
		endMLine = new MergeLineDTO();
		endMLine.setBeginTime(sdf.parse("2014-5-1"));
		endMLine.setEndTime(sdf.parse("2014-5-1"));
		endMLine.setHigh(1.5);
		endMLine.setLow(1.5);
		
		touchDTO.setStartMLine(startMLine);
		touchDTO.setEndMLine(endMLine);
		touchDTO.setDirection("down");
		
		touchList.add(touchDTO);
		
		//4~5
		touchDTO = new StrokeDTO();
		startMLine = new MergeLineDTO();
		startMLine.setBeginTime(sdf.parse("2014-5-1"));
		startMLine.setEndTime(sdf.parse("2014-5-1"));
		startMLine.setHigh(1.5);
		startMLine.setLow(1.5);
		
		endMLine = new MergeLineDTO();
		endMLine.setBeginTime(sdf.parse("2014-6-1"));
		endMLine.setEndTime(sdf.parse("2014-6-1"));
		endMLine.setHigh(5.0);
		endMLine.setLow(5.0);
		
		touchDTO.setStartMLine(startMLine);
		touchDTO.setEndMLine(endMLine);
		touchDTO.setDirection("up");
		
		touchList.add(touchDTO);
		
		//5~6
		touchDTO = new StrokeDTO();
		startMLine = new MergeLineDTO();
		startMLine.setBeginTime(sdf.parse("2014-6-1"));
		startMLine.setEndTime(sdf.parse("2014-6-1"));
		startMLine.setHigh(5.0);
		startMLine.setLow(5.0);
		
		endMLine = new MergeLineDTO();
		endMLine.setBeginTime(sdf.parse("2014-7-1"));
		endMLine.setEndTime(sdf.parse("2014-7-1"));
		endMLine.setHigh(2.8);
		endMLine.setLow(2.8);
		
		touchDTO.setStartMLine(startMLine);
		touchDTO.setEndMLine(endMLine);
		touchDTO.setDirection("down");
		
		touchList.add(touchDTO);
		
		//6~7
		touchDTO = new StrokeDTO();
		startMLine = new MergeLineDTO();
		startMLine.setBeginTime(sdf.parse("2014-7-1"));
		startMLine.setEndTime(sdf.parse("2014-7-1"));
		startMLine.setHigh(2.8);
		startMLine.setLow(2.8);
		
		endMLine = new MergeLineDTO();
		endMLine.setBeginTime(sdf.parse("2014-8-1"));
		endMLine.setEndTime(sdf.parse("2014-8-1"));
		endMLine.setHigh(4.5);
		endMLine.setLow(4.5);
		
		touchDTO.setStartMLine(startMLine);
		touchDTO.setEndMLine(endMLine);
		touchDTO.setDirection("up");
		
		touchList.add(touchDTO);
		
		
		//7~8
		touchDTO = new StrokeDTO();
		startMLine = new MergeLineDTO();
		startMLine.setBeginTime(sdf.parse("2014-8-1"));
		startMLine.setEndTime(sdf.parse("2014-8-1"));
		startMLine.setHigh(4.5);
		startMLine.setLow(4.5);
		
		endMLine = new MergeLineDTO();
		endMLine.setBeginTime(sdf.parse("2014-9-1"));
		endMLine.setEndTime(sdf.parse("2014-9-1"));
		endMLine.setHigh(2.3);
		endMLine.setLow(2.3);
		
		touchDTO.setStartMLine(startMLine);
		touchDTO.setEndMLine(endMLine);
		touchDTO.setDirection("down");
		
		touchList.add(touchDTO);
		
		//8~9
		touchDTO = new StrokeDTO();
		startMLine = new MergeLineDTO();
		startMLine.setBeginTime(sdf.parse("2014-9-1"));
		startMLine.setEndTime(sdf.parse("2014-9-1"));
		startMLine.setHigh(2.3);
		startMLine.setLow(2.3);
		
		endMLine = new MergeLineDTO();
		endMLine.setBeginTime(sdf.parse("2014-10-1"));
		endMLine.setEndTime(sdf.parse("2014-10-1"));
		endMLine.setHigh(5.5);
		endMLine.setLow(5.5);
		
		touchDTO.setStartMLine(startMLine);
		touchDTO.setEndMLine(endMLine);
		touchDTO.setDirection("up");
		
		touchList.add(touchDTO);
		
		findSegmentImpl2.findSegment(touchList);
	}
	
	
	
	public void atest67_01() throws ParseException{

		System.out.println("----------begin test67_01----------");
		
		
		String fileName = System.getProperty("user.dir")+"/resource/67_01/67_01.txt";
		
		List <StrokeDTO>touchList = GetTestDataUtil.getTestData(fileName);
		
		findSegmentImpl2.findSegment(touchList);
		
		System.out.println("----------end----------");
		System.out.println("");
		
	}
	
	public void test67_02() throws ParseException{

		System.out.println("----------begin test67_02----------");
		
		
		String fileName = System.getProperty("user.dir")+"/resource/67_02/67_02.txt";
		
		List <StrokeDTO>touchList = GetTestDataUtil.getTestData(fileName);
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for(int i = 0;i<touchList.size();i++){
			StrokeDTO touchDTO = touchList.get(i);
			
			System.out.println(
					sdf.format(touchDTO.getStartMLine().getBeginTime())+" "+
					sdf.format(touchDTO.getEndMLine().getBeginTime())+" "+touchDTO.getStartMLine().getHigh()+" "
					+touchDTO.getEndMLine().getHigh()
			);
			
		}
		
		
		findSegmentImpl2.findSegment(touchList);
		
		System.out.println("----------end----------");
		System.out.println("");
		
	}
	
	public void atest79_01() throws ParseException{

		System.out.println("test79_01");
		
		
		String fileName = System.getProperty("user.dir")+"/resource/79_01/79_01.txt";
		
		List <StrokeDTO>touchList = GetTestDataUtil.getTestData(fileName);
		
		findSegmentImpl2.findSegment(touchList);
		
	}
	
}
