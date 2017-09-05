package com.base.library.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 作者：王东一
 * 创建时间：2017/8/31.
 * 电话号码类
 */

public class PhoneUtil {
    public static boolean isMobileNumber(String mobiles) {
        Pattern p = Pattern.compile("^(13[0-9]|15([0-3]|[5-9])|14[5,7,9]|17[1,3,5,6,7,8]|18[0-9])\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }
}
