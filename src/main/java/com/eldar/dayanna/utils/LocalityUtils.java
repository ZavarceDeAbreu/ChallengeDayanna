package com.eldar.dayanna.utils;

import com.eldar.dayanna.model.Locality;
import com.eldar.dayanna.model.record.LocalityRecord;
import com.eldar.dayanna.model.record.ProvinceRecord;

public class LocalityUtils {
    public static LocalityRecord localityToRecord(Locality locality) {
        return new LocalityRecord(locality.getId(), locality.getName(), locality.getPostalCode(), new ProvinceRecord(locality.getProvince().getId(), locality.getProvince().getName(), locality.getProvince().getCode31662()));
    }
}
