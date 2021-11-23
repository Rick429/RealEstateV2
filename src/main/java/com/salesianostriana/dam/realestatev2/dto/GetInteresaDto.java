package com.salesianostriana.dam.realestatev2.dto;

import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class GetInteresaDto {

    private UUID interesadoId;
    private UUID viviendaId;
    private LocalDateTime createdDate;
    private String mensaje;
}
