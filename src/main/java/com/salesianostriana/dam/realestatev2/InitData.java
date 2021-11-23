package com.salesianostriana.dam.realestatev2;

import com.salesianostriana.dam.realestatev2.models.Estado;
import com.salesianostriana.dam.realestatev2.models.Tipo;
import com.salesianostriana.dam.realestatev2.models.Vivienda;
import com.salesianostriana.dam.realestatev2.security.PasswordEncoderConfig;
import com.salesianostriana.dam.realestatev2.services.InmobiliariaService;
import com.salesianostriana.dam.realestatev2.services.ViviendaService;
import com.salesianostriana.dam.realestatev2.users.model.UserEntity;
import com.salesianostriana.dam.realestatev2.users.model.UserRole;
import com.salesianostriana.dam.realestatev2.users.service.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class InitData {

    private final UserEntityService userEntityService;
    private final ViviendaService viviendaService;
    private final InmobiliariaService inmobiliariaService;
    private final PasswordEncoderConfig passwordEncoderConfig;

    @PostConstruct
    public void test() {

    }
}
