package pers.sam.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import pers.sam.dto.StockKLinePriceDTO;

/**
 * 取数工具类(sqlite)(A股)
 * @author lizandeng(Sam Lee)
 * @version 2015-5-31 下午03:06:39
 */
public class GetStockDataFromSqliteUtil {
	
	/**
	 * 取日线数据
	 * @param stockCode
	 * @return
	 */
	public static List<StockKLinePriceDTO> getDayStockData(String stockCode,String beginDay,String endDay){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Connection c = null;
	    Statement stmt = null;
	    List<StockKLinePriceDTO> priceList = new ArrayList<StockKLinePriceDTO>();
	    
	    try {
	        Class.forName("org.sqlite.JDBC");
	        c = DriverManager.getConnection("jdbc:sqlite:myStock.db");
	        c.setAutoCommit(false);
	        System.out.println("Opened database successfully");

	        stmt = c.createStatement();
	        ResultSet rs = stmt.executeQuery( "select * from stock_day_data where code='"+stockCode+"' " +
	        		  " and day>='"+beginDay+"' and day<='"+endDay+"' "+
	        		" order by day asc;" );
	        while (rs.next()) {
	           String dayStr = rs.getString("day");
	           String beginTime = rs.getString("begin_time");
	           String endTime = rs.getString("end_time");
	           
	           StockKLinePriceDTO dto = new StockKLinePriceDTO();
	           dto.setDay(sdf.parse(dayStr));
	           dto.setBeginTime(sdf2.parse(beginTime));
	           dto.setEndTime(sdf2.parse(endTime));
	           dto.setOpen(rs.getDouble("open"));
	           dto.setHigh(rs.getDouble("high"));
	           dto.setLow(rs.getDouble("low"));
	           dto.setClose(rs.getDouble("close"));
	           
	           dto.setVolumn(rs.getDouble("volumn"));
	           dto.setMavol1(rs.getDouble("mavol1"));
	           dto.setMavol2(rs.getDouble("mavol2"));
	           dto.setDif(rs.getDouble("dif"));
	           dto.setDea(rs.getDouble("dea"));
	           dto.setMacd(rs.getDouble("macd"));
	           dto.setMa5(rs.getDouble("ma5"));
	           dto.setMa10(rs.getDouble("ma10"));
	           dto.setMa20(rs.getDouble("ma20"));
	           dto.setMa60(rs.getDouble("ma60"));
	          
	           priceList.add(dto);
	        }
	        rs.close();
	        stmt.close();
	        c.close();
	      } catch ( Exception e ) {
	        System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	        System.exit(0);
	      }
	    
//	      for(int i = 0;i<priceList.size();i++){
//	    	  StockDayPriceDTO dto = priceList.get(i);
//	    	  System.out.println(dto.getDate()+" "+dto.getOpen()+" "+dto.getMacd());
//	    	  
//	      }
	    return priceList;
	}
	
	
	/**
	 * 取周线数据
	 * @param stockCode
	 * @return
	 */
	public static List<StockKLinePriceDTO> getStockWeekData(
			String stockCode,String beginDay,String endDay){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Connection c = null;
	    Statement stmt = null;
	    List<StockKLinePriceDTO> priceList = new ArrayList<StockKLinePriceDTO>();
	    
	    try {
	        Class.forName("org.sqlite.JDBC");
	        c = DriverManager.getConnection("jdbc:sqlite:myStock.db");
	        c.setAutoCommit(false);
	        System.out.println("Opened database successfully");

	        stmt = c.createStatement();
	        ResultSet rs = stmt.executeQuery( "select * from stock_week_data where code='"+stockCode+"' and day<='"+endDay+
	        									"' and day>='"+beginDay+"' order by day asc;" );
	        while (rs.next()) {
	           String dayStr = rs.getString("day");
	           String beginTime = rs.getString("begin_time");
	           String endTime = rs.getString("end_time");
	           
	           StockKLinePriceDTO dto = new StockKLinePriceDTO();
	           dto.setDay(sdf.parse(dayStr));
	           dto.setBeginTime(sdf2.parse(beginTime));
	           dto.setEndTime(sdf2.parse(endTime));
	           dto.setOpen(rs.getDouble("open"));
	           dto.setHigh(rs.getDouble("high"));
	           dto.setLow(rs.getDouble("low"));
	           dto.setClose(rs.getDouble("close"));
	           
	           dto.setVolumn(rs.getDouble("volumn"));
	           dto.setMavol1(rs.getDouble("mavol1"));
	           dto.setMavol2(rs.getDouble("mavol2"));
	           dto.setDif(rs.getDouble("dif"));
	           dto.setDea(rs.getDouble("dea"));
	           dto.setMacd(rs.getDouble("macd"));
	           dto.setMa5(rs.getDouble("ma5"));
	           dto.setMa10(rs.getDouble("ma10"));
	           dto.setMa20(rs.getDouble("ma20"));
	           dto.setMa60(rs.getDouble("ma60"));
	          
	           priceList.add(dto);
	        }
	        rs.close();
	        stmt.close();
	        c.close();
	      } catch ( Exception e ) {
	        System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	        System.exit(0);
	      }
	    
	    return priceList;
	}		
	
	/**
	 * 取月线数据
	 * @param stockCode
	 * @return
	 */
	public static List<StockKLinePriceDTO> getStockMonthData(
			String stockCode,String beginDay,String endDay){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Connection c = null;
	    Statement stmt = null;
	    List<StockKLinePriceDTO> priceList = new ArrayList<StockKLinePriceDTO>();
	    
	    try {
	        Class.forName("org.sqlite.JDBC");
	        c = DriverManager.getConnection("jdbc:sqlite:myStock.db");
	        c.setAutoCommit(false);
	        System.out.println("Opened database successfully");

	        stmt = c.createStatement();
	        ResultSet rs = stmt.executeQuery( "select * from stock_month_data where code='"+stockCode+"' and day<='"+endDay+
	        									"' and day>='"+beginDay+"' order by day asc;" );
	        while (rs.next()) {
	           String dayStr = rs.getString("day");
	           String beginTime = rs.getString("begin_time");
	           String endTime = rs.getString("end_time");
	           
	           StockKLinePriceDTO dto = new StockKLinePriceDTO();
	           dto.setDay(sdf.parse(dayStr));
	           dto.setBeginTime(sdf2.parse(beginTime));
	           dto.setEndTime(sdf2.parse(endTime));
	           dto.setOpen(rs.getDouble("open"));
	           dto.setHigh(rs.getDouble("high"));
	           dto.setLow(rs.getDouble("low"));
	           dto.setClose(rs.getDouble("close"));
	           
	           dto.setVolumn(rs.getDouble("volumn"));
	           dto.setMavol1(rs.getDouble("mavol1"));
	           dto.setMavol2(rs.getDouble("mavol2"));
	           dto.setDif(rs.getDouble("dif"));
	           dto.setDea(rs.getDouble("dea"));
	           dto.setMacd(rs.getDouble("macd"));
	           dto.setMa5(rs.getDouble("ma5"));
	           dto.setMa10(rs.getDouble("ma10"));
	           dto.setMa20(rs.getDouble("ma20"));
	           dto.setMa60(rs.getDouble("ma60"));
	          
	           priceList.add(dto);
	        }
	        rs.close();
	        stmt.close();
	        c.close();
	      } catch ( Exception e ) {
	        System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	        System.exit(0);
	      }
	    
	    return priceList;
	}	
	
	/**
	 * 取30分钟线数据
	 * @param stockCode
	 * @return
	 */
	public static List<StockKLinePriceDTO> getStock30MinDataByDay(
			String stockCode,String beginDay,String endDay){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Connection c = null;
	    Statement stmt = null;
	    List<StockKLinePriceDTO> priceList = new ArrayList<StockKLinePriceDTO>();
	    
	    try {
	        Class.forName("org.sqlite.JDBC");
	        c = DriverManager.getConnection("jdbc:sqlite:myStock.db");
	        c.setAutoCommit(false);
	        System.out.println("Opened database successfully");

	        stmt = c.createStatement();
	        ResultSet rs = stmt.executeQuery( "select * from stock_30min_data where code='"+stockCode+"' and day<='"+endDay+
	        									"' and day>='"+beginDay+"' order by day asc;" );
	        while (rs.next()) {
	           String dayStr = rs.getString("day");
	           String beginTime = rs.getString("begin_time");
	           String endTime = rs.getString("end_time");
	           
	           StockKLinePriceDTO dto = new StockKLinePriceDTO();
	           dto.setDay(sdf.parse(dayStr));
	           dto.setBeginTime(sdf2.parse(beginTime));
	           dto.setEndTime(sdf2.parse(endTime));
	           dto.setOpen(rs.getDouble("open"));
	           dto.setHigh(rs.getDouble("high"));
	           dto.setLow(rs.getDouble("low"));
	           dto.setClose(rs.getDouble("close"));
	           
	           dto.setVolumn(rs.getDouble("volumn"));
	           dto.setMavol1(rs.getDouble("mavol1"));
	           dto.setMavol2(rs.getDouble("mavol2"));
	           dto.setDif(rs.getDouble("dif"));
	           dto.setDea(rs.getDouble("dea"));
	           dto.setMacd(rs.getDouble("macd"));
	           dto.setMa5(rs.getDouble("ma5"));
	           dto.setMa10(rs.getDouble("ma10"));
	           dto.setMa20(rs.getDouble("ma20"));
	           dto.setMa60(rs.getDouble("ma60"));
	          
	           priceList.add(dto);
	        }
	        rs.close();
	        stmt.close();
	        c.close();
	      } catch ( Exception e ) {
	        System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	        System.exit(0);
	      }
	    
	    return priceList;
	}	
	
	/**
	 * 取5分钟线数据
	 * @param stockCode
	 * @return
	 */
	public static List<StockKLinePriceDTO> getStock5MinDataByTime(
			String stockCode,String beginQueryTime,String endQueryTime){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Connection c = null;
	    Statement stmt = null;
	    List<StockKLinePriceDTO> priceList = new ArrayList<StockKLinePriceDTO>();
	    
	    try {
	        Class.forName("org.sqlite.JDBC");
	        c = DriverManager.getConnection("jdbc:sqlite:myStock.db");
	        c.setAutoCommit(false);
	        System.out.println("Opened database successfully");

	        stmt = c.createStatement();
	        ResultSet rs = stmt
					.executeQuery("select * from stock_5min_data where code='"
							+ stockCode + "' and end_time<='" + endQueryTime
							+ "' and begin_time>='" + beginQueryTime
							+ "' order by begin_time asc;");
	        while (rs.next()) {
	           String dayStr = rs.getString("day");
	           String beginTime = rs.getString("begin_time");
	           String endTime = rs.getString("end_time");
	           
	           StockKLinePriceDTO dto = new StockKLinePriceDTO();
	           dto.setDay(sdf.parse(dayStr));
	           dto.setBeginTime(sdf2.parse(beginTime));
	           dto.setEndTime(sdf2.parse(endTime));
	           dto.setOpen(rs.getDouble("open"));
	           dto.setHigh(rs.getDouble("high"));
	           dto.setLow(rs.getDouble("low"));
	           dto.setClose(rs.getDouble("close"));
	           
	           dto.setVolumn(rs.getDouble("volumn"));
	           dto.setMavol1(rs.getDouble("mavol1"));
	           dto.setMavol2(rs.getDouble("mavol2"));
	           dto.setDif(rs.getDouble("dif"));
	           dto.setDea(rs.getDouble("dea"));
	           dto.setMacd(rs.getDouble("macd"));
	           dto.setMa5(rs.getDouble("ma5"));
	           dto.setMa10(rs.getDouble("ma10"));
	           dto.setMa20(rs.getDouble("ma20"));
	           dto.setMa60(rs.getDouble("ma60"));
	          
	           priceList.add(dto);
	        }
	        rs.close();
	        stmt.close();
	        c.close();
	      } catch ( Exception e ) {
	        System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	        System.exit(0);
	      }
	    
	    return priceList;
	}	
	
	/**
	 * 取30分钟线数据
	 * @param stockCode
	 * @return
	 */
	public static List<StockKLinePriceDTO> getStock30MinDataByTime(
			String stockCode,String beginQueryTime,String endQueryTime){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Connection c = null;
	    Statement stmt = null;
	    List<StockKLinePriceDTO> priceList = new ArrayList<StockKLinePriceDTO>();
	    
	    try {
	        Class.forName("org.sqlite.JDBC");
	        c = DriverManager.getConnection("jdbc:sqlite:myStock.db");
	        c.setAutoCommit(false);
	        System.out.println("Opened database successfully");

	        stmt = c.createStatement();
	        ResultSet rs = stmt
					.executeQuery("select * from stock_30min_data where code='"
							+ stockCode + "' and end_time<='" + endQueryTime
							+ "' and begin_time>='" + beginQueryTime
							+ "' order by begin_time asc;");
	        while (rs.next()) {
	           String dayStr = rs.getString("day");
	           String beginTime = rs.getString("begin_time");
	           String endTime = rs.getString("end_time");
	           
	           StockKLinePriceDTO dto = new StockKLinePriceDTO();
	           dto.setDay(sdf.parse(dayStr));
	           dto.setBeginTime(sdf2.parse(beginTime));
	           dto.setEndTime(sdf2.parse(endTime));
	           dto.setOpen(rs.getDouble("open"));
	           dto.setHigh(rs.getDouble("high"));
	           dto.setLow(rs.getDouble("low"));
	           dto.setClose(rs.getDouble("close"));
	           
	           dto.setVolumn(rs.getDouble("volumn"));
	           dto.setMavol1(rs.getDouble("mavol1"));
	           dto.setMavol2(rs.getDouble("mavol2"));
	           dto.setDif(rs.getDouble("dif"));
	           dto.setDea(rs.getDouble("dea"));
	           dto.setMacd(rs.getDouble("macd"));
	           dto.setMa5(rs.getDouble("ma5"));
	           dto.setMa10(rs.getDouble("ma10"));
	           dto.setMa20(rs.getDouble("ma20"));
	           dto.setMa60(rs.getDouble("ma60"));
	          
	           priceList.add(dto);
	        }
	        rs.close();
	        stmt.close();
	        c.close();
	      } catch ( Exception e ) {
	        System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	        System.exit(0);
	      }
	    
	    return priceList;
	}	
	
	/**
	 * 取测试数据
	 * @param stockCode
	 * @return
	 */
	public static List<StockKLinePriceDTO> getTestStockData(String stockCode){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Connection c = null;
	    Statement stmt = null;
	    List<StockKLinePriceDTO> priceList = new ArrayList<StockKLinePriceDTO>();
	    
	    try {
	        Class.forName("org.sqlite.JDBC");
	        c = DriverManager.getConnection("jdbc:sqlite:myStock.db");
	        c.setAutoCommit(false);
	        System.out.println("Opened database successfully");

	        stmt = c.createStatement();
	        ResultSet rs = stmt.executeQuery( "select * from stock_Test_data where code='"+stockCode+"' order by day asc;" );
	        while (rs.next()) {
	           String dayStr = rs.getString("day");
	           String beginTime = rs.getString("begin_time");
	           String endTime = rs.getString("end_time");
	           
	           StockKLinePriceDTO dto = new StockKLinePriceDTO();
	           dto.setDay(sdf.parse(dayStr));
	           dto.setBeginTime(sdf2.parse(beginTime));
	           dto.setEndTime(sdf2.parse(endTime));
	           dto.setOpen(rs.getDouble("open"));
	           dto.setHigh(rs.getDouble("high"));
	           dto.setLow(rs.getDouble("low"));
	           dto.setClose(rs.getDouble("close"));
	           
	           dto.setVolumn(rs.getDouble("volumn"));
	           dto.setMavol1(rs.getDouble("mavol1"));
	           dto.setMavol2(rs.getDouble("mavol2"));
	           dto.setDif(rs.getDouble("dif"));
	           dto.setDea(rs.getDouble("dea"));
	           dto.setMacd(rs.getDouble("macd"));
	           dto.setMa5(rs.getDouble("ma5"));
	           dto.setMa10(rs.getDouble("ma10"));
	           dto.setMa20(rs.getDouble("ma20"));
	           dto.setMa60(rs.getDouble("ma60"));
	          
	           priceList.add(dto);
	        }
	        rs.close();
	        stmt.close();
	        c.close();
	      } catch ( Exception e ) {
	        System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	        System.exit(0);
	      }
	    
//	      for(int i = 0;i<priceList.size();i++){
//	    	  StockDayPriceDTO dto = priceList.get(i);
//	    	  System.out.println(dto.getDate()+" "+dto.getOpen()+" "+dto.getMacd());
//	    	  
//	      }
	    return priceList;
	}	
	
	
}
