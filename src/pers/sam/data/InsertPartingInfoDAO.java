package pers.sam.data;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.Date;

import pers.sam.dto.PartingInfoDTO;
import pers.sam.util.PeriodUtil;
import pers.sam.util.StockDateUtil;

/**
 * 插入分笔状态信息
 * @author lizandeng(Sam Lee)
 * @version 2015-5-31 下午11:33:04
 */
public class InsertPartingInfoDAO {

	public static void main(String args[]) throws ClassNotFoundException, SQLException{
		
		PartingInfoDTO partingInfo = new PartingInfoDTO();
		
		partingInfo.setStockCode("999999");
		partingInfo.setPeriod(PeriodUtil.PERIOD_30_MIN);
		partingInfo.setPartingDate(new Date());
		partingInfo.setPartingDirection(PartingInfoDTO.DIRECTION_UP);
		partingInfo.setPartingStatus(PartingInfoDTO.PARTING_EXTENDING);
		
		InsertPartingInfoDAO.insert(partingInfo);
		
	}
	
	
	/**
	 * 
	 * @param stockCode
	 * @param filePath
	 * @throws ClassNotFoundException 
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws SQLException 
	 * @throws SQLException
	 * @throws ParseException
	 */
	public static void insert(PartingInfoDTO partingInfo) throws ClassNotFoundException, SQLException{
		
		Connection c = null;
		Statement stmt = null;
		Class.forName("org.sqlite.JDBC");
	    c = DriverManager.getConnection("jdbc:sqlite:myStock.db");
	    c.setAutoCommit(false);
	    stmt = c.createStatement();
		
	    String sql = "insert into stock_parting_info"+
		"(code,period,parting_date,parting_direction,parting_status) values ("+
		"'"+partingInfo.getStockCode()+"',"+
		"'"+partingInfo.getPeriod()+"',"+
		"'"+StockDateUtil.SDF_TIME.format(partingInfo.getPartingDate())+"',"+
		"'"+partingInfo.getPartingDirection()+"',"+
		"'"+partingInfo.getPartingStatus()+"');";
	    System.out.println(sql);
	    
		stmt.executeUpdate(sql);	

	    stmt.close();
	    c.commit();
	    c.close();
	}	
}
