package com.eldar.dayanna.model.dto;

import com.eldar.dayanna.model.record.ProvinceRecord;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProvincesDTOResponse {
    private List<ProvinceRecord> excelProvinces;
    private List<ProvinceRecord> dbProvinces;
    private long excelExecutionTime;
    private long dbExecutionTime;
}
