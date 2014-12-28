package pers.sam.dto;

import java.util.Date;
import java.util.List;

/**
 * Ïß¶ÎDTO
 * @author Administrator
 *
 */
public class SegmentDTO {
	
	private Date beginTime;
	
	private Date endTime;
	
	private Double high;
	
	private Double low;
	
	private List<MergeLineDTO> mergeLineList;
	
	//up\down
	private String trend;
	
	public List<MergeLineDTO> getMergeLineList() {
		return mergeLineList;
	}

	public void setMergeLineList(List<MergeLineDTO> mergeLineList) {
		this.mergeLineList = mergeLineList;
	}

	public String getTrend() {
		return trend;
	}

	public void setTrend(String trend) {
		this.trend = trend;
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
}
