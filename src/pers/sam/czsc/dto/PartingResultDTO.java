package pers.sam.czsc.dto;

import java.util.List;

/**
 * 分笔结果对象，包含三部分：
 * 1）分笔列 2） K线合并结果列表 3）分笔结果数组
 * @author lizandeng(Sam Lee)
 * @version 2015-6-2 下午09:14:03
 */
public class PartingResultDTO {
	
	//分笔列表
	public List<StrokeDTO> strokeList;
	//K线合并结果列表
	public List<MergeLineDTO> mergeLineList;
	//分笔结果数组（和mergeLineList一起，可得出最终的分笔结果）
	public boolean sectionResultArray[];

	public List<StrokeDTO> getStrokeList() {
		return strokeList;
	}

	public void setStrokeList(List<StrokeDTO> strokeList) {
		this.strokeList = strokeList;
	}

	public List<MergeLineDTO> getMergeLineList() {
		return mergeLineList;
	}

	public void setMergeLineList(List<MergeLineDTO> mergeLineList) {
		this.mergeLineList = mergeLineList;
	}

	public boolean[] getSectionResultArray() {
		return sectionResultArray;
	}

	public void setSectionResultArray(boolean[] sectionResultArray) {
		this.sectionResultArray = sectionResultArray;
	}
	
}
