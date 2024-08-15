package com.eldar.dayanna.service.impl;

import com.eldar.dayanna.exception.ResourceNotFoundException;
import com.eldar.dayanna.exception.ServiceException;
import com.eldar.dayanna.model.dto.LocalityDto;
import com.eldar.dayanna.model.record.LocalityRecordExel;
import com.eldar.dayanna.service.IExcelLocalityService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Slf4j
@Service
public class ExcelLocalityServiceImpl implements IExcelLocalityService {

    @Override
    public List<LocalityRecordExel> findByParamsExcel(String filePath, String name, Integer id, String postalCode) {
        List<LocalityRecordExel> localities = new ArrayList<>();

        try (FileInputStream fileInputStream = new FileInputStream(filePath);
             Workbook workbook = WorkbookFactory.create(fileInputStream)) {

            Sheet sheet = workbook.getSheetAt(0);
            int rows = sheet.getLastRowNum();

            for (int i = 1; i <= rows; i++) {
                Row row = sheet.getRow(i);

                if (row != null) {
                    boolean added = false;

                    if (id != null && row.getCell(0).getNumericCellValue() == id) {
                        localities.add(new LocalityRecordExel((int) row.getCell(0).getNumericCellValue(), row.getCell(1).getStringCellValue(), (int) row.getCell(2).getNumericCellValue(), row.getCell(3).getStringCellValue()));
                        return localities;
                    }

                    if (name != null && row.getCell(1).getStringCellValue().equalsIgnoreCase(name)) {
                        localities.add(new LocalityRecordExel((int) row.getCell(0).getNumericCellValue(), row.getCell(1).getStringCellValue(), (int) row.getCell(2).getNumericCellValue(), row.getCell(3).getStringCellValue()));
                        added = true;
                    }

                    if (postalCode != null && row.getCell(2).getNumericCellValue() == Integer.parseInt(postalCode) && !added) {
                        localities.add(new LocalityRecordExel((int) row.getCell(0).getNumericCellValue(), row.getCell(1).getStringCellValue(), (int) row.getCell(2).getNumericCellValue(), row.getCell(3).getStringCellValue()));
                    }
                }
            }
        } catch (FileNotFoundException e) {
            log.error("Archivo no encontrado: " + filePath, e);
            throw new RuntimeException("Archivo no encontrado: " + filePath, e);
        } catch (IOException e) {
            log.error("Error al leer el archivo de Excel", e);
        }

        return localities;
    }

    @Override
    public void createLocality(String filePath, LocalityDto newLocality) throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(filePath);
             Workbook workbook = WorkbookFactory.create(fileInputStream)) {

            Sheet sheet = workbook.getSheetAt(0);
            int lastRowNum = sheet.getLastRowNum();
            Row newRow = sheet.createRow(lastRowNum + 1);

            newRow.createCell(0).setCellValue(newRow.getRowNum());
            newRow.createCell(1).setCellValue(newLocality.getName());
            newRow.createCell(2).setCellValue(newLocality.getPostalCode());
            newRow.createCell(3).setCellValue(newLocality.getProvinceId());

            try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
                workbook.write(outputStream);
                log.info("Localidad creada exitosamente en archivo {}", filePath);
            }
        } catch (FileNotFoundException e) {
            log.error("Archivo no encontrado: {}", filePath, e);
            throw new RuntimeException("Archivo no encontrado: " + filePath, e);
        } catch (IOException e) {
            log.error("Error al leer el archivo de Excel", e);
        }
    }


    @Override
    public void updateLocality(Integer id, String filePath, LocalityDto updatedLocality) {
        try (FileInputStream fileInputStream = new FileInputStream(filePath);
             Workbook workbook = WorkbookFactory.create(fileInputStream)) {

            Sheet sheet = workbook.getSheetAt(0);
            Optional<Row> rowToUpdate = IntStream.range(1, sheet.getLastRowNum() + 1)
                    .mapToObj(sheet::getRow)
                    .filter(row -> row.getCell(0).getNumericCellValue() == id)
                    .findFirst();

            if (rowToUpdate.isPresent()) {
                Row row = rowToUpdate.get();
                row.getCell(0).setCellValue(id);
                row.getCell(1).setCellValue(updatedLocality.getName());
                row.getCell(2).setCellValue(updatedLocality.getPostalCode());
                row.getCell(3).setCellValue(updatedLocality.getProvinceId());
            } else {
                log.error("No se encontró la localidad con el ID {}", id);
                throw new ResourceNotFoundException(id.toString());
            }

            try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
                workbook.write(outputStream);
            }
        } catch (FileNotFoundException e) {
            log.error("Archivo no encontrado: {}", filePath, e);
            throw new RuntimeException("Archivo no encontrado: " + filePath, e);
        } catch (IOException e) {
            log.error("Error al actualizar la localidad", e);
            throw new ServiceException("Error al actualizar la localidad", id);
        }
    }

    @Override
    public void deleteLocality(String filePath, Integer id) throws IOException {
        Workbook workbook = WorkbookFactory.create(new FileInputStream(filePath));
        Sheet sheet = workbook.getSheetAt(0);
        Optional<Row> rowToDelete = IntStream.range(1, sheet.getLastRowNum() + 1)
                .mapToObj(sheet::getRow)
                .filter(row -> row.getCell(0).getNumericCellValue() == id)
                .findFirst();

        if (rowToDelete.isPresent()) {
            rowToDelete.ifPresent(row -> {
                int lastRowNum = sheet.getLastRowNum();
                if (row.getRowNum() < lastRowNum) {
                    sheet.shiftRows(row.getRowNum() + 1, lastRowNum, -1);
                }
                sheet.removeRow(row);
            });
        } else {
            log.warn("Localidad no encontrada para eliminar con ID {} en archivo {}", id, filePath);
        }

        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
            workbook.write(outputStream);
        } catch (FileNotFoundException e) {
            log.error("Archivo no encontrado: {}", filePath, e);
            throw new RuntimeException("Archivo no encontrado: " + filePath, e);
        } catch (IOException e) {
            log.error("Error al eliminar localidad en archivo {}", filePath, e);
            throw e;
        }
    }
}
