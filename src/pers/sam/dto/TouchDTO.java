package pers.sam.dto;

/**
 * 分笔DTO
 * @author Administrator
 *
 */
public class TouchDTO {
	
	//开始
	private MergeLineDTO  startMLine;
	
	//结束
	private MergeLineDTO endMLine;

	//方向，向上或者向下  up/down
	private String direction;
	
	public MergeLineDTO getStartMLine() {
		return startMLine;
	}

	public void setStartMLine(MergeLineDTO startMLine) {
		this.startMLine = startMLine;
	}

	public MergeLineDTO getEndMLine() {
		return endMLine;
	}

	public void setEndMLine(MergeLineDTO endMLine) {
		this.endMLine = endMLine;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}
	
}
