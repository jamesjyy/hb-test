package cn.best.approveservice.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串操作工具类
 * Created by hai on 2018/1/31.
 */
public final class StringUtil {
    /**
     * 去空 如果是NULL则返回空字符
     * @param str
     * @return
     */
    public static String delNull(String str) {
        if(CommonUtil.isNull(str)){
            str = "";
        }
        return str;
    }

    /**
     * 判断字符串是否非null && 非空字符
     *
     * @param param
     * @return
     */
    public static boolean isNotEmpty(String param) {
        return param != null && !"".equals(param.trim());
    }

    /**
     * 判断字符串是否为null || 空字符串
     *
     * @param param
     * @return
     */
    public static boolean isEmpty(String param) {
        return param == null || "".equals(param.trim());
    }
    //生成指定长度数字
    public static String getNumByLength(int length) {
        Random random = new Random();
        String result = "";
        for (int i = 0; i < length; i++) {
            result += random.nextInt(10);
        }
        return result;
    }

    /**
     * 判断是否为数字类�?
     *
     * @param str
     * @return True为数字类�?
     */
    public static boolean isNum(String str) {
        String regex = "^(-?\\d+)(\\.\\d+)?$";
        return matchRegex(str, regex);
    }

    /**
     * 生成uuid
     *
     * @return
     */
    public static String getSquence() {
        String rtVal = null;
        rtVal = UUID.randomUUID().toString();
        rtVal = rtVal.replaceAll("-", "");
        return rtVal;
    }

    private static boolean matchRegex(String value, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }
    public static boolean isBlank(String str) {
        return StringUtils.isBlank(str);
    }

    /*密码*/
    public static Integer checkPassword(String passwordStr) {
        String regexZ = "\\d*";
        String regexS = "[a-zA-Z]+";
        String regexT = "\\W+$";
        String regexZT = "\\D*";
        String regexST = "[\\d\\W]*";
        String regexZS = "\\w*";
        String regexZST = "[\\w\\W]*";

        if (passwordStr.matches(regexZ)) {
            return 1;
        }
        if (passwordStr.matches(regexS)) {
            return 1;
        }
        if (passwordStr.matches(regexT)) {
            return 1;
        }
        if (passwordStr.matches(regexZT)) {
            return 2;
        }
        if (passwordStr.matches(regexST)) {
            return 2;
        }
        if (passwordStr.matches(regexZS)) {
            return 2;
        }
        if (passwordStr.matches(regexZST)) {
            return 3;
        }
        return 2;
    }

    /**
     * 从str字符串中提取中文字符，并保留英文字符
     * @param str
     * @return
     */
    public static String getChineseStr(String str) {
        String rs = "";
        if (!CommonUtil.isNull(str)) {
            rs = str.replaceAll("[^0-9a-zA-Z\\u4e00-\\u9fa5]", "");
        }
        return rs;
    }


    /**
     * 清除空白字符
     * @param str
     * @return
     */
    public static String trim(String str) {
        return StringUtils.trim(str);
    }

    /**
     * 字符串str不足num位数的，在前面补齐"0"
     * @param str 要处理的字符串
     * @param num 处理后的长度
     * @return
     */
    public static String addZero(int s, long num) {
        return StringUtil.addZero(String.valueOf(s), num);
    }
    public static String addZero(String str, long num) {
        StringBuffer ss = new StringBuffer();
        while (ss.length() != num - str.length()) {
            ss.append("0");
        }
        ss.append(str);
        return ss.toString();
    }

    public static void main(String[] args) {
        System.out.println(MD5Util.getMD5Str("bianchenghui"+"656jkea132"));
    }
}
