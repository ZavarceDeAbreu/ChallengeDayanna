package com.eldar.dayanna.service;

import com.eldar.dayanna.model.dto.LocalityDto;
import com.eldar.dayanna.model.record.LocalityRecordExel;
import jakarta.validation.constraints.NotNull;

import java.io.IOException;
import java.util.List;

public interface IExcelLocalityService {

    List<LocalityRecordExel> findByParamsExcel(String filePath, String name, Integer id, String postalCode);

    void updateLocality(@NotNull Integer id, String filePath, LocalityDto updatedLocality);

    void deleteLocality(@NotNull String filePath, Integer id) throws IOException;

    void createLocality(String filePath, LocalityDto newLocality) throws IOException;
}
