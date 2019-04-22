package com.hyk.code.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 *emoji工具过滤类
 */
public class SLEmojiFilter {
  

    /**
     * 微信过滤表情
     * @author hsw
     *
     */

        public static String emjoFileter(String source) {
            if (source == null) {
                return source;
            }
            Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                    Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
            Matcher emojiMatcher = emoji.matcher(source);
            if (emojiMatcher.find()) {
                source = emojiMatcher.replaceAll("*");
                return source;
            }
            return source;
        }
    
    public static void main(String[] args ) {
    	
    	String ss="黑椒🐶".replaceAll("[\ue000-\uefff]", "");
    	
    	System.out.println("=============="+emjoFileter("黑椒🐶"));
    }
    
    
}