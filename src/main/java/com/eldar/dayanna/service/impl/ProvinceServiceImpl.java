package com.eldar.dayanna.service.impl;

import com.eldar.dayanna.exception.ResourceNotFoundException;
import com.eldar.dayanna.model.Province;
import com.eldar.dayanna.model.dto.ProvinceDto;
import com.eldar.dayanna.model.record.ProvinceRecord;
import com.eldar.dayanna.repository.ProvinceRepo;
import com.eldar.dayanna.service.IProvinceService;
import com.eldar.dayanna.utils.ProvinceUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.eldar.dayanna.utils.ProvinceUtils.provinceToRecord;

/**
 *
 */
@Slf4j
@Service
public class ProvinceServiceImpl implements IProvinceService {

    private final ProvinceRepo provinceRepo;

    public ProvinceServiceImpl(ProvinceRepo provinceRepo) {
        this.provinceRepo = provinceRepo;
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProvinceRecord> findByParamsSQL(String name, Integer id, String code) {
        List<Province> provinces = new ArrayList<>();

        if (name != null) {
            provinces = provinceRepo.findByName(name);
        } else if (id != null) {
            Optional<Province> province = provinceRepo.findById(id);
            if (province.isPresent()) {
                provinces.add(province.get());
            }
        } else if (code != null) {
            provinces = provinceRepo.findByCode31662(code);
        } else {
            return Collections.emptyList();
        }

        if (provinces.isEmpty()) {
            throw new ResourceNotFoundException(name + id + code);
        }
        return provinces.stream()
                .map(ProvinceUtils::provinceToRecord)
                .collect(Collectors.toList());
    }

    @Override
    public Integer createProvince(ProvinceDto provinceDto) {
        try {
            Province province = new Province();
            province.setName(provinceDto.getName());
            province.setCode31662(provinceDto.getCode31662());

            return provinceRepo.save(province).getId();
        } catch (Exception e) {
            throw new RuntimeException("Error al crear la provincia", e);
        }
    }

    @Transactional
    @Override
    public ProvinceRecord updateProvince(Integer id, ProvinceRecord updateDto) {
        try {
            Province province = provinceRepo.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(id.toString()));
            province.setName(updateDto.name());
            province.setCode31662(updateDto.code());
            provinceRepo.save(province);
            return provinceToRecord(province);
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar la provincia", e);
        }
    }

    @Transactional
    @Override
    public void deleteProvince(Integer id) {
        Province province = provinceRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id.toString()));
        provinceRepo.delete(province);
    }

}

