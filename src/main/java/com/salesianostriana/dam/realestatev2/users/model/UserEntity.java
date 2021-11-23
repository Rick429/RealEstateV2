package com.salesianostriana.dam.realestatev2.users.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.salesianostriana.dam.realestatev2.models.Inmobiliaria;
import com.salesianostriana.dam.realestatev2.models.Interesa;
import com.salesianostriana.dam.realestatev2.models.Vivienda;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Parameter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="users")
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter @Builder
public class UserEntity implements UserDetails {

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
    private String nombre, apellidos, direccion;
    @NaturalId
    @Column(unique = true, updatable = false)
    private String email;
    private String telefono, avatar;
    private String password;
    private UserRole role;
    @CreatedDate
    private LocalDateTime createdAt;

    @Builder.Default
    private LocalDateTime lastPasswordChangeAt = LocalDateTime.now();
    @OneToMany(mappedBy = "propietario", orphanRemoval = true)
    @Builder.Default
    private List<Vivienda> viviendas = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "interesado")
    private List<Interesa> intereses = new ArrayList<>();

    @ManyToOne
    private Inmobiliaria inmobiliarias;


    /*Helpers*/

    public void addGestorInmobiliaria (Inmobiliaria i) {
        inmobiliarias = i;
        i.getGestores().add(this);
    }

    public void removeGestorInmobiliaria (Inmobiliaria i) {
        i.getGestores().remove(this);
        inmobiliarias = null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }
    /**
     * No vamos a gestionar la expiración de cuentas. De hacerse, se tendría que dar
     * cuerpo a este método
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * No vamos a gestionar el bloqueo de cuentas. De hacerse, se tendría que dar
     * cuerpo a este método
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * No vamos a gestionar la expiración de cuentas. De hacerse, se tendría que dar
     * cuerpo a este método
     */

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


    /**
     * No vamos a gestionar el bloqueo de cuentas. De hacerse, se tendría que dar
     * cuerpo a este método
     */
    @Override
    public boolean isEnabled() {
        return true;
    }


}
