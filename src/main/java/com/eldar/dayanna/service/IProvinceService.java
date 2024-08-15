package com.eldar.dayanna.service;

import com.eldar.dayanna.model.dto.ProvinceDto;
import com.eldar.dayanna.model.record.ProvinceRecord;
import jakarta.validation.Valid;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IProvinceService {
    @Transactional(readOnly = true)
    List<ProvinceRecord> findByParamsSQL(String name, Integer id, String code);

    Integer createProvince(@Valid ProvinceDto provinceDto);

    @Transactional
    ProvinceRecord updateProvince(Integer id, ProvinceRecord updateDto);

    @Transactional
    void deleteProvince(Integer id);
}
