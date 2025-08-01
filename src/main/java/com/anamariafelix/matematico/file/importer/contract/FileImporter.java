package com.anamariafelix.matematico.file.importer.contract;

import com.anamariafelix.matematico.dto.PersonDTO;

import java.io.InputStream;
import java.util.List;

public interface FileImporter {

    List<PersonDTO> importFile(InputStream inputStream) throws Exception;
}
