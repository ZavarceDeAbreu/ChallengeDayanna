package com.eldar.dayanna.utils;

import com.eldar.dayanna.model.Province;
import com.eldar.dayanna.model.record.ProvinceRecord;

public class ProvinceUtils {
    public static ProvinceRecord provinceToRecord(Province province) {
        return new ProvinceRecord(province.getId(), province.getName(), province.getCode31662());
    }
}