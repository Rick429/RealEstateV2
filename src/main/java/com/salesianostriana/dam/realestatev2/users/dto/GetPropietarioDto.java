package com.salesianostriana.dam.realestatev2.users.dto;

import com.salesianostriana.dam.realestatev2.dto.GetViviendaDto;
import lombok.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class GetPropietarioDto {
    private UUID id;
    private String avatar, nombre, apellidos, direccion, email, telefono;
    private List<GetViviendaDto> listaViviendas = new ArrayList<>();

}
