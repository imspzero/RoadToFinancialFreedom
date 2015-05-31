package pers.sam.dto;

import java.util.Date;

/**
 * 分笔状态DTO（stock_parting_info），即"当下"
 * @author lizandeng(Sam Lee)
 * @version 2015-5-31 下午11:25:56
 */
public class PartingInfoDTO {
	
	//分型构造中
	public static String PARTING_FORMING="P[0]-forming";
	//分型确认延伸为笔的过程中
	public static String PARTING_EXTENDING="P[1]-extending";
	
	//向上笔
	public static String DIRECTION_UP ="D[1]-up";
	//向下笔
	public static String DIRECTION_DOWN ="D[-1]-down";
	
	/**
	 * 股票编码
	 */
	private String stockCode;
	
	/**
	 * 分析周期
	 */
	private String period;
	
	/**
	 * 分型出现的时间点
	 */
	private Date partingDate;
	
	/**
	 * 笔的方向（在向上还是向下笔中）
	 */
	private String partingDirection;
	
	/**
	 * 当前的状态（PARTING_STATUS）
	 */
	private String partingStatus;

	public String getStockCode() {
		return stockCode;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public Date getPartingDate() {
		return partingDate;
	}

	public void setPartingDate(Date partingDate) {
		this.partingDate = partingDate;
	}

	public String getPartingDirection() {
		return partingDirection;
	}

	public void setPartingDirection(String partingDirection) {
		this.partingDirection = partingDirection;
	}

	public String getPartingStatus() {
		return partingStatus;
	}

	public void setPartingStatus(String partingStatus) {
		this.partingStatus = partingStatus;
	} 
}
