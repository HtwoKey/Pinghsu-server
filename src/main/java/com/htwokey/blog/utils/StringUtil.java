package com.htwokey.blog.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StringUtil extends org.apache.commons.lang3.StringUtils  {


	/**
	 *替换空格 ，半角 、全角
	 */
	 public static String ReplaceBlank(String str) {
		    if (str==null){return str;}
		    Pattern p = Pattern.compile("\\s*|\t|\r|\n");
		    Matcher m = p.matcher(str);
		    str = m.replaceAll("");
		    str= remove(str, "　");
	        str=remove(str, " ");	
	        return str;
	 }
	
	 
	 public  static String  RemoveEmpty(String str) {
		    if (str==null) {
				return null;
			}
		    str=str.replaceAll("\\u00A0", "");
		    str=str.replaceAll("\\n", "");
		    str=str.replaceAll("\\r", "");		    
		    str=removeStart(str, " ");
		    str=removeEnd(str, " ");
		    return str;
	 }
	 
	 public  static String  cleanKey(String str) {
		    if (str==null) {
				return null;
			}
		    str=RemoveEmpty(str);
		    str=str.replaceAll("：", "").replaceAll(":", "").replaceAll("【", "").replaceAll("】", "").replaceAll(" ", "").replaceAll("　", "");
		    return RemoveEmpty(str);
	 }

	/**
	 * 将emoji表情替换为空串
	 * @return 过滤后的字符串
	 */
	 public static String filterEmoji(String str){
	 	if (str != null && str.length() > 0){
	 		return str.replaceAll("[\ud800\udc00-\udbff\udfff\ud800-\udfff]", "");
		}
	 	return str;
	 }
	
	 
}

