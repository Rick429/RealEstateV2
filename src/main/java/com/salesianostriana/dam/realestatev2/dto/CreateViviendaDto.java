package com.salesianostriana.dam.realestatev2.dto;

import com.salesianostriana.dam.realestatev2.models.Estado;
import com.salesianostriana.dam.realestatev2.models.Tipo;
import lombok.*;
import javax.persistence.Column;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder @Getter @Setter
public class CreateViviendaDto {

    private String titulo, descripcion, avatar, latlng, direccion, poblacion, provincia;
    private int codigoPostal, numHabitaciones, metrosCuadrados, numBanyos;
    private Tipo tipo;
    private Estado estado;
    private double precio;
    private boolean tienePiscina, tieneAscensor, tieneGaraje;
    private UUID propietarioId;
    @Column(nullable = true)
    private UUID inmobiliariaId;
}
