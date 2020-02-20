/**
 * 
 */
package com.neel.example;

/**
 * @author nsalbarde
 *
 */
public class MedianOfTwoSortedArray {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int[] a = {1,2};
		int[] b = {3,4};
		System.out.println(findMedianSortedArrays(a, b));
	}

	public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
		int i=0;
		int j=0;
		int[] values = new int[nums1.length + nums2.length];
		int index = 0;
		while(i < nums1.length || j < nums2.length) {
			if(i < nums1.length && j < nums2.length) {
				if(nums1[i] < nums2[j]) {
					values[index] = nums1[i++];
				}else {
					values[index] = nums2[j++];
				}
			}else if(i < nums1.length) {
				values[index] = nums1[i++];
			}else {
				values[index] = nums2[j++];
			}
			index++;
		}
		
		for(int a=0; a < values.length; a++) {
			System.out.println(values[a]);
		}
		
		if(values.length % 2 == 0) {
			System.out.println("Even");
			int p = values.length/2;
			return Double.valueOf(values[p-1] + values[p])/2;
		}else {
			System.out.println("Odd");
			return Double.valueOf(values[values.length/2]);
		}
	}
}
