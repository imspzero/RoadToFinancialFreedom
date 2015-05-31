package pers.sam.czsc.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 特征序列元素
 * @author Administrator
 *
 */
public class FeatureElementDTO {

	private Date beginTime;
	
	private Date endTime;
	
	private double high;
	
	private double low;
	
	//特性序列对应原分笔序列上的序号
	private List<Integer> touchIndexList = new ArrayList<Integer>();
	
	public List<Integer> getTouchIndexList() {
		return touchIndexList;
	}

	public void setTouchIndexList(List<Integer> touchIndexList) {
		this.touchIndexList = touchIndexList;
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

	public double getHigh() {
		return high;
	}

	public void setHigh(double high) {
		this.high = high;
	}

	public double getLow() {
		return low;
	}

	public void setLow(double low) {
		this.low = low;
	}
}
