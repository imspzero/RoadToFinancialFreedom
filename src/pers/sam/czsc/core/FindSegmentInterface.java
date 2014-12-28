package pers.sam.czsc.core;

import java.util.List;

import pers.sam.dto.TouchDTO;

/**
 * 线段划分接口
 * @author Administrator
 *
 */
public interface FindSegmentInterface {
	
	public void findSegment(List <TouchDTO> touchList);
	
}
