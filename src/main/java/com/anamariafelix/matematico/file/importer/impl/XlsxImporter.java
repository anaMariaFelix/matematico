package com.anamariafelix.matematico.file.importer.impl;

import com.anamariafelix.matematico.dto.PersonDTO;
import com.anamariafelix.matematico.file.importer.contract.FileImporter;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class XlsxImporter implements FileImporter {

    @Override
    public List<PersonDTO> importFile(InputStream inputStream) throws Exception {

        try (XSSFWorkbook workbook = new XSSFWorkbook(inputStream)) {//carrega a planilha em workbook
            XSSFSheet sheet = workbook.getSheetAt(0);//referente a qual aba da planilha da sendo usada, pq arquivos xlsx podem ter mais de uma aba
            Iterator<Row> rowIterator = sheet.iterator();//lista de linhas da planilha

            if (rowIterator.hasNext()){
                rowIterator.next();//pula a primeira linha da planilha pra n pegar os cabeçarios
            }

            return parseRowsToPersonDtoList(rowIterator);

        }
    }

    private List<PersonDTO> parseRowsToPersonDtoList(Iterator<Row> rowIterator) {
        List<PersonDTO> people = new ArrayList<>();

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            if (isRowValid(row)) {//se a linha for valida
                people.add(parseRowToPersonDto(row));//adciona na lista mas antes converte para persondto
            }
        }
        return people;
    }

    private PersonDTO parseRowToPersonDto(Row row) {
        PersonDTO personDTO = new PersonDTO();

        personDTO.setFirstName(row.getCell(0).getStringCellValue());//pega as posições e seta no person, as posições são as colunas da tabela
        personDTO.setLastName(row.getCell(1).getStringCellValue());
        personDTO.setAddress(row.getCell(2).getStringCellValue());
        personDTO.setGender(row.getCell(3).getStringCellValue());
        personDTO.setEnabled(true);
        return personDTO;
    }

    private static boolean isRowValid(Row row) {
        return row.getCell(0) != null && row.getCell(0).getCellType() != CellType.BLANK;
    }
}
