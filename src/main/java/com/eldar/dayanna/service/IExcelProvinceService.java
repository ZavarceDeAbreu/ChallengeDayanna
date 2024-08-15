package com.eldar.dayanna.service;


import com.eldar.dayanna.model.dto.ProvinceDto;
import com.eldar.dayanna.model.record.ProvinceRecord;
import jakarta.validation.constraints.NotNull;

import java.io.IOException;
import java.util.List;

public interface IExcelProvinceService {

    void createProvince(String filePath, ProvinceDto newProvince) throws IOException;

    void updateProvince(@NotNull Integer id, String filePath, ProvinceDto updatedProvince) throws IOException;

    void deleteProvince(@NotNull String filePath, Integer id) throws IOException;

    List<ProvinceRecord> findByParamsExcel(String filePath, String name, Integer id, String code31662);
}
