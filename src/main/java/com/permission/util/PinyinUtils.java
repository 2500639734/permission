package com.permission.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * @auther: shenke
 * @date: 2020/2/23 10:07
 * @description: 中文拼音转换工具类
 */
public class PinyinUtils {

    /**
     * 获得汉语拼音首字母
     * @param chines 汉字
     * @param hanyuPinyinCaseType 大写或小写
     * @return
     */
    public static String getAlpha(String chines, HanyuPinyinCaseType hanyuPinyinCaseType) {
        try {
            String pinyinName = "";
            char[] nameChar = chines.toCharArray();
            HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
            defaultFormat.setCaseType(hanyuPinyinCaseType);
            defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
            for (int i = 0; i < nameChar.length; i++) {
                if (nameChar[i] > 128) {
                    pinyinName += PinyinHelper.toHanyuPinyinStringArray(
                            nameChar[i], defaultFormat)[0].charAt(0);
                } else {
                    pinyinName += nameChar[i];
                }
            }
            return pinyinName;
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将字符串中的中文转化为拼音,英文字符不变（小写）
     * @param inputString 汉字
     * @param hanyuPinyinCaseType 大写或小写
     * @return
     */
    public static String getPingYin(String inputString, HanyuPinyinCaseType hanyuPinyinCaseType) {
        try {
            HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
            format.setCaseType(hanyuPinyinCaseType);
            format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
            format.setVCharType(HanyuPinyinVCharType.WITH_V);
            String output = "";
            if (inputString != null && inputString.length() > 0
                    && !"null".equals(inputString)) {
                char[] input = inputString.trim().toCharArray();
                for (int i = 0; i < input.length; i++) {
                    if (java.lang.Character.toString(input[i]).matches(
                            "[\\u4E00-\\u9FA5]+")) {
                        String[] temp = PinyinHelper.toHanyuPinyinStringArray(
                                input[i], format);
                        output += temp[0];
                    } else
                        output += java.lang.Character.toString(input[i]);
                }
            } else {
                return "*";
            }
            return output;
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            throw new RuntimeException(e);
        }
    }

}
