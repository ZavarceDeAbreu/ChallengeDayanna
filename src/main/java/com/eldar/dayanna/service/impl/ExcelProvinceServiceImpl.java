package com.eldar.dayanna.service.impl;

import com.eldar.dayanna.exception.ResourceNotFoundException;
import com.eldar.dayanna.model.dto.ProvinceDto;
import com.eldar.dayanna.model.record.ProvinceRecord;
import com.eldar.dayanna.service.IExcelProvinceService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Slf4j
@Service
public class ExcelProvinceServiceImpl implements IExcelProvinceService {

    @Override
    public List<ProvinceRecord> findByParamsExcel(String filePath, String name, Integer id, String code31662) {
        List<ProvinceRecord> provinces = new ArrayList<>();

        try (FileInputStream fileInputStream = new FileInputStream(filePath);
             Workbook workbook = WorkbookFactory.create(fileInputStream)) {

            Sheet sheet = workbook.getSheetAt(0);
            int rows = sheet.getLastRowNum();

            for (int i = 1; i <= rows; i++) {
                Row row = sheet.getRow(i);

                if (row != null) {
                    if (id != null && row.getCell(0).getNumericCellValue() == id) {
                        provinces.add(new ProvinceRecord((int) row.getCell(0).getNumericCellValue(), row.getCell(1).getStringCellValue(), row.getCell(2).getStringCellValue()));
                        return provinces;
                    } else if (name != null && row.getCell(1).getStringCellValue().equalsIgnoreCase(name)) {
                        provinces.add(new ProvinceRecord((int) row.getCell(0).getNumericCellValue(), row.getCell(1).getStringCellValue(), row.getCell(2).getStringCellValue()));
                    } else if (code31662 != null && row.getCell(2).getStringCellValue().equals(code31662)) {
                        provinces.add(new ProvinceRecord((int) row.getCell(0).getNumericCellValue(), row.getCell(1).getStringCellValue(), row.getCell(2).getStringCellValue()));
                    }
                }
            }
        } catch (IOException e) {
            log.error("Error al leer el archivo de Excel", e);
        } catch (NullPointerException e) {
            throw new ResourceNotFoundException("province");
        }

        return provinces;
    }

    @Override
    public void createProvince(String filePath, ProvinceDto newProvince) throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(filePath);
             Workbook workbook = WorkbookFactory.create(fileInputStream)) {

            Sheet sheet = workbook.getSheetAt(0);
            int lastRowNum = sheet.getLastRowNum();
            Row newRow = sheet.createRow(lastRowNum + 1);

            newRow.createCell(0).setCellValue(newRow.getRowNum());
            newRow.createCell(1).setCellValue(newProvince.getName());
            newRow.createCell(2).setCellValue(newProvince.getCode31662());

            try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
                workbook.write(outputStream);
                log.info("Provincia creada exitosamente en archivo {}", filePath);
            }
        } catch (IOException e) {
            log.error("Error al crear provincia en archivo {}", filePath, e);
            throw e;
        }
    }

    @Override
    public void updateProvince(Integer id, String filePath, ProvinceDto updatedProvince) {
        try (FileInputStream fileInputStream = new FileInputStream(filePath);
             Workbook workbook = WorkbookFactory.create(fileInputStream)) {

            // Creamos un stream de números enteros que va desde 1 hasta el último número de fila de la hoja
            // Esto nos permitirá iterar sobre cada fila de la hoja
            Sheet sheet = workbook.getSheetAt(0);
            Optional<Row> rowToUpdate = IntStream.range(1, sheet.getLastRowNum() + 1)
                    .mapToObj(sheet::getRow)
                    .filter(row -> row.getCell(0).getNumericCellValue() == id)
                    .findFirst();

            if (rowToUpdate.isPresent()) {
                Row row = rowToUpdate.get();
                row.getCell(1).setCellValue(updatedProvince.getName());
                row.getCell(2).setCellValue(updatedProvince.getCode31662());
            } else {
                log.error("No se encontró la provincia con el ID {}", id);
                throw new ResourceNotFoundException(id.toString());
            }

            try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
                workbook.write(outputStream);
            }
        } catch (IOException e) {
            log.error("Error al actualizar la provincia", e);
            throw new ServiceException("Error al actualizar la provincia", e);
        } catch (NullPointerException e) {
            throw new ResourceNotFoundException(id.toString());
        }
    }

    @Override
    public void deleteProvince(String filePath, Integer id) throws IOException {
        Workbook workbook = WorkbookFactory.create(new FileInputStream(filePath));
        Sheet sheet = workbook.getSheetAt(0);
        try {

            Optional<Row> rowToDelete = IntStream.range(1, sheet.getLastRowNum() + 1)
                    .mapToObj(sheet::getRow)
                    .filter(row -> row.getCell(0).getNumericCellValue() == id)
                    .findFirst();

            if (rowToDelete.isPresent()) {
                rowToDelete.ifPresent(row -> {
                    int rowNum = row.getRowNum();
                    int lastRowNum = sheet.getLastRowNum();
                    sheet.removeRow(row); // Elimino la fila primero
                    if (rowNum < lastRowNum) {
                        sheet.shiftRows(rowNum + 1, lastRowNum, -1); // Desplaza desde la fila siguiente
                    }
                });
            } else {
                log.warn("Provincia no encontrada para eliminar con ID {} en archivo {}", id, filePath);
                throw new ResourceNotFoundException(id.toString());
            }

            try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
                workbook.write(outputStream);
            } catch (IOException e) {
                log.error("Error al eliminar provincia en archivo {}", filePath, e);
                throw e;
            }
        } catch (NullPointerException e) {
            throw new ResourceNotFoundException(id.toString());
        }

    }


}
