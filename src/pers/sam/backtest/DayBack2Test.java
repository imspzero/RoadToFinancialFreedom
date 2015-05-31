package pers.sam.backtest;

import java.text.SimpleDateFormat;
import java.util.List;

import pers.sam.dto.StockKLinePriceDTO;
import pers.sam.util.GetStockDataFromSqliteUtil;

public class DayBack2Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String stockCode ="000100";
		
		List<StockKLinePriceDTO> priceList = GetStockDataFromSqliteUtil.getDayStockData(stockCode,"","");
		
		/**
		 * 
		 * 买入策略：macd小于0，并且macd将比上一天的短
		 * 卖出策略：macd大于0，并且macd比上一天的短
		 * 
		 */
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		String lastAction = "sell";
		
		Double totalMoney = new Double(10000.00);
		Double assetValue = new Double(10000.00);
		
		Double stockValue = new Double(0);
		Double stockNum = new Double(0);
		Double cashValue = new Double(0);
		
		for(int i = 0;i<priceList.size();i++){
			StockKLinePriceDTO today = priceList.get(i);
			
			if(lastAction.equals("sell")&&today.getMacd()>0){
				continue;
			}else if(lastAction.equals("buy")&&today.getMacd()<0){
				continue;
			}else if(lastAction.equals("sell")&&today.getMacd()<0){
				
				//观察是否是买入点
				StockKLinePriceDTO lastday = priceList.get(i-1);
				
				if(today.getMacd()>lastday.getMacd()){//开始缩短,买入
					
					Double dealPrice = getTradePrice(today);
					totalMoney = assetValue;
					stockNum = Math.floor(totalMoney/dealPrice);
					cashValue = totalMoney - dealPrice*stockNum;
					stockValue =dealPrice*stockNum;
					assetValue = cashValue+stockValue;
					lastAction ="buy";
					
					System.out.println("日期："+sdf.format(today.getDay())+"买入"+stockNum+"股," +
							"每股价格"+dealPrice+","+
							"现金总价值"+cashValue+","+
							"股票总价值"+stockValue+","+
							"资产总价值"+assetValue);
				}
			}else if(lastAction.equals("buy")&&today.getMacd()>0){
				
				//观察是否是卖出点
				StockKLinePriceDTO lastday = priceList.get(i-1);
				if(today.getMacd()<lastday.getMacd()){
					Double dealPrice = getTradePrice(today);
					stockValue = dealPrice*stockNum;
					cashValue = cashValue + stockValue;
					stockValue = new Double(0);
					assetValue = cashValue+stockValue;
					lastAction ="sell";
					
					System.out.println("日期："+sdf.format(today.getDay())+"卖出"+stockNum+"股," +
							"每股价格"+dealPrice+","+
							"现金总价值"+cashValue+","+
							"股票总价值"+"0"+","+
							"资产总价值"+assetValue);
				}
			}
		}
	}
	
	public static Double getTradePrice(StockKLinePriceDTO dto){
//		Double dealPrice = (dto.getOpen()+dto.getClose()+dto.getHigh()+dto.getLow())/4;
		Double dealPrice = (dto.getOpen()+dto.getClose())/2;
		return dealPrice;
	}

}
