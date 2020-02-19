/**
 * 
 */
package com.neel.example;

import java.util.HashMap;
import java.util.Map;

/**
 * @author nsalbarde
 *
 */
public class LongestSubStrWithoutRptChar {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(lengthOfLongestSubstring("abcabcbb"));
	}
	
	public static int lengthOfLongestSubstring(String s) {
		int length = 0;
		int currentLen = 0;
		Map<Character, Integer> map = new HashMap<Character, Integer>();
		for(int index = 0; index < s.length(); index++) {
			System.out.println("Char : "+s.charAt(index));
			if(map.containsKey(s.charAt(index))) {
				System.out.println("Found");
				if(currentLen > length) 
				{
					length = currentLen;
				}
				index = map.get(s.charAt(index));
				map.clear();
				currentLen = 0;
			}else {
				System.out.println("Not Found");
				map.put(s.charAt(index), index);
				currentLen++;
			}
			System.out.println("Length : "+length);
			System.out.println("Current Len : "+currentLen);
			System.out.println("Index : "+index);
			System.out.println("");
		}
		if(currentLen > length) {
			length = currentLen;
		}
		return length;
	}
}
