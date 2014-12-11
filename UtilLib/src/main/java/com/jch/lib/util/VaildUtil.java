package com.jch.lib.util;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author
 * @createdate 2014-6-24 上午11:02:38
 * @Description: 校验工具类
 */
public class VaildUtil {

    /**
     * @return
     * @Description: 校验手机号码
     */
    public static String validPhone(String phone) {
        String message = "";
        if (TextUtil.stringIsNull(phone.replace(" ", ""))) {
            message = "请输入手机号码！";
        } else if (phone.length() < 11) {
            message = "请输入11位手机号码！";
        } else {
            String phoneRule = "^((14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$";
            Pattern p = Pattern.compile(phoneRule);
            Matcher match = p.matcher(phone);
            if (!match.matches()) {
                message = "请输入正确的手机号码！";
            }
        }
        return message;
    }

    /**
     * @param qq
     * @return
     * @Description: (用一句话描述该方法做什么) 验证qq号
     */
    public static String validQQ(String qq) {
        String message = "";
        if (TextUtil.stringIsNull(qq.replace(" ", ""))) {
            message = "";
        } else {
            /*
             * if("14527060199".equals(phone)){ //测试所用 return message; }
			 */
            // String phoneRule = "^[1]+[3,5,8]+[0-9]{9}";
            // String phoneRule =
            // "^((14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
            String phoneRule = "^\\d{3,15}$";
            Pattern p = Pattern.compile(phoneRule);
            Matcher match = p.matcher(qq);
            if (!match.matches()) {
                message = "请输入正确的QQ号码！";
            }
        }
        return message;
    }

    /**
     * @return
     * @Description: 校验密码
     */
    public static String validPassword(String pwd) {
        String message = "";
        if (pwd.contains(" ")) {
            message = "密码中不能含有空格！";
            return message;
        }
        if (TextUtil.stringIsNull(pwd)) {
            message = "请输入密码！";
        } else if (pwd.length() < 6) {
            message = "请输入6-10位密码！";
        } else if (pwd.length() > 10) {
            message = "请输入6-10位密码！";
        }
        return message;
    }

    /**
     * @param checkCode
     * @return
     * @Description: 校验验证码
     */
    public static String validCheckCode(String checkCode) {
        String message = "";
        if (TextUtil.stringIsNull(checkCode)) {
            message = "请输入验证码！";
        } else if (checkCode.length() < 4) {
            message = "验证码请输入4位有效数字！";
        }
        return message;
    }

    /**
     * @param email
     * @return
     * @Description: 校验邮箱
     */
    public static String validEmail(String email) {
        String message = "";
        String emailRule = "^\\w+@\\w+\\.\\w+$";
        Matcher match = Pattern.compile(emailRule).matcher(email);
        if (!match.matches()) {
            message = "请输入正确的邮箱！";
        }
        return message;
    }

    /**
     * @param name
     * @return
     * @Description: 校验姓名 中文，英文，中英文，中文少两个字符，纯英文 至少三个字符
     */
    public static String validName(String name) {
        String message = "";
        if (name.contains(" ")) {
            message = "联系人中不能含有空格！";
            return message;
        }
        String englishNameRule = "^[A-Za-z]{3,}|[\u4e00-\u9fa5]{1,}[A-Za-z]+$";
        String chineseNameRule = "^[\u4e00-\u9fa5]{2,}$";
        Matcher mat = Pattern.compile(englishNameRule).matcher(name.replace(" ", ""));
        Matcher match = Pattern.compile(chineseNameRule).matcher(name.replace(" ", ""));
        if (TextUtil.stringIsNotNull(name)) {
            if (!mat.matches() && !match.matches()) {
                message = "请输入真实的联系人姓名！";
            } else if (match.matches() && name.length() > 30) {
                message = "联系人姓名最长30个汉字！";
            } else if (mat.matches() && name.length() > 60) {
                message = "联系人姓名最长60个字符！";
            }
        } else {
            message = "请填写联系人姓名！";
        }
        return message;
    }

    /**
     * 判断号码是联通，移动，电信中的哪个, 在使用本方法前，请先验证号码的合法性 规则：
     * <p/>
     * 中国移动拥有号码段为:139,138,137,136,135,134,147,159,158,157(3G),151,152,150,182(3G
     * ),188(3G),187(3G);16个号段
     * 中国联通拥有号码段为:130,131,132,145,155,156(3G),186(3G),185(3G);8个号段
     * 中国电信拥有号码段为:133,1349,153,189(3G),180(3G);5个号码段
     *
     * @return 返回相应类型：1代表联通；2代表移动；3代表电信
     */
    public static String getMobileType(String mobile) {

        if (mobile.startsWith("0") || mobile.startsWith("+860")) {
            mobile = mobile.substring(mobile.indexOf("0") + 1, mobile.length());
        }
        List chinaUnicom = Arrays.asList(new String[]{"130", "131", "132", "145", "155", "156", "186", "185"});
        List chinaMobile1 = Arrays.asList(new String[]{"135", "136", "137", "138", "139", "147", "150", "151", "152", "157", "158",
                "159", "182", "187", "188"});
        List chinaMobile2 = Arrays.asList(new String[]{"1340", "1341", "1342", "1343", "1344", "1345", "1346", "1347", "1348"});

        boolean bolChinaUnicom = (chinaUnicom.contains(mobile.substring(0, 3)));
        boolean bolChinaMobile1 = (chinaMobile1.contains(mobile.substring(0, 3)));
        boolean bolChinaMobile2 = (chinaMobile2.contains(mobile.substring(0, 4)));
        if (bolChinaUnicom)
            return "1";// 联通
        if (bolChinaMobile1 || bolChinaMobile2)
            return "2"; // 移动
        return "3"; // 其他为电信

    }
}
