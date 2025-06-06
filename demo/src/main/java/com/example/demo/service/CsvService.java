package com.example.demo.service;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.stereotype.Service;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class CsvService<T> {

    public <T> void exportCsv(String fileName, HttpServletResponse response, List<T> respList, Class<T> reqClass)
            throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

        try {
            OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream(), StandardCharsets.UTF_8);
            StatefulBeanToCsv<T> csvWriter = new StatefulBeanToCsvBuilder<T>(writer)
                    .withQuotechar(CSVWriter.DEFAULT_QUOTE_CHARACTER)
                    .withSeparator(';')
                    .withOrderedResults(true)
                    .build();

            csvWriter.write(respList);
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace(); // para diagnosticar
            throw new RuntimeException("Exceção ao exportar arquivo CSV", e);
        }
    }
}
