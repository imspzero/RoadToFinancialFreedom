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

public class TempTest extends TestCase {
	
	private FindSegmentImpl3  findSegmentImpl3= new FindSegmentImpl3();
	

	/**
	 * ≤‚ ‘Õ¯¬Á…œÕº∆¨µƒ
	 * mergeFeatureElement()
	 * from:http://blog.sina.com.cn/s/blog_6408f06a0100nlmw.html
	 * @throws ParseException
	 */	
	public void testWeb_07() throws ParseException{
		
		System.out.println("----------begin testWeb_07----------");
		
		String fileName = System.getProperty("user.dir")+"/resource/web_07/web_07.txt";
		
		List <StrokeDTO>touchList = GetTestDataUtil.getTestData(fileName);
		
		System.out.println(touchList.size());
		
		for(int i =0; i<touchList.size();i++){
			StrokeDTO dto  = touchList.get(i);
			System.out.println(dto.getStartMLine().getHigh()+" "+dto.getEndMLine().getHigh());
		}
		
		List<String> resultList = findSegmentImpl3.findSegment(touchList);
//		assertEquals(1, resultList.size());
//		assertEquals("1905-01-01 00:00:00", resultList.get(0));
		System.out.println("sdfsdf");
		System.out.println("----------end----------");
		System.out.println("");
	}
}
