package pers.sam.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * K线合并后的线
 * @author Administrator
 *
 */
public class MergeLineDTO {
	
	private int stickNumber = 0;
	
	private Date beginTime;
	
	private Date endTime;
	
	private Double high;
	
	private Double low;
	
	private List<StockKLinePriceDTO> memberList = new ArrayList<StockKLinePriceDTO>(); 
	
	/**
	 * 是否顶
	 */
	private String isPeak = "N";
	
	/**
	 * 是否底
	 */
	private String isBottom = "N";
	
	public int getStickNumber() {
		return stickNumber;
	}

	public void setStickNumber(int stickNumber) {
		this.stickNumber = stickNumber;
	}

	public String getIsPeak() {
		return isPeak;
	}

	public void setIsPeak(String isPeak) {
		this.isPeak = isPeak;
	}

	public String getIsBottom() {
		return isBottom;
	}

	public void setIsBottom(String isBottom) {
		this.isBottom = isBottom;
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

	public List<StockKLinePriceDTO> getMemberList() {
		return memberList;
	}

	public void setMemberList(List<StockKLinePriceDTO> memberList) {
		this.memberList = memberList;
	}
}
