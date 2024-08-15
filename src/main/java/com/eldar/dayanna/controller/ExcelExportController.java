package com.eldar.dayanna.controller;

import com.eldar.dayanna.model.dto.LocalityDto;
import com.eldar.dayanna.model.dto.ProvinceDto;
import com.eldar.dayanna.model.record.LocalityRecordExel;
import com.eldar.dayanna.model.record.ProvinceRecord;
import com.eldar.dayanna.service.IExcelExportService;
import com.eldar.dayanna.service.IExcelLocalityService;
import com.eldar.dayanna.service.IExcelProvinceService;
import jakarta.validation.constraints.NotNull;
import org.apache.coyote.BadRequestException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/excel")
public class ExcelExportController {

    private final IExcelExportService excelExportService;
    private final IExcelProvinceService excelProvinceService;
    private final IExcelLocalityService excelLocalityService;

    public ExcelExportController(IExcelExportService excelExportService, IExcelProvinceService excelProvinceService, IExcelLocalityService excelLocalityService) {
        this.excelExportService = excelExportService;
        this.excelProvinceService = excelProvinceService;
        this.excelLocalityService = excelLocalityService;
    }

    @GetMapping("/download")
    public ResponseEntity<InputStreamResource> downloadProvincesAndLocalitiesZip() throws IOException {
        String zipFilePath = "provincesAndLocalities.zip";

        excelExportService.exportProvincesAndLocalitiesToExcel(zipFilePath);

        FileInputStream fileInputStream = new FileInputStream(zipFilePath);
        InputStreamResource resource = new InputStreamResource(fileInputStream);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=provinces.zip")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @GetMapping("/provinces")
    @ResponseBody
    public ResponseEntity<List<ProvinceRecord>> getProvinces(
            @NotNull @RequestParam("filePath") String filePath,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) String code31662) {
        List<ProvinceRecord> excelProvinces = excelProvinceService.findByParamsExcel(filePath, name, id, code31662);

        return ResponseEntity.ok()
                .body(excelProvinces);
    }

    @PostMapping("/province/create")
    @ResponseBody
    public ResponseEntity<Void> createProvince(@RequestBody ProvinceDto newProvince) throws IOException {
        if (newProvince.getFilePath() == null) {
            throw new BadRequestException("File path es requerido");
        }
        excelProvinceService.createProvince(newProvince.getFilePath(), newProvince);
        return ResponseEntity.status(201).build();
    }

    @PatchMapping("/province/{id}")
    @ResponseBody
    public ResponseEntity<Void> updateProvince(@NotNull @PathVariable Integer id, @RequestBody ProvinceDto updatedProvince) throws IOException {
        if (updatedProvince.getFilePath() == null) {
            throw new BadRequestException("File path es requerido");
        }
        excelProvinceService.updateProvince(id, updatedProvince.getFilePath(), updatedProvince);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/province/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteProvince(@NotNull @RequestParam("filePath") String filePath, @PathVariable Integer id) throws IOException {
        excelProvinceService.deleteProvince(filePath, id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/locality")
    @ResponseBody
    public ResponseEntity<List<LocalityRecordExel>> getLocalityByID(
            @NotNull @RequestParam("filePath") String filePath,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) String postalCode) {
        List<LocalityRecordExel> locality = excelLocalityService.findByParamsExcel(filePath, name, id, postalCode);
        return ResponseEntity.ok(locality);
    }

    @PostMapping("/locality/create")
    @ResponseBody
    public ResponseEntity<Void> createLocality(@RequestBody LocalityDto newLocality) throws IOException {
        if (newLocality.getFilePath() == null) {
            throw new BadRequestException("File path es requerido");
        }
        excelLocalityService.createLocality(newLocality.getFilePath(), newLocality);
        return ResponseEntity.status(201).build();
    }

    @PatchMapping("/locality/{id}")
    @ResponseBody
    public ResponseEntity<Void> updateLocality(@NotNull @PathVariable Integer id, @RequestBody LocalityDto updatedLocality) throws IOException {
        if (updatedLocality.getFilePath() == null) {
            throw new BadRequestException("File path es requerido");
        }
        excelLocalityService.updateLocality(id, updatedLocality.getFilePath(), updatedLocality);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/locality/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteLocality(@NotNull @RequestParam("filePath") String filePath, @PathVariable Integer id) throws IOException {
        excelLocalityService.deleteLocality(filePath, id);
        return ResponseEntity.noContent().build();
    }
}