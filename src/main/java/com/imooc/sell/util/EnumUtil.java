package com.imooc.sell.util;

//according code return enum

import com.imooc.sell.enums.CodeEnum;

public class EnumUtil {
    public static <T extends CodeEnum> T getEnumByCode(Integer code, Class<T> enumClass){
        //use reflect
        for (T each : enumClass.getEnumConstants()) {
            if (code.equals(each.getCode()))
                return each;
        }
        return null;
    }
}
