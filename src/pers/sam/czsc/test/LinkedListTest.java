package pers.sam.czsc.test;

import java.util.LinkedList;
import java.util.List;

import pers.sam.czsc.dto.StrokeDTO;

/**
 * 
 * @author Administrator
 *	
 */
public class LinkedListTest {
	

	public static void main(String args[]){
		
		List<String> list = new LinkedList<String>();
		
		
		list.add("1");
		list.add("2");
		list.add("3");
		list.add("k");
		list.add("5");
		list.add("6");
		list.add("7");
		
		System.out.println(list);
		
		list.remove(3);
		
		System.out.println(list);
		
		list.add(3,"4");
		
		System.out.println(list);
		
	}

}
