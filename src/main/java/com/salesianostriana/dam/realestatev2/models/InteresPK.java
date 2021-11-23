package com.salesianostriana.dam.realestatev2.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class InteresPK implements Serializable {

    private UUID vivienda_id;

    private UUID interesado_id;
}
