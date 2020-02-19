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
public class TwoSum {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int[] nums = {2, 7, 11, 15};
		int[] vals = twoSum(nums, 9);
		System.out.println(nums[vals[0]] +" : "+nums[vals[1]]);
	}
	
	public static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		for (int i = 0; i < nums.length; i++) {
			int value = target - nums[i];
			if (map.containsKey(value)) {
				return new int[]{i,map.get(value)};
			}
			map.put(nums[i], i);
		}
		return new int[]{};
    }

}
