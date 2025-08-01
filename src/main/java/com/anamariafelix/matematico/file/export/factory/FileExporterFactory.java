package com.anamariafelix.matematico.file.export.factory;

import com.anamariafelix.matematico.exception.BadResquetException;
import com.anamariafelix.matematico.file.export.MediaTypes;
import com.anamariafelix.matematico.file.export.contract.FileExporter;
import com.anamariafelix.matematico.file.export.impl.CsvExporter;
import com.anamariafelix.matematico.file.export.impl.XlsxExporter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component //usado para conseguir fazer injesão de dependencia dele em outras classes
public class FileExporterFactory {

    private final ApplicationContext applicationContext;

    public FileExporter getExporter(String acceptHeader) throws Exception {

        if (acceptHeader.equalsIgnoreCase(MediaTypes.APPLICATION_XLSX_VALUE)) {
            return applicationContext.getBean(XlsxExporter.class);

        } else if (acceptHeader.equalsIgnoreCase(MediaTypes.APPLICATION_CSV_VALUE)) {
            return applicationContext.getBean(CsvExporter.class);

        } else {
            throw new BadResquetException("Invalid File Format!");
        }
    }
}
