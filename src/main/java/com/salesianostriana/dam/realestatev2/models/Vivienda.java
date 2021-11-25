package com.salesianostriana.dam.realestatev2.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.salesianostriana.dam.realestatev2.users.model.UserEntity;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
//@NamedEntityGraph(
//        name = "vivienda-interesa",
//        attributeNodes = {
//                @NamedAttributeNode("intereses"),
//                @NamedAttributeNode("interesado")
//        }
//)

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Vivienda implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(
                            name = "uuid_gen_strategy_class",
                            value = "org.hibernate.id.uuid.CustomVersionOneStrategy"
                    )
            }
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    private String titulo, latlng, direccion, poblacion, provincia;
    @Column(length = 2000)
    private String descripcion;
    @Column(length = 600)
    private String avatar;
    private int codigoPostal, numHabitaciones, metrosCuadrados, numBanyos;
    @Enumerated(EnumType.STRING)
    private Tipo tipo;
    @Enumerated(EnumType.STRING)
    private Estado estado;
    private double precio;
    private boolean tienePiscina, tieneAscensor, tieneGaraje;

    @ManyToOne
    @JsonIgnoreProperties("propietario")
    private UserEntity propietario;

    public Vivienda(String titulo, String descripcion, String avatar, String latlng, String direccion, String poblacion, String provincia, int codigoPostal, int numHabitaciones, int metrosCuadrados, int numBanyos, Tipo tipo, Estado estado, double precio, boolean tienePiscina, boolean tieneAscensor, boolean tieneGaraje) {
    }

    /* HELPERS */

    public void addPropietario(UserEntity p) {
        this.propietario = p;
        p.getViviendas().add(this);
    }

    public void removePropietario(UserEntity p) {
        p.getViviendas().remove(this);
        this.propietario = null;
    }

    @Builder.Default
    @OneToMany(mappedBy = "vivienda", orphanRemoval = true)
    private List<Interesa> intereses = new ArrayList<>();


    @ManyToOne
    @JoinColumn(name = "inmobiliaria", foreignKey = @ForeignKey(name = "FK_VIVIENDA_INMOBILIARIA"), nullable = true)
    @JsonIgnoreProperties("inmobiliaria")
    private Inmobiliaria inmobiliaria;

    //Helpers
    public void addToInmobiliaria(Inmobiliaria i){
        this.inmobiliaria = i;
        i.getViviendas().add(this);
    }

    public void removeFromInmobiliaria(Inmobiliaria i){
        i.getViviendas().remove(this);
        this.inmobiliaria = null;
    }
}
