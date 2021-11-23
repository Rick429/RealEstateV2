package com.salesianostriana.dam.realestatev2.dto;

import com.salesianostriana.dam.realestatev2.models.Estado;
import com.salesianostriana.dam.realestatev2.models.Tipo;
import lombok.*;
import javax.persistence.Column;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@Builder
public class GetViviendaDto {
    private UUID id;
    private String titulo;
    private double precio;
    private Tipo tipo;
    private String descripcion, direccion;
    private int metrosCuadrados, numHabitaciones, numBanyos;
    private String latlng;
    private String avatarVivienda;
    private String avatarPropietario;
    private boolean tienePiscina, tieneAscensor, tieneGaraje;
    private int intereses;
    private String email;
    private String telefono;
    private String poblacion;
    private int codigoPostal;
    private String provincia;
    private Estado estado;
    private String nombre, apellidos;
    private UUID propietarioId;
    private UUID inmobiliariaId;
    private List<GetInteresaDto>interes;
}
