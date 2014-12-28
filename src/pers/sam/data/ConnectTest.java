package pers.sam.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class ConnectTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection c = null;
		Statement stmt = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:myStock.db");
	      
	      stmt = c.createStatement();
	      String sql = "CREATE TABLE STOCK_PRICE (  " +
	                   "ID     INTEGER  PRIMARY KEY AUTOINCREMENT, " +
	                   "TIME   DATETIME NOT NULL," + 
	                   "OPEN   REAL," +
	                   "HIGH   REAL," +
	                   "LOW    REAL," +
	                   "CLOSE  REAL," +
	                   "VOLUMN REAL," +
	                   "MAVOL1 REAL," +
	                   "MAVOL2 REAL," +
	                   "DIF    REAL," +
	                   "DEA    REAL," +
	                   "MACD   REAL," +
	                   "MA5    REAL," +
	                   "MA10   REAL," +
	                   "MA20   REAL," +
	                   "MA60   REAL " +
	                   ")"; 
	      stmt.executeUpdate(sql);
	      stmt.close();
	      c.close();	      
	      	      
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("Opened database successfully");
	}

}
