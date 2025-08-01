package com.anamariafelix.matematico.service;

import com.anamariafelix.matematico.dto.PersonDTO;
import com.anamariafelix.matematico.exception.BadResquetException;
import com.anamariafelix.matematico.exception.FileStorageException;
import com.anamariafelix.matematico.exception.ResourceNotFoundException;
import com.anamariafelix.matematico.file.export.contract.FileExporter;
import com.anamariafelix.matematico.file.export.factory.FileExporterFactory;
import com.anamariafelix.matematico.file.importer.contract.FileImporter;
import com.anamariafelix.matematico.file.importer.factory.FileImporterFactory;
import com.anamariafelix.matematico.model.Person;
import com.anamariafelix.matematico.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import static com.anamariafelix.matematico.mapper.ObjectMapper.toDTO;
import static com.anamariafelix.matematico.mapper.ObjectMapper.toEntity;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonServices {

    private final AtomicLong counter = new AtomicLong();

    private final PersonRepository personRepository;

    private final FileImporterFactory fileImporterFactory;

    private final FileExporterFactory fileExporterFactory;

    public List<Person> findAll() {

        log.info("Finding all People!");

        return personRepository.findAll();
    }

    public Person findById(Long id) {
        log.info("Finding one Person!");

        return personRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No records found for this ID!"));

    }

    public Resource exportPage(String acceptHeader) {

        log.info("Exporting a People page!");

        List<PersonDTO> people = personRepository.findAll().stream()
                .map(person -> toDTO(person))
                .collect(Collectors.toList());

        try {
            FileExporter exporter = this.fileExporterFactory.getExporter(acceptHeader);
            return exporter.exportFile(people);
        } catch (Exception e) {
            throw new RuntimeException("Error during file export!", e);
        }
    }

    public Person create(Person person) {

        log.info("Creating one Person!");
        return personRepository.save(person);
    }

    public Person update(Person person) {

        log.info("Updating one Person!");
        Person entity = personRepository.findById(person.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        return personRepository.save(person);
    }

    public void delete(Long id) {

        Person entity = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        personRepository.delete(entity);
    }

    //metodo para trabalhar com csv e xlsx
    public List<PersonDTO> massCreation(MultipartFile file) {
        log.info("Importing People from file!");

        //verifica se o file estapreenchido
        if (file.isEmpty()) throw new BadResquetException("Please set a Valid File!");
        log.info("Validou se o arquivo é vazio");

        try(InputStream inputStream = file.getInputStream()){
            log.info("dentro do try");
            String filename = Optional.ofNullable(file.getOriginalFilename())
                    .orElseThrow(() -> new BadResquetException("O nome do arquivo"));//feito para descobri qual instancia sera utilizada no factory se é pra trabalhar com csv ou com xlsx
            log.info("pegou o nome do arquivo "+ filename);

            FileImporter importer = this.fileImporterFactory.getImporter(filename);//passa para factory para ela dizer qual a classe que vai trabalhar com esse arquivo se é a de csv ou a de xlsx
            log.info("descobiu a instancia do import "+ importer.getClass().getName());

            //o importer faz referencia a uma das classes csvImport ou xlsxImporte
            List<Person> entities = importer.importFile(inputStream).stream()//conver a lista de persondto em entidades person e salva um a um no banco
                    .map(dto -> personRepository.save(toEntity(dto)))
                    .toList();
            log.info("salvou");

            List<PersonDTO> personDTOS = entities.stream()//converte para dtos para ser retornado
                    .map(entity -> {
                        var dto = toDTO(entity);
                        return dto;
                    })
                    .toList();

            return personDTOS;

        } catch (Exception e) {
            log.error("catch do metodo lançanda a exceptio FileStorageException");
            throw new FileStorageException("Error processing the file!");
        }
    }

}
