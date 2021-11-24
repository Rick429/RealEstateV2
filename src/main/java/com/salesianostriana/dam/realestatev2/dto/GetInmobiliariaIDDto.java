package com.salesianostriana.dam.realestatev2.dto;

import lombok.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class GetInmobiliariaIDDto {
    private String inmobiliaria;
    private List<GetViviendaDatosDto> listaViviendas = new ArrayList<>();
}
