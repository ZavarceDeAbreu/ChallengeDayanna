package com.eldar.dayanna.service.impl;

import com.eldar.dayanna.model.Locality;
import com.eldar.dayanna.model.Province;
import com.eldar.dayanna.repository.LocalityRepo;
import com.eldar.dayanna.repository.ProvinceRepo;
import com.eldar.dayanna.service.IExcelExportService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class ExcelExportService implements IExcelExportService {

    private final ProvinceRepo provinceRepository;

    private final LocalityRepo localityRepository;

    public ExcelExportService(ProvinceRepo provinceRepository, LocalityRepo localityRepository) {
        this.provinceRepository = provinceRepository;
        this.localityRepository = localityRepository;
    }

    @Override
    public void exportProvincesAndLocalitiesToExcel(String filePath) throws IOException {
        List<Province> provinces = provinceRepository.findAll();
        List<Locality> localities = localityRepository.findAll();

        try (FileOutputStream fileOut = new FileOutputStream(filePath);
             ZipOutputStream zipOut = new ZipOutputStream(fileOut)) {

            addProvincesToZip(provinces, zipOut);
            addLocalitiesToZip(localities, zipOut);
        }
    }

    private void addProvincesToZip(List<Province> provinces, ZipOutputStream zipOut) throws IOException {
        Workbook workbook = createProvincesWorkbook(provinces);
        zipOut.putNextEntry(new ZipEntry("provinces.xlsx"));
        workbook.write(zipOut);
        workbook.close();
    }

    private void addLocalitiesToZip(List<Locality> localities, ZipOutputStream zipOut) throws IOException {
        Workbook workbook = createLocalitiesWorkbook(localities);
        zipOut.putNextEntry(new ZipEntry("localities.xlsx"));
        workbook.write(zipOut);
        workbook.close();
    }

    private Workbook createProvincesWorkbook(List<Province> provinces) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Provinces");

        createHeaderRow(sheet, "ID", "Name", "Code 3166-2");

        int rowNum = 1;
        for (Province province : provinces) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(province.getId());
            row.createCell(1).setCellValue(province.getName());
            row.createCell(2).setCellValue(province.getCode31662());
        }

        return workbook;
    }

    private Workbook createLocalitiesWorkbook(List<Locality> localities) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Localities");

        createHeaderRow(sheet, "ID", "Name", "PostalCode", "Province");

        int rowNum = 1;
        for (Locality locality : localities) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(locality.getId());
            row.createCell(1).setCellValue(locality.getName());
            row.createCell(2).setCellValue(locality.getPostalCode());
            row.createCell(3).setCellValue(locality.getProvince().getName());
        }

        return workbook;
    }

    private void createHeaderRow(Sheet sheet, String... headers) {
        Row header = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            header.createCell(i).setCellValue(headers[i]);
        }
    }
}
