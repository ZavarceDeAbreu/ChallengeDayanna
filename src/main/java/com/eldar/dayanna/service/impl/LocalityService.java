package com.eldar.dayanna.service.impl;

import com.eldar.dayanna.exception.ResourceNotFoundException;
import com.eldar.dayanna.model.Locality;
import com.eldar.dayanna.model.Province;
import com.eldar.dayanna.model.dto.LocalityDto;
import com.eldar.dayanna.model.record.LocalityRecord;
import com.eldar.dayanna.repository.LocalityRepo;
import com.eldar.dayanna.service.ILocalityService;
import com.eldar.dayanna.utils.LocalityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.eldar.dayanna.utils.LocalityUtils.localityToRecord;

@Service
public class LocalityService implements ILocalityService {

    private final LocalityRepo localityRepo;

    @Autowired
    public LocalityService(LocalityRepo localityRepo) {
        this.localityRepo = localityRepo;
    }

    @Override
    public List<LocalityRecord> findByParamsSQL(String name, Integer id, String code) {
        List<Locality> Localities = new ArrayList<>();

        if (name != null) {
            Localities = localityRepo.findByNameLike(name);
        } else if (id != null) {
            Optional<Locality> locality = localityRepo.findById(id);
            if (locality.isPresent()) {
                Localities.add(locality.get());
            }
        } else if (code != null) {
            Localities = localityRepo.findByPostalCode(Short.parseShort(code));
        } else {
            return Collections.emptyList();
        }

        if (Localities.isEmpty()) {
            throw new ResourceNotFoundException(name + id + code);
        }
        return Localities.stream()
                .map(LocalityUtils::localityToRecord)
                .collect(Collectors.toList());
    }

    @Override
    public Integer createLocality(LocalityDto localityDto) {
        try {
            Locality locality = new Locality();
            locality.setName(localityDto.getName());
            locality.setPostalCode(localityDto.getPostalCode());
            locality.setProvince(new Province(localityDto.getProvinceId()));

            return localityRepo.save(locality).getId();
        } catch (Exception e) {
            throw new RuntimeException("Error al crear la localidad", e);
        }
    }

    @Transactional
    @Override
    public LocalityRecord updateLocality(Integer id, LocalityDto updateDto) {
        try {
            Locality locality = localityRepo.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(id.toString()));
            locality.setName(updateDto.getName());
            locality.setPostalCode(updateDto.getPostalCode());
            locality.setProvince(new Province(updateDto.getProvinceId()));
            localityRepo.save(locality);
            return localityToRecord(locality);
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar la localidad", e);
        }
    }

    @Transactional
    @Override
    public void deleteLocality(Integer id) {
        Locality locality = localityRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id.toString()));
        localityRepo.delete(locality);
    }


}
