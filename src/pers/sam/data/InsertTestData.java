package pers.sam.data;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.csvreader.CsvReader;

import pers.sam.dto.StockKLinePriceDTO;
import pers.sam.util.StockDateUtil;

public class InsertTestData {

	
	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	public static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * @param args
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 * @throws ParseException 
	 */
	public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException, ParseException {
		
		/**
		 * 分笔测试
		 */
//		String filePath = "C:\\Documents and Settings\\Administrator\\桌面\\国信数据导出\\T00001.csv";
//		String stockCode ="T00001";
//		insertTestStockData(stockCode,filePath);
		
		/**
		 * 顶底分型测试
		 */
		String filePath = "C:\\Documents and Settings\\Administrator\\桌面\\国信数据导出\\T00002.csv";
		String stockCode ="T00002";
		insertTestStockData(stockCode,filePath);		
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
	private static void insertTestStockData(String stockCode,String filePath)
			throws FileNotFoundException, IOException, ClassNotFoundException,
			SQLException, ParseException {
		CsvReader reader = new CsvReader(filePath);
		
		//跳过前五行
		for(int i = 0;i<3;i++){
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
			
			if(null==reader.get(1)||"".equals(reader.get(1))){
				continue;
			}
			
			String dateStr = reader.get(0);
			dateStr=dateStr.replace('/', '-');
			
			String sql = "insert into STOCK_TEST_DATA"+
					"(code,day,begin_time,end_time,open,high,low,close) values ("+
					"'"+stockCode+"',"+
					"'"+sdf.format(sdf.parse(dateStr))+"',"+
					"'"+StockDateUtil.getDayOpenTime(dateStr)+"',"+
					"'"+StockDateUtil.getDayCloseTime(dateStr)+"',"+
					"'"+reader.get(2)+"',"+
					"'"+reader.get(2)+"',"+
					"'"+reader.get(1)+"',"+
					"'"+reader.get(1)+"');";
			System.out.println(sql);
			stmt.executeUpdate(sql);	
		}
	    stmt.close();
	    c.commit();
	    c.close();
		reader.close();
	}
	
}
