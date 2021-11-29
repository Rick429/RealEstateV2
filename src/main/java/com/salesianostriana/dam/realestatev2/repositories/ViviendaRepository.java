package com.salesianostriana.dam.realestatev2.repositories;

import com.salesianostriana.dam.realestatev2.dto.CreateViviendaDto;
import com.salesianostriana.dam.realestatev2.dto.GetViviendaInteresa;
import com.salesianostriana.dam.realestatev2.dto.ViviendaDtoConverter;
import com.salesianostriana.dam.realestatev2.models.Vivienda;
import com.salesianostriana.dam.realestatev2.users.model.UserEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @EntityGraph("viviendas-propietario")
    public List<Vivienda> findViviendaByPropietario(UserEntity propietario);

//    @Query(value = """
//            select new com.salesianostriana.dam.realestatev2.dto.GetViviendaInteresa(
//                v.id, v.titulo, v.direccion, v.precio, v.tipo, v.estado, v.inmobiliaria, v.meInteresa
//             )from vivienda v LEFT JOIN v.inmobiliaria i LEFT JOIN v.interesa in
//            where v.propietario in
//            (select *
//            from users u join interesa i
//            on u.id = i.propietario_id
//            group by u.id)
//            """)
    @Query(value = """
            select new com.salesianostriana.dam.realestatev2.dto.GetViviendaInteresa(
                v.id, v.titulo, v.direccion, v.precio, v.tipo, v.estado
             )from Vivienda v join fetch Interesa i
             where v = i.vivienda and i.interesado = :id
            """)
    public List<GetViviendaInteresa> viviendasConInteres(@Param("id") UserEntity id);


}
