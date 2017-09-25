package com.base.library.manufacturerUtil;

import android.os.Build;
import android.util.Log;

import static com.base.library.manufacturerUtil.Manufacturer.other;

/**
 * 作者：王东一
 * 创建时间：2017/9/12.
 * 机型处理
 */

public class ManufacturerUtil {
    private static final String MANUFACTURER_HUAWEI = "HUAWEI";
    private static final String MANUFACTURER_XIAOMI = "XIAOMI";
    private static final String MANUFACTURER_OPPO = "OPPO";
    private static final String MANUFACTURER_VIVO = "vivo";
    private static final String MANUFACTURER_MEIZU = "meizu";
    private static final String manufacturer = Build.MANUFACTURER;

    public static Manufacturer getManufactureUtil() {
        Manufacturer Rmanufacturer = other;
        try {
            switch (manufacturer) {
                case MANUFACTURER_HUAWEI:
                    Rmanufacturer = Manufacturer.HUAWEI;
                    break;
                case MANUFACTURER_OPPO:
                    Rmanufacturer = Manufacturer.OPPO;
                    break;
                case MANUFACTURER_VIVO:
                    Rmanufacturer = Manufacturer.vivo;
                    break;
                case MANUFACTURER_XIAOMI:
                    Rmanufacturer = Manufacturer.XIAOMI;
                    break;
                case MANUFACTURER_MEIZU:
                    Rmanufacturer = Manufacturer.meizu;
                    break;
            }
            return Rmanufacturer;
        } catch (Exception e) {
            Log.e("Permissions4M", "手机品牌为：" + manufacturer + "异常抛出，：" + e.getMessage());
            return Rmanufacturer;
        }
    }
}
