package com.anamariafelix.matematico.file.importer.impl;

import com.anamariafelix.matematico.dto.PersonDTO;
import com.anamariafelix.matematico.file.importer.contract.FileImporter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvImporter implements FileImporter {
    @Override
    public List<PersonDTO> importFile(InputStream inputStream) throws Exception {

        //usado para transforma a planilha em iterables
        CSVFormat format = CSVFormat.Builder.create()
                .setHeader()
                .setSkipHeaderRecord(true)//pula o cabeçario
                .setIgnoreEmptyLines(true)//ignora linha vazias
                .setTrim(true)//elimina os espaços que tem antes e depois da linha
                .build();
        //pega cada linha da planilha e tranforma ela em uma linha de records e guadando em uma lista de recordes
        Iterable<CSVRecord> records = format.parse(new InputStreamReader(inputStream));//cada record dessa lista com tem as informações de uma persondto
        return parseRecordsToPersonDTO(records);
    }

    private List<PersonDTO> parseRecordsToPersonDTO(Iterable<CSVRecord> records) {

        List<PersonDTO> people = new ArrayList<>();

        for(CSVRecord record: records){//vai transforma cada records em um PersonDto

            PersonDTO personDTO = new PersonDTO();

            personDTO.setFirstName(record.get("first_name"));
            personDTO.setLastName(record.get("last_name"));
            personDTO.setAddress(record.get("address"));
            personDTO.setGender(record.get("gender"));
            personDTO.setEnabled(true);
            people.add(personDTO);
        }

        return people;
    }
}
