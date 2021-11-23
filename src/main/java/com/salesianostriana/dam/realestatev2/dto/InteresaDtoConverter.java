package com.salesianostriana.dam.realestatev2.dto;

import com.salesianostriana.dam.realestatev2.models.Interesa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InteresaDtoConverter {

    public GetInteresaDto interesaToGetInteresaDto(Interesa in){
        return GetInteresaDto.builder()
                .interesadoId(in.getInteresado().getId())
                .viviendaId(in.getVivienda().getId())
                .mensaje(in.getMensaje())
                .createdDate(in.getCreatedDate())
                .build();
    }
}
