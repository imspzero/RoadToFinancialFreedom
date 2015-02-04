package pers.sam.czsc.core.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import pers.sam.dto.MergeLineDTO;
import pers.sam.dto.TouchDTO;

/**
 * 从resource中获取数据
 * @author Administrator
 *
 */
public class GetDataUtil {
	
	
	public static void main(String args[]) throws ParseException{
		
		String fileName = System.getProperty("user.dir")+"/resource/79_01/79_01.txt";
		
//		System.out.println(System.getProperty("user.dir")+"/resource/79_01/79_01.txt");
//		
//		List<Double> numberList = readFileByLines(fileName);
//		
//		System.out.println(numberList);
//		
//		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		Date lastDate = sdf.parse("1900-1-1");
//		System.out.println(getNextDate(lastDate));
		
		List<TouchDTO> touchList = getTestData(fileName);
		
		for(int i = 0;i<touchList.size();i++){
			TouchDTO touchDTO = touchList.get(i);
			
			System.out.println(
					sdf.format(touchDTO.getStartMLine().getBeginTime())+" "+
					sdf.format(touchDTO.getEndMLine().getBeginTime())+" "+touchDTO.getStartMLine().getHigh()+" "
					+touchDTO.getEndMLine().getHigh()
			);
			
		}
		
	}
	
	/**
	 * 传入文件名称，获取模拟的分笔数据
	 * @param fileName
	 * @return
	 * @throws ParseException 
	 */
	public static List<TouchDTO> getTestData(String fileName) throws ParseException{
		
		List<Double> numberList = readFileByLines(fileName);
		
		List <TouchDTO> touchList = new ArrayList();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		TouchDTO touchDTO = null; 
		MergeLineDTO startMLine = null;
		MergeLineDTO endMLine = null;
		
		Double headNumber  = numberList.get(0);
		Double tailNumber = null;
		Date lastDate = sdf.parse("1900-1-1");
		
		for(int i = 1;i<numberList.size();i++){
			
			tailNumber = numberList.get(i);
			touchDTO = new TouchDTO();
			
			startMLine = new MergeLineDTO();
			startMLine.setBeginTime(lastDate);
			startMLine.setEndTime(lastDate);
			startMLine.setHigh(headNumber);
			startMLine.setLow(headNumber);
			
			endMLine = new MergeLineDTO();
			endMLine.setBeginTime(getNextDate(lastDate));
			endMLine.setEndTime(getNextDate(lastDate));
			endMLine.setHigh(tailNumber);
			endMLine.setLow(tailNumber);
			
			touchDTO.setStartMLine(startMLine);
			touchDTO.setEndMLine(endMLine);
			
			if(touchDTO.getEndMLine().getHigh()
					>touchDTO.getStartMLine().getHigh()){
				touchDTO.setDirection("up");
			}else{
				touchDTO.setDirection("down");
			}
			
			touchList.add(touchDTO);
			
			headNumber = tailNumber;
			lastDate = getNextDate(lastDate);
		}
		
		return touchList;
	}
	
	
	/**
	 * 获取下一年
	 */
	public static Date getNextDate(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.YEAR, c.get(Calendar.YEAR)+1);
		return c.getTime();
	}
	
	/**
	 * 获取X轴的坐标
	 * @param fileName
	 * @return
	 */
	public static List<Double> readFileByLines(String fileName) {
		List<Double> numberList = new ArrayList<Double>(); 
		
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
//            System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
            	
            	numberList.add(Double.valueOf(tempString.trim()));
                //System.out.println("line " + line + ": " + tempString);
                line++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        
        return numberList;
    }
}
