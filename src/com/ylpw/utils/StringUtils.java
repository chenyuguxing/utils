package com.ylpw.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public class StringUtils {
    public static boolean isNullString(String s) {
        return s == null || "".equals(s.trim());
    }


    /**
     * 身份证号码转换，15位转18位，18位转15位
     * @param id        身份证号码
     * @return String   转换后的号码
     */
    public static String convertSfzmhm(String id){
        if(StringUtils.isNullString(id)){
            return "";
        }
        if(id.length() == 18){
            return eighteenToFifteen(id);
        }else if(id.length() == 15){
            return fifteenToEighteen(id);
        }else{
            return "";
        }
    }
    /**
     * 15位身份证转为18位身份证
     *
     * @param id 15位身份证
     * @return 18位身份证
     */
    public static String fifteenToEighteen(String id) {

        if (id == null || id.length() != 15) {
            return "";
        }
        String strResult = "";
        int w[] = new int[18];
        int ll_sum = 0;
        int ll_i;
        String ls_check = "";
        StringBuffer SBIDCARD = new StringBuffer(id.substring(0, 6));
        SBIDCARD.append("19");
        SBIDCARD.append(id.substring(6, 15));
        String strIDCARD = new String(SBIDCARD);
        //========================================================//
        w[1] = 7;
        w[2] = 9;
        w[3] = 10;
        w[4] = 5;
        w[5] = 8;
        w[6] = 4;
        w[7] = 2;
        w[8] = 1;
        w[9] = 6;
        w[10] = 3;
        w[11] = 7;
        w[12] = 9;
        w[13] = 10;
        w[14] = 5;
        w[15] = 8;
        w[16] = 4;
        w[17] = 2;
        //=========================================================//
        for (ll_i = 1; ll_i < 18; ll_i++) {
            Integer IntegerID = new Integer(strIDCARD.substring((ll_i - 1), ll_i));
//            int intid = IntegerID;
            ll_sum = ll_sum + IntegerID * w[ll_i];
        }
        ll_sum = ll_sum % 11;
//        Long Longll_sum = new Long(ll_sum);
//        int intll_sum = ll_sum.intValue();
        switch (ll_sum) {
            case 0:
                ls_check = "1";
                break;
            case 1:
                ls_check = "0";
                break;
            case 2:
                ls_check = "X";
                break;
            case 3:
                ls_check = "9";
                break;
            case 4:
                ls_check = "8";
                break;
            case 5:
                ls_check = "7";
                break;
            case 6:
                ls_check = "6";
                break;
            case 7:
                ls_check = "5";
                break;
            case 8:
                ls_check = "4";
                break;
            case 9:
                ls_check = "3";
                break;
            case 10:
                ls_check = "2";
                break;
        }
        SBIDCARD.append(ls_check);
        strResult = new String(SBIDCARD);
        return strResult;
    }

    /**
     * 18位身份证转为15位身份证
     * @param id        18位身份证号
     * @return String   15位身份证号码
     */
    public static String eighteenToFifteen(String id) {

        if (id == null || id.length() != 18) {
            return "";
        }
        String strResult = "";
        StringBuffer SBIDCARD = new StringBuffer(id.substring(0, 6));
        SBIDCARD.append(id.substring(8, 17));
        strResult = new String(SBIDCARD);
        return strResult;
	}
    
    /**
     * 得到UUID
     * @return
     */
    public static String getUUID(){
    	return UUID.randomUUID().toString().replaceAll("-", "");
    }

     /**
     * 检查是否为手机号码
     *
     * @param sth       手机号码
     * @return boolean  成功失败
     */
    public static boolean isMobileNumble(String sth) {
        if(isNullString(sth)){
            return false;
        }
        String regex = "^1[358]\\d{9}$";
        return sth.matches(regex);
    }

    public static boolean isNumber(String str){
        return !isNullString(str) && str.matches("[0-9]+");
    }
    public static boolean isNumberOrLetter(String str){
    	return !isNullString(str) && str.matches("[0-9a-zA-Z]+");
    }

    /**
     * 检查是否为固话
     * @param str   电话号码
     * @return boolean
     */
    public static boolean isTelNumber(String str){
        if(isNullString(str)){
            return false;
        }
        String regex = "^((0\\d{2,3})-)(\\d{7,8})(-(\\d{3,4}))?$";
        return str.matches(regex);
    }

    public static boolean isEmail(String str){
        if(isNullString(str)){
            return false;
        }
        String regex = "^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$";
        return str.matches(regex);
    }
    /**
     * 功能描述：将字符串转化为数组类型。
     */
	public static Object[] parseArray(final String in_value, final Object... param) {
        String value = (in_value == null) ? "" : in_value;
        // -------------------
        List array = parseList(value, param);
        return array.toArray();
    }
    /**
     * 功能描述：将字符串转化为集合类型。在转化过程中可以指定分割符转换类型以及相应类型的默认转换值。
     * 类型的默认转换值是指当原数据在像目标转换时发生异常而采用的默认值取代。
     */
    @SuppressWarnings("unchecked")
	public static List parseList(final String in_value, final Object... param) {
        String value = (in_value == null) ? "" : in_value;
        // -------------------
        String split = ",";// 默认分割符。
        Class<?> toType = String.class;// 默认String类型
        Object defaultValue = null;// 默认值是null。
        List array = null;
        boolean replay = true;// 默认值是true 替换。
        // -------------------
        if (param.length == 0) {
            // 没有参数
            array = new ArrayList<Object>(0);
        } else if (param.length == 1) {
            // 一个参数
            split = (String) param[0];
            array = new ArrayList<Object>(0);
        } else if (param.length == 2) {
            // 两个参数
            split = (String) param[0];
            toType = (Class<?>) param[1];
            array = new ArrayList<Object>(0);
        } else if (param.length == 3) {
            // 三个参数
            split = (String) param[0];
            toType = (Class<?>) param[1];
            defaultValue = param[2];
            array = new ArrayList<Object>(0);
        } else if (param.length == 4) {
            // 四个参数
            split = (String) param[0];
            toType = (Class<?>) param[1];
            defaultValue = param[2];
            array = (List<?>) param[3];
        } else {
            // 五个参数
            split = (String) param[0];
            toType = (Class<?>) param[1];
            defaultValue = param[2];
            array = (List<?>) param[3];
            replay = StringUtil.parseBoolean(param[4].toString(), true);
        }
        // -------------------
        String[] temp_split = value.split(split);
        for (String var : temp_split){
        	if (array.contains(var) == true){
        		if (replay == true) {
                    array.remove(var);
                    array.add(StringUtil.changeType(var, toType, defaultValue));
                } else {}
        	}else{
                array.add(StringUtil.changeType(var, toType, defaultValue));
        	}
        }  
        return array;
    }

	public static boolean isEmpty(Object obj) {
		if (obj == null) {
			return true;
		} else if (obj instanceof String && String.valueOf(obj).trim().equals("")) {
			return true;
		} else if (obj instanceof Number && ((Number) obj).doubleValue() == 0) {
			return true;
		} else if (obj instanceof Boolean && !((Boolean) obj)) {
			return true;
		} else if (obj instanceof Collection && ((Collection) obj).isEmpty()) {
			return true;
		} else if (obj instanceof Map && ((Map) obj).isEmpty()) {
			return true;
		} else if (obj instanceof Object[] && ((Object[]) obj).length == 0) {
			return true;
		}
		return false;
	}
	
	
    public static void main(String[] args) {
//        String s = "110109198005105210";
//        String s2 = eighteenToFifteen(s);
//        System.out.println("eighteenToFifteen(s) = " + s2);
//        System.out.println("fifteenToEighteen(s2) = " + fifteenToEighteen(s2));
//        System.out.println(getUUID());
//
//        String tel = "18701296521";
//        System.out.println("isMobileNumble(tel) = " + isMobileNumble(tel));
        String queryStr = "method=keyLogin&jump=1&current=10000100001&sfzmhm=111111111111111111&drvlevel=1&vehlevel=1&violevel=1&acdlevel=1&hazlevel=1&tralevel=1";
        String[] query = queryStr.split("&");
        System.out.println("query.length = " + query.length);
        for(String s:query){
            System.out.println("s = " + s);
        }
        for(int i=3;i<query.length-6;i++){
            System.out.println("query[i] = " + query[i]);
        }
    }
}
