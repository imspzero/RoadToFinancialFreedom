package pers.sam.czsc.core.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import junit.framework.TestCase;
import pers.sam.czsc.core.util.GetDataUtil;
import pers.sam.dto.TouchDTO;

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
	
	public void atestFindSegment() throws ParseException{
		
		
		String fileName = System.getProperty("user.dir")+"/resource/79_01/79_01.txt";
		
		List <TouchDTO>touchList = GetDataUtil.getTestData(fileName);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for(int i = 0;i<touchList.size();i++){
			TouchDTO touchDTO = touchList.get(i);
			
			System.out.println(
					sdf.format(touchDTO.getStartMLine().getBeginTime())+" "+
					sdf.format(touchDTO.getEndMLine().getBeginTime())+" "+touchDTO.getStartMLine().getHigh()+" "
					+touchDTO.getEndMLine().getHigh()
			);
			
		}
		
		findSegmentImpl3.findSegment(touchList);
	}
	
	
	public void test67_01() throws ParseException{

		System.out.println("----------begin test67_01----------");
		
		
		String fileName = System.getProperty("user.dir")+"/resource/67_01/67_01.txt";
		
		List <TouchDTO>touchList = GetDataUtil.getTestData(fileName);
		
		findSegmentImpl3.findSegment(touchList);
		
		System.out.println("----------end----------");
		System.out.println("");
		
	}
	
	public void test67_02() throws ParseException{

		System.out.println("----------begin test67_02----------");
		
		
		String fileName = System.getProperty("user.dir")+"/resource/67_02/67_02.txt";
		
		List <TouchDTO>touchList = GetDataUtil.getTestData(fileName);
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for(int i = 0;i<touchList.size();i++){
			TouchDTO touchDTO = touchList.get(i);
			
			System.out.println(
					sdf.format(touchDTO.getStartMLine().getBeginTime())+" "+
					sdf.format(touchDTO.getEndMLine().getBeginTime())+" "+touchDTO.getStartMLine().getHigh()+" "
					+touchDTO.getEndMLine().getHigh()
			);
			
		}
		
		findSegmentImpl3.findSegment(touchList);
		
		System.out.println("----------end----------");
		System.out.println("");
		
	}
	
	public void test79_01() throws ParseException{

		System.out.println("test79_01");
		
		
		String fileName = System.getProperty("user.dir")+"/resource/79_01/79_01.txt";
		
		List <TouchDTO>touchList = GetDataUtil.getTestData(fileName);
		
		
		findSegmentImpl3.findSegment(touchList);
		
	}
	
}
