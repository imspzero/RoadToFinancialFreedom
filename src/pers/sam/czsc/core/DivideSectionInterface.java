package pers.sam.czsc.core;

import java.util.List;

import pers.sam.czsc.dto.MergeLineDTO;

/**
 * 分笔 接口
 * @author Administrator
 *
 */
public interface DivideSectionInterface {
	
	/**
	 * 分笔主逻辑
	 * 假如没有找到结果，直接抛出异常
	 * @param mergeSticksList
	 * @return
	 * @throws Exception
	 */
	public boolean [] divideSection(List<MergeLineDTO> mergeSticksList) throws Exception;
	
}
