package com.imooc.sell.utils;

import com.imooc.sell.enums.CodeEnum;

public class EnumUtil {

    /**
     * 通过code值获取枚举对象
     */
    public static <T extends CodeEnum> T getByCode(Integer code, Class<T> enumClass) {
        for (T each : enumClass.getEnumConstants()) {
            if (code.equals(each.getCode())) {
                return each;
            }
        }
        return null;
    }

}
