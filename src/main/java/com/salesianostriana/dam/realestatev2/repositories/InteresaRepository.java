package com.salesianostriana.dam.realestatev2.repositories;

import com.salesianostriana.dam.realestatev2.models.InteresPK;
import com.salesianostriana.dam.realestatev2.models.Interesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.UUID;

public interface InteresaRepository extends JpaRepository<Interesa, InteresPK> {

    @Query(
            """
            SELECT i
            FROM Interesa i
            WHERE i.vivienda.id = :id
              AND  i.interesado.id = :id2
            """
    )
    Interesa findOne(@Param("id") UUID idVivienda, @Param("id2") UUID idInteresado);
}
