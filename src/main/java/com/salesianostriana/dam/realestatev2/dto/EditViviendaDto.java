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
public class EditViviendaDto {
    private UUID id;
    private String titulo;
    private double precio;
    private Tipo tipo;
    private String descripcion, direccion;
    private int metrosCuadrados, numHabitaciones, numBanyos;
    private String latlng;
    private String avatar;
    private boolean tienePiscina, tieneAscensor, tieneGaraje;
    private String poblacion;
    private int codigoPostal;
    private String provincia;
    private Estado estado;
}
