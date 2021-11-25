package com.salesianostriana.dam.realestatev2.repositories;

import com.salesianostriana.dam.realestatev2.dto.CreateViviendaDto;
import com.salesianostriana.dam.realestatev2.dto.ViviendaDtoConverter;
import com.salesianostriana.dam.realestatev2.models.Vivienda;
import com.salesianostriana.dam.realestatev2.users.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import java.util.List;
import java.util.UUID;
public interface ViviendaRepository extends JpaRepository<Vivienda, UUID>, JpaSpecificationExecutor<Vivienda> {

    @Query(value = """
            select * from vivienda v
            where v.id in
            (select v1.id
            from vivienda v1 join interesa i
            on v1.id = i.vivienda_id
            group by v1.id
            order by count(*) desc
            limit 3)
            """, nativeQuery = true)
    public List<Vivienda> topViviendas();

}
