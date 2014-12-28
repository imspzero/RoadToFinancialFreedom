package pers.sam.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pers.sam.dto.StockKLinePriceDTO;

public class QueryTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Connection c = null;
	    Statement stmt = null;
	    List<StockKLinePriceDTO> priceList = new ArrayList<StockKLinePriceDTO>();
	    
	    try {
	        Class.forName("org.sqlite.JDBC");
	        c = DriverManager.getConnection("jdbc:sqlite:myStock.db");
	        c.setAutoCommit(false);
	        System.out.println("Opened database successfully");

	        stmt = c.createStatement();
	        ResultSet rs = stmt.executeQuery( "select * from stock_price order by time;" );
	        while (rs.next()) {
	           String dateStr = rs.getString("time");
	           
	           StockKLinePriceDTO dto = new StockKLinePriceDTO();
	           dto.setDay(sdf.parse(dateStr));
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
	    
	      for(int i = 0;i<priceList.size();i++){
	    	  StockKLinePriceDTO dto = priceList.get(i);
	    	  System.out.println(dto.getDay()+" "+dto.getOpen()+" "+dto.getMacd());
	    	  
	      }
	    
	}
}
