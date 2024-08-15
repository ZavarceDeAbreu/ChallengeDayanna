package com.eldar.dayanna.model.dto;

import com.eldar.dayanna.model.record.LocalityRecord;
import com.eldar.dayanna.model.record.LocalityRecordExel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LocalityDTOResponse {
    private List<LocalityRecordExel> excelCities;
    private List<LocalityRecord> dbCities;
    private long excelExecutionTime;
    private long dbExecutionTime;
}
