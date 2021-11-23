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
                .build();
    }

    public GetInmobiliariaDto getInmobiliariaDatosVivienda(Inmobiliaria i){
        return GetInmobiliariaDto
                .builder()
                .id(i.getId())
                .nombre(i.getNombre())
                .email(i.getEmail())
                .telefono(i.getTelefono())
                .listaViviendas(i.getViviendas().stream().map(viviendaDtoConverter::viviendaToGetViviendaDto).collect(Collectors.toList()))
                .build();
    }
}
