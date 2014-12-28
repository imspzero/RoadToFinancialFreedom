package pers.sam.data;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import pers.sam.util.StockDateUtil;

import com.csvreader.CsvReader;


public class InsertData {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 * @throws ParseException 
	 */
	public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException, ParseException {
		
//		String filePath = "C:\\Users\\hh\\Desktop\\国信数据导出\\000100-day.csv";
//		String stockCode ="000100";
//		insertDayStockData(stockCode,filePath);
		
//		String filePath = "C:\\Users\\hh\\Desktop\\国信数据导出\\000100-30min.csv";
//		String stockCode ="000100";
//		insert30MinStockData(stockCode,filePath);
		
//		String filePath = "C:\\Users\\hh\\Desktop\\国信数据导出\\000100-week.csv";
//		String stockCode ="000100";
//		insertWeekStockData(stockCode,filePath);
		
//		String filePath = "C:\\Documents and Settings\\Administrator\\桌面\\国信数据导出\\999999-month.csv";
//		String stockCode ="999999";
//		insertMonthStockData(stockCode,filePath);
		
		String filePath = "C:\\Documents and Settings\\Administrator\\桌面\\国信数据导出\\999999-30min.csv";
		String stockCode ="999999";
		insert30MinStockData(stockCode,filePath);		
		
	}
	
	/**
	 * 插入日线数据
	 * 附件日期是格式： yyyy/MM/dd
	 * @param stockCode
	 * @param filePath
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws ParseException
	 */
	private static void insertDayStockData(String stockCode,String filePath)
			throws FileNotFoundException, IOException, ClassNotFoundException,
			SQLException, ParseException {
		CsvReader reader = new CsvReader(filePath);
		
		//跳过前五行
		for(int i = 0;i<5;i++){
			reader.readRecord();
		}
		
//		System.out.println(reader.getColumnCount());
//		System.out.println(reader.get(0)+"\t"+reader.get(1)+"\t"+reader.get(2)
//				          +"\t"+reader.get(3)+"\t"+reader.get(4)+"\t"+reader.get(5));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Connection c = null;
		Statement stmt = null;
		Class.forName("org.sqlite.JDBC");
	    c = DriverManager.getConnection("jdbc:sqlite:myStock.db");
	    c.setAutoCommit(false);
	    stmt = c.createStatement();
		
		while(reader.readRecord()){
//			System.out.println(reader.getColumnCount());
//			System.out.println(reader.get(0)+"\t"+reader.get(1)+"\t"+reader.get(2)
//			          +"\t"+reader.get(3)+"\t"+reader.get(4)+"\t"+reader.get(5));
//			
			
			if(null==reader.get(1)||"".equals(reader.get(1))){
				continue;
			}
			
			String dateStr = reader.get(0);
			dateStr=dateStr.replace('/', '-');
			
			String sql = "insert into STOCK_DAY_DATA"+
					"(code,day,begin_time,end_time,open,high,low,close,ma5,ma10,ma20,ma60,volumn,mavol1,mavol2,dif,dea,macd) values ("+
					"'"+stockCode+"',"+
					"'"+sdf.format(sdf.parse(dateStr))+"',"+
					"'"+StockDateUtil.getDayOpenTime(dateStr)+"',"+
					"'"+StockDateUtil.getDayCloseTime(dateStr)+"',"+
					"'"+reader.get(1)+"',"+
					"'"+reader.get(2)+"',"+
					"'"+reader.get(3)+"',"+
					"'"+reader.get(4)+"',"+
					"'"+reader.get(6)+"',"+
					"'"+reader.get(7)+"',"+
					"'"+reader.get(8)+"',"+
					"'"+reader.get(9)+"',"+
					"'"+reader.get(10)+"',"+
					"'"+reader.get(11)+"',"+
					"'"+reader.get(12)+"',"+
					"'"+reader.get(13)+"',"+
					"'"+reader.get(14)+"',"+
					"'"+reader.get(15)+"'"+
					");";
			System.out.println(sql);
			
			stmt.executeUpdate(sql);	
		}
	    stmt.close();
	    c.commit();
	    c.close();
		reader.close();
	}
	
	/**
	 * 插入30分钟线数据
	 * 附件日期是格式： yyyy/MM/dd-10:00
	 * @param stockCode
	 * @param filePath
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws ParseException
	 */
	private static void insert30MinStockData(String stockCode,String filePath)
			throws FileNotFoundException, IOException, ClassNotFoundException,
			SQLException, ParseException {
		CsvReader reader = new CsvReader(filePath);
		
		//跳过前五行
		for(int i = 0;i<5;i++){
			reader.readRecord();
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Connection c = null;
		Statement stmt = null;
		Class.forName("org.sqlite.JDBC");
	    c = DriverManager.getConnection("jdbc:sqlite:myStock.db");
	    c.setAutoCommit(false);
	    stmt = c.createStatement();
		
		while(reader.readRecord()){
//			System.out.println(reader.getColumnCount());
//			System.out.println(reader.get(0)+"\t"+reader.get(1)+"\t"+reader.get(2)
//			          +"\t"+reader.get(3)+"\t"+reader.get(4)+"\t"+reader.get(5));
//			
			if(null==reader.get(1)||"".equals(reader.get(1))){
				continue;
			}
			
			//yyyy/MM/dd-10:00
			String dateStr = reader.get(0);
			
			String dayStr=dateStr.split("-")[0];
			dayStr=dayStr.replace('/', '-');
			
			dayStr=sdf.format(sdf.parse(dayStr));
			
			
			String sql = "insert into STOCK_30MIN_DATA"+
					"(code,day,begin_time,end_time,open,high,low,close,ma5,ma10,ma20,ma60,volumn,mavol1,mavol2,dif,dea,macd) values ("+
					"'"+stockCode+"',"+
					"'"+dayStr+"',"+
					"'"+StockDateUtil.get30MinOpenTime(dateStr)+"',"+
					"'"+StockDateUtil.get30MinCloseTime(dateStr)+"',"+
					"'"+reader.get(1)+"',"+
					"'"+reader.get(2)+"',"+
					"'"+reader.get(3)+"',"+
					"'"+reader.get(4)+"',"+
					"'"+reader.get(6)+"',"+
					"'"+reader.get(7)+"',"+
					"'"+reader.get(8)+"',"+
					"'"+reader.get(9)+"',"+
					"'"+reader.get(10)+"',"+
					"'"+reader.get(11)+"',"+
					"'"+reader.get(12)+"',"+
					"'"+reader.get(13)+"',"+
					"'"+reader.get(14)+"',"+
					"'"+reader.get(15)+"'"+
					");";
			System.out.println(sql);
			
			stmt.executeUpdate(sql);	
		}
	    stmt.close();
	    c.commit();
	    c.close();
		reader.close();
	}
	
	/**
	 * 插入周线数据
	 * 附件日期是格式： yyyy/MM/dd-10:00
	 * @param stockCode
	 * @param filePath
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws ParseException
	 */
	private static void insertWeekStockData(String stockCode,String filePath)
			throws FileNotFoundException, IOException, ClassNotFoundException,
			SQLException, ParseException {
		CsvReader reader = new CsvReader(filePath);
		
		//跳过前五行
		for(int i = 0;i<5;i++){
			reader.readRecord();
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Connection c = null;
		Statement stmt = null;
		Class.forName("org.sqlite.JDBC");
	    c = DriverManager.getConnection("jdbc:sqlite:myStock.db");
	    c.setAutoCommit(false);
	    stmt = c.createStatement();
		
		while(reader.readRecord()){
//			System.out.println(reader.getColumnCount());
//			System.out.println(reader.get(0)+"\t"+reader.get(1)+"\t"+reader.get(2)
//			          +"\t"+reader.get(3)+"\t"+reader.get(4)+"\t"+reader.get(5));
//			
			if(null==reader.get(1)||"".equals(reader.get(1))){
				continue;
			}
			
			//yyyy/MM/dd-10:00
			String dateStr = reader.get(0);
			dateStr=dateStr.replace('/', '-');
			
			String sql = "insert into STOCK_WEEK_DATA"+
					"(code,day,begin_time,end_time,open,high,low,close,ma5,ma10,ma20,ma60,volumn,mavol1,mavol2,dif,dea,macd) values ("+
					"'"+stockCode+"',"+
					"'"+sdf.format(sdf.parse(dateStr))+"',"+
					"'"+StockDateUtil.getWeekOpenTime(reader.get(0))+"',"+
					"'"+StockDateUtil.getWeekCloseTime(reader.get(0))+"',"+
					"'"+reader.get(1)+"',"+
					"'"+reader.get(2)+"',"+
					"'"+reader.get(3)+"',"+
					"'"+reader.get(4)+"',"+
					"'"+reader.get(6)+"',"+
					"'"+reader.get(7)+"',"+
					"'"+reader.get(8)+"',"+
					"'"+reader.get(9)+"',"+
					"'"+reader.get(10)+"',"+
					"'"+reader.get(11)+"',"+
					"'"+reader.get(12)+"',"+
					"'"+reader.get(13)+"',"+
					"'"+reader.get(14)+"',"+
					"'"+reader.get(15)+"'"+
					");";
			System.out.println(sql);
			stmt.executeUpdate(sql);
		}
	    stmt.close();
	    c.commit();
	    c.close();
		reader.close();
	}
	
	/**
	 * 插入月线数据
	 * 附件日期是格式： yyyy/MM/dd
	 * @param stockCode
	 * @param filePath
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws ParseException
	 */
	private static void insertMonthStockData(String stockCode,String filePath)
			throws FileNotFoundException, IOException, ClassNotFoundException,
			SQLException, ParseException {
		CsvReader reader = new CsvReader(filePath);
		
		//跳过前五行
		for(int i = 0;i<3;i++){
			reader.readRecord();
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Connection c = null;
		Statement stmt = null;
		Class.forName("org.sqlite.JDBC");
	    c = DriverManager.getConnection("jdbc:sqlite:myStock.db");
	    c.setAutoCommit(false);
	    stmt = c.createStatement();
		
		while(reader.readRecord()){
//			System.out.println(reader.getColumnCount());
//			System.out.println(reader.get(0)+"\t"+reader.get(1)+"\t"+reader.get(2)
//			          +"\t"+reader.get(3)+"\t"+reader.get(4)+"\t"+reader.get(5));
			if(null==reader.get(1)||"".equals(reader.get(1))){
				continue;
			}
			//yyyy/MM/dd-10:00
			String dateStr = reader.get(0);
			dateStr=dateStr.replace('/', '-');
			
			String sql = "insert into STOCK_MONTH_DATA"+
					"(code,day,begin_time,end_time,open,high,low,close,ma5,ma10,ma20,ma60,volumn,mavol1,mavol2,dif,dea,macd) values ("+
					"'"+stockCode+"',"+
					"'"+sdf.format(sdf.parse(dateStr))+"',"+
					"'"+StockDateUtil.getMonthOpenTime(reader.get(0))+"',"+
					"'"+StockDateUtil.getMonthCloseTime(reader.get(0))+"',"+
					"'"+reader.get(1)+"',"+
					"'"+reader.get(2)+"',"+
					"'"+reader.get(3)+"',"+
					"'"+reader.get(4)+"',"+
					"'"+reader.get(6)+"',"+
					"'"+reader.get(7)+"',"+
					"'"+reader.get(8)+"',"+
					"'"+reader.get(9)+"',"+
					"'"+reader.get(5)+"',"+
					"'',"+
					"'',"+
					"'"+reader.get(10)+"',"+
					"'"+reader.get(11)+"',"+
					"'"+reader.get(12)+"'"+
					");";
			System.out.println(sql);
			//stmt.executeUpdate(sql);
		}
	    stmt.close();
	    c.commit();
	    c.close();
		reader.close();
	}
	
}
