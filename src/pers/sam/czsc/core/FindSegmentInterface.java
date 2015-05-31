package pers.sam.czsc.core;

import java.util.List;

import pers.sam.czsc.dto.StrokeDTO;

/**
 * 线段划分接口
 * @author Administrator
 *
 */
public interface FindSegmentInterface {
	
	/**
	 * 输入分笔序列strokeList
	 * @param strokeList
	 */
	public void findSegment(List <StrokeDTO> strokeList);
	
}
