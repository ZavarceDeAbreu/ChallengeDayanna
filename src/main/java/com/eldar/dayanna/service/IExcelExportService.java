package com.eldar.dayanna.service;

import java.io.IOException;

public interface IExcelExportService {
    void exportProvincesAndLocalitiesToExcel(String filePath) throws IOException;
}
