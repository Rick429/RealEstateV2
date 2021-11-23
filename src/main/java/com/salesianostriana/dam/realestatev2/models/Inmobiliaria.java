package com.salesianostriana.dam.realestatev2.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.salesianostriana.dam.realestatev2.users.model.UserEntity;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inmobiliaria implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator",
            parameters = {
                    @Parameter(
                            name = "uuid_gen_strategy_class",
                            value = "org.hibernate.id.uuid.CustomVersionOneStrategy"
                    )
            }
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    private String nombre, email, telefono;

    @Builder.Default
    @OneToMany(mappedBy = "inmobiliaria", orphanRemoval = true)
    @JsonIgnore
    private List<Vivienda> viviendas = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "inmobiliaria")
    private List<UserEntity> gestores = new ArrayList<>();
}
