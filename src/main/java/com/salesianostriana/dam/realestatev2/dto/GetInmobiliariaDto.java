package com.salesianostriana.dam.realestatev2.dto;

import lombok.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class GetInmobiliariaDto {
    private UUID id;
    private String nombre, email, telefono;

    private List<GetViviendaDto> listaViviendas = new ArrayList<>();
}
