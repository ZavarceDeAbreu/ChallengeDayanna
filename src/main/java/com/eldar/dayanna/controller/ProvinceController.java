package com.eldar.dayanna.controller;

import com.eldar.dayanna.model.dto.ProvinceDto;
import com.eldar.dayanna.model.record.ProvinceRecord;
import com.eldar.dayanna.service.IProvinceService;
import com.eldar.dayanna.service.impl.ProvinceServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/provinces")
public class ProvinceController {

    private final IProvinceService provinceService;

    public ProvinceController(ProvinceServiceImpl provinceService) {
        this.provinceService = provinceService;
    }

    @GetMapping("/get")
    @ResponseBody
    public ResponseEntity<List<ProvinceRecord>> getProvinces(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) String code) {

        List<ProvinceRecord> provinces = provinceService.findByParamsSQL(name, id, code);

        return ResponseEntity.ok(provinces);
    }

    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity<Void> createProvince(@Valid @RequestBody ProvinceDto provinceDto) {
        Integer provinceId = provinceService.createProvince(provinceDto);

        if (provinceId == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        URI location = URI.create("/provinces/" + provinceId);
        return ResponseEntity.created(location).build();
    }

    @PatchMapping("/{id}")
    @ResponseBody
    public ResponseEntity<ProvinceRecord> updateProvince(@PathVariable Integer id, @RequestBody ProvinceRecord updateDto) {
        ProvinceRecord province = provinceService.updateProvince(id, updateDto);
        if (province != null) {
            return ResponseEntity.ok(province);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteProvince(@PathVariable Integer id) {
        provinceService.deleteProvince(id);
        return ResponseEntity.noContent().build();
    }
}

