package com.salesianostriana.dam.realestatev2.services;

import com.salesianostriana.dam.realestatev2.models.InteresPK;
import com.salesianostriana.dam.realestatev2.models.Interesa;
import com.salesianostriana.dam.realestatev2.models.Vivienda;
import com.salesianostriana.dam.realestatev2.repositories.InteresaRepository;
import com.salesianostriana.dam.realestatev2.services.base.BaseService;
import com.salesianostriana.dam.realestatev2.users.model.UserEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InteresaService extends BaseService<Interesa, InteresPK, InteresaRepository> {

    public void darMeInteresa(Vivienda v, UserEntity i, String mensaje) {
        Interesa interes = Interesa.builder()
                .interesado(i)
                .vivienda(v)
                .mensaje(mensaje)
                .build();
        interes.addToInteresado(i);
        interes.addToVivienda(v);
        this.save(interes);

    }

    public Interesa findOne(UUID id, UUID id2){
        return repositorio.findOne(id, id2);
    }
}

