package com.anamariafelix.matematico.file.importer.factory;

import com.anamariafelix.matematico.exception.BadResquetException;
import com.anamariafelix.matematico.file.importer.contract.FileImporter;
import com.anamariafelix.matematico.file.importer.impl.CsvImporter;
import com.anamariafelix.matematico.file.importer.impl.XlsxImporter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Slf4j
@RequiredArgsConstructor
@Component //usado para conseguir fazer injesão de dependencia dele em outras classes
public class FileImporterFactory {

    private final ApplicationContext applicationContext;

    public FileImporter getImporter(String fileName) throws  Exception{

        if(fileName.endsWith(".xlsx")){
            return applicationContext.getBean(XlsxImporter.class);//dessa forma o spring fica responsavel por fazer a instanciação da classe pra mim, sem que eu precise instanciar ela usando new

        } else if (fileName.endsWith(".csv")) {
            return applicationContext.getBean(CsvImporter.class);

        }else{
            throw new BadResquetException("Formato de arquivo invalido!");
        }
    }
}
