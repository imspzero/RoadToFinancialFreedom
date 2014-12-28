package pers.sam.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		
//		try {
//			Date date = sdf.parse("2008-8-8");
//			System.out.println(date);
//			System.out.println(sdf.format(date));
//			
//			Date date2 = sdf2.parse("2008-8-8"+" 09:30:00");
//			System.out.println(date2);
//			System.out.println(sdf2.format(date2));
//			
//			String dateStr = "2004/1/30";
//			System.out.println(dateStr.replace('/', '-'));
//			
//			dateStr ="2008-8-8";
//			
//			System.out.println(sdf2.parse(dateStr));
//			System.out.println(sdf2.format(sdf2.parse(dateStr)));
//			
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		Double d = new Double(100000.00);
		Double price = new Double(3.33);
		System.out.println(Math.floor(d/price));
		
	}

}
