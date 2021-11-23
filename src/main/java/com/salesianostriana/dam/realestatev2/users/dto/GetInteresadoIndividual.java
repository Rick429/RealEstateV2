package com.salesianostriana.dam.realestatev2.users.dto;

import lombok.*;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class GetInteresadoIndividual {
    private UUID id;
    private String nombre, apellidos, direccion, email, telefono, avatar;
}
