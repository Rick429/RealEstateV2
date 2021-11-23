package com.salesianostriana.dam.realestatev2.users.dto;

import com.salesianostriana.dam.realestatev2.dto.GetViviendaBasicaDto;
import lombok.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class GetPropietarioDtoVi{
    private UUID id;
    private String avatar, nombre, apellidos, direccion, email, telefono;
    private List<GetViviendaBasicaDto> listaViviendas = new ArrayList<>();
}
