package com.anamariafelix.matematico.dto.request;

import lombok.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class EmailRequestDTO { //dto referente a classe de envio de email

    private String to;
    private String subject;
    private String body;
}
