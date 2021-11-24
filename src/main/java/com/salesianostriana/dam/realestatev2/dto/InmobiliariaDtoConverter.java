package com.salesianostriana.dam.realestatev2.dto;

import com.salesianostriana.dam.realestatev2.models.Inmobiliaria;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class InmobiliariaDtoConverter {

    private final ViviendaDtoConverter viviendaDtoConverter;

    public GetInmobiliariaDto getInmobiliariaDatos(Inmobiliaria i){
        return GetInmobiliariaDto
                .builder()
                .id(i.getId())
                .nombre(i.getNombre())
                .email(i.getEmail())
                .telefono(i.getTelefono())
                .num_viviendas(i.getViviendas().size())
                .num_gestores(i.getGestores().size())
                .build();
    }

    public GetInmobiliariaDto getInmobiliariaDatosVivienda(Inmobiliaria i){
        return GetInmobiliariaDto
                .builder()
                .id(i.getId())
                .nombre(i.getNombre())
                .email(i.getEmail())
                .telefono(i.getTelefono())
                .build();
    }

    public GetInmobiliariaIDDto inmobiliariaToGetInmobiliariaIDDto(Inmobiliaria i){
        return GetInmobiliariaIDDto
                .builder()
                .inmobiliaria(i.getNombre())
                .listaViviendas(i.getViviendas().stream().map(viviendaDtoConverter::viviendaToGetViviendaDatosDto).collect(Collectors.toList()))
                .build();
    }
}
