package com.htwokey.blog.utils;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * 用于摘要的MD系列 加密MD5 同时还有MD5 对文件校验
 * @author hch
 */
public class DigestMD {

	private static final Logger logger = LoggerFactory.getLogger(DigestMD.class);

	
	/**
	 * MD5摘要 用的是commons codec 的MD5
	 * @param data
	 * @return
	 */
	public static String MD5(String data)
	{
		if (data==null) 
		{
			return null;
		}
		return DigestUtils.md5Hex(data);
		
	}

	/**
	 * md5加盐算法，在data中混入随机key在进行md5加密
	 * @param data 要加密的数据
	 * @param key 随机key
	 * @return
	 */
	public static String Hmac(String data, String key){
		if(data == null){
			return null;
		}
		String ms = key + data;
		return DigestUtils.md5Hex(ms);
	}
	
	/**
	 * MD5摘要 用的是commons codec 的MD5
	 * @param file 文件
	 * @return
	 */
	public static String MD5(File file) 
	{
		Date st = new Date();
		if (file==null)
		{
			return null;
		}
		
			 String r =null; 
			try {
				FileInputStream  input = new FileInputStream(file);
				r=DigestUtils.md5Hex(input);
				input.close(); 
			} catch (IOException e) {
				logger.error("{}文件MD5加密错误", file.getName());
				logger.error("异常:{}",e);
			}
			
		 
		return r;
	}

	public int[] twoSum(int[] nums, int target) {
		int[] index = new int[2];

		Map<Integer,Integer> hash = new HashMap<>(15);
		for(int i = 0; i < nums.length; i++){
			if(hash.containsKey(nums[i])){
				index[0] = i;
				index[1] = hash.get(nums[i]);
				return index;
			}

			hash.put(target-nums[i],i);
		}
		return index;
	}




	public static void main(String[] args){
		String hamc = Hmac("123qwe,.","IV7+rly0u=");
		String s = Hmac("123qwe","qwe,.");
		System.out.println(s);

	}
}
