package com.salesianostriana.dam.realestatev2.services;

import com.salesianostriana.dam.realestatev2.models.Inmobiliaria;
import com.salesianostriana.dam.realestatev2.repositories.InmobiliariaRepository;
import com.salesianostriana.dam.realestatev2.services.base.BaseService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class InmobiliariaService extends BaseService<Inmobiliaria, UUID, InmobiliariaRepository> {

    public List<Inmobiliaria> topInmobiliarias() {
        return repositorio.topInmobiliarias();
    }
}
