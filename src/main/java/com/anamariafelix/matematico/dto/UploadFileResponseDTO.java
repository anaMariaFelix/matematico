package com.anamariafelix.matematico.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter @EqualsAndHashCode
public class UploadFileResponseDTO { //DTO do dowload e upload de arquivos

    private String fileName;
    private String fileDownloadUri;
    private String fileType;
    private Long size;
}
