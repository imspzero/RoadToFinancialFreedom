package pers.sam.czsc.core.impl;

import java.text.ParseException;
import java.util.List;

import junit.framework.TestCase;
import pers.sam.czsc.core.util.GetDataUtil;
import pers.sam.dto.TouchDTO;

public class FindSegmentImpl3Test extends TestCase {
	
	private FindSegmentImpl3  findSegmentImpl3= new FindSegmentImpl3();
	
	public void testFindSegment() throws ParseException{
		
		
		String fileName = System.getProperty("user.dir")+"/resource/79_01/79_01.txt";
		
		List <TouchDTO>touchList = GetDataUtil.getTestData(fileName);
		
		
		findSegmentImpl3.findSegment(touchList);
	}
	
}
