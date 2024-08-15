package com.eldar.dayanna.controller;

import com.eldar.dayanna.model.dto.LocalityDTOResponse;
import com.eldar.dayanna.model.dto.ProvincesDTOResponse;
import com.eldar.dayanna.model.record.LocalityRecord;
import com.eldar.dayanna.model.record.LocalityRecordExel;
import com.eldar.dayanna.model.record.ProvinceRecord;
import com.eldar.dayanna.service.IExcelLocalityService;
import com.eldar.dayanna.service.IExcelProvinceService;
import com.eldar.dayanna.service.ILocalityService;
import com.eldar.dayanna.service.IProvinceService;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/hybrid")
public class HybridController {

    private final IProvinceService provinceService;
    private final IExcelProvinceService excelProvinceService;
    private final ILocalityService iLocalityService;
    private final IExcelLocalityService excelLocalityService;

    public HybridController(IProvinceService provinceService, IExcelProvinceService excelProvinceService, ILocalityService iLocalityService, IExcelLocalityService excelLocalityService) {
        this.provinceService = provinceService;
        this.excelProvinceService = excelProvinceService;
        this.iLocalityService = iLocalityService;
        this.excelLocalityService = excelLocalityService;
    }

    @GetMapping("/provinces")
    public ResponseEntity<ProvincesDTOResponse> getProvinces(
            @NotNull @RequestParam("filePath") String filePath,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) String code31662) {

        ProvincesDTOResponse response = new ProvincesDTOResponse();

        // Consulta en Excel
        long excelStartTime = System.currentTimeMillis();
        List<ProvinceRecord> excelProvinces = excelProvinceService.findByParamsExcel(filePath, name, id, code31662);
        long excelEndTime = System.currentTimeMillis();
        response.setExcelProvinces(excelProvinces);
        response.setExcelExecutionTime(excelEndTime - excelStartTime);

        // Consulta en Base de Datos
        long dbStartTime = System.currentTimeMillis();
        List<ProvinceRecord> dbProvinces = provinceService.findByParamsSQL(name, id, code31662);
        long dbEndTime = System.currentTimeMillis();

        response.setDbProvinces(dbProvinces);
        response.setDbExecutionTime(dbEndTime - dbStartTime);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/localities")
    public ResponseEntity<LocalityDTOResponse> getCities(
            @NotNull @RequestParam("filePath") String filePath,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) String postalCode) {

        LocalityDTOResponse response = new LocalityDTOResponse();

        // Consulta en Excel
        long excelStartTime = System.currentTimeMillis();
        List<LocalityRecordExel> excelCities = excelLocalityService.findByParamsExcel(filePath, name, id, postalCode);
        long excelEndTime = System.currentTimeMillis();
        response.setExcelCities(excelCities);
        response.setExcelExecutionTime(excelEndTime - excelStartTime);

        // Consulta en Base de Datos
        long dbStartTime = System.currentTimeMillis();
        List<LocalityRecord> dbCities = iLocalityService.findByParamsSQL(name, id, postalCode);
        long dbEndTime = System.currentTimeMillis();
        response.setDbCities(dbCities);
        response.setDbExecutionTime(dbEndTime - dbStartTime);

        return ResponseEntity.ok(response);
    }
}
