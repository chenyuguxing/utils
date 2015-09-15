package com.ylpw.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.apache.log4j.Logger;

/**
 * @Author ZJJ
 * @Date Nov 19, 2011
 * @Time 11:04:45 AM 类说明
 */
public class ChineseSpelling {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ChineseSpelling.class);
	 /**   
     * 汉字转换位汉语拼音首字母，英文字符不变   
     * @param chinese 汉字   
     * @return 拼音   
     */      
    public static String converterToFirstSpell(String chinese){              
         String pinyinName = "";       
        char[] nameChar = chinese.toCharArray();       
         HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();       
         defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);       
         defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);       
        for (int i = 0; i < nameChar.length; i++) {       
            if (nameChar[i] > 128) {       
                try {       
                     pinyinName += PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaultFormat)[0].charAt(0);       
                 } catch (BadHanyuPinyinOutputFormatCombination e) {
                	 logger.error(e.getMessage(), e);
                 }       
             }else{       
                 pinyinName += nameChar[i];       
             }       
         }       
        return pinyinName;       
     }       
        
    /**   
     * 汉字转换位汉语拼音，英文字符不变   
     * @param chinese 汉字   
     * @return 拼音   
     */      
    public static String converterToSpell(String chinese){               
         String pinyinName = "";       
        char[] nameChar = chinese.toCharArray();       
         HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();       
         defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);       
         defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);       
        for (int i = 0; i < nameChar.length; i++) {       
            if (nameChar[i] > 128) {       
                try {       
                     pinyinName += PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaultFormat)[0];       
                 } catch (BadHanyuPinyinOutputFormatCombination e) {
                	 logger.error(e.getMessage(), e);
                 }       
             }else{       
                 pinyinName += nameChar[i];       
             }       
         }       
        return pinyinName;       
     }   
    
    
    public static void main(String[] args) {      
    }     

}
