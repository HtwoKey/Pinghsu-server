package com.htwokey.blog.utils;


public class DigitsUtil {
	
	//提取字符串中数字
	public static String get_StringNum(String str) {
		str=str.trim();//字符串开始和末尾的空格
		String str2="";
		if(str != null && !"".equals(str)){//判断字符串是否为null和是否为空字符
			for(int i=0;i<str.length();i++){
				//ascll码48到57是数字0~9
				//charAt(i)这个位置上的字符是否是数字的
				if(str.charAt(i)>=48 && str.charAt(i)<=57){
					str2+=str.charAt(i);
				}
			}
		}
		return str2;
	}
	
	
//	public static void main(String[] args) throws IOException {
//		String str = FileUtils.readFileToString(new File(""));
//		JSONObject json = JSON.parseObject(str);
//	}
}

