package com.anamariafelix.matematico.file.export.contract;

import com.anamariafelix.matematico.dto.PersonDTO;
import org.springframework.core.io.Resource;

import java.io.InputStream;
import java.util.List;

public interface FileExporter {

     Resource exportFile(List<PersonDTO> people) throws Exception;
}
