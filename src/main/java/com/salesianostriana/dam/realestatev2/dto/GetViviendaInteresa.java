package com.salesianostriana.dam.realestatev2.dto;

import com.salesianostriana.dam.realestatev2.models.Estado;
import com.salesianostriana.dam.realestatev2.models.Tipo;
import lombok.*;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class GetViviendaInteresa {
    private UUID id;
    private String titulo;
    private String direccion;
    private double precio;
    private Tipo tipo;
    private Estado estado;
    //private boolean meInteresa;
}
