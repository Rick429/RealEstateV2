package com.salesianostriana.dam.realestatev2.repositories;

import com.salesianostriana.dam.realestatev2.models.Inmobiliaria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface InmobiliariaRepository extends JpaRepository<Inmobiliaria, UUID> {

    @Query(value = """
            select * from inmobiliaria i
            where i.id in
            (select i1.id
            from inmobiliaria i1 join vivienda v
            on i1.id = v.id
            group by i1.id
            order by count(*) desc
            limit 6)
            """, nativeQuery = true)
    public List<Inmobiliaria> topInmobiliarias();

}
