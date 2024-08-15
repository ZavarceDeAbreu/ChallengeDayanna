package com.eldar.dayanna.service;

import com.eldar.dayanna.model.dto.LocalityDto;
import com.eldar.dayanna.model.record.LocalityRecord;
import jakarta.validation.Valid;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ILocalityService {

    List<LocalityRecord> findByParamsSQL(String name, Integer id, String code);

    Integer createLocality(@Valid LocalityDto localityDto);

    @Transactional
    LocalityRecord updateLocality(Integer id, LocalityDto updateDto);

    @Transactional
    void deleteLocality(Integer id);
}
