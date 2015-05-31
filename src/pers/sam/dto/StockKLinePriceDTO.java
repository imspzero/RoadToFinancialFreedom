package pers.sam.dto;

import java.util.Date;

/**
 * KÏßdto
 * @author lizandeng(Sam Lee)
 * @version 2015-5-31 ÏÂÎç03:16:28
 */
public class StockKLinePriceDTO {
	
	private Date day;
	
	private Date beginTime;
	
	private Date endTime;
	
	private Double open;
	
	private Double high;
	
	private Double low;
	
	private Double close;
	
	private Double volumn;
	
	private Double mavol1;
	
	private Double mavol2;
	
	private Double dif;
	
	private Double dea;
	
	private Double macd;
	
	private Double ma5;
	
	private Double ma10;
	
	private Double ma20;
	
	private Double ma60;

	public Date getDay() {
		return day;
	}

	public void setDay(Date day) {
		this.day = day;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Double getOpen() {
		return open;
	}

	public void setOpen(Double open) {
		this.open = open;
	}

	public Double getHigh() {
		return high;
	}

	public void setHigh(Double high) {
		this.high = high;
	}

	public Double getLow() {
		return low;
	}

	public void setLow(Double low) {
		this.low = low;
	}

	public Double getClose() {
		return close;
	}

	public void setClose(Double close) {
		this.close = close;
	}

	public Double getVolumn() {
		return volumn;
	}

	public void setVolumn(Double volumn) {
		this.volumn = volumn;
	}

	public Double getMavol1() {
		return mavol1;
	}

	public void setMavol1(Double mavol1) {
		this.mavol1 = mavol1;
	}

	public Double getMavol2() {
		return mavol2;
	}

	public void setMavol2(Double mavol2) {
		this.mavol2 = mavol2;
	}

	public Double getDif() {
		return dif;
	}

	public void setDif(Double dif) {
		this.dif = dif;
	}

	public Double getDea() {
		return dea;
	}

	public void setDea(Double dea) {
		this.dea = dea;
	}

	public Double getMacd() {
		return macd;
	}

	public void setMacd(Double macd) {
		this.macd = macd;
	}

	public Double getMa5() {
		return ma5;
	}

	public void setMa5(Double ma5) {
		this.ma5 = ma5;
	}

	public Double getMa10() {
		return ma10;
	}

	public void setMa10(Double ma10) {
		this.ma10 = ma10;
	}

	public Double getMa20() {
		return ma20;
	}

	public void setMa20(Double ma20) {
		this.ma20 = ma20;
	}

	public Double getMa60() {
		return ma60;
	}

	public void setMa60(Double ma60) {
		this.ma60 = ma60;
	}
}
