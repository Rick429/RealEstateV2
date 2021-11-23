package com.salesianostriana.dam.realestatev2.users.repository;


import com.salesianostriana.dam.realestatev2.users.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserEntityRepository extends JpaRepository<UserEntity, UUID> {

    @Query(value = """
            select * from persona p
            where p.id in
            (select p1.id
            from persona p1 join vivienda v
            on p1.id = v.propietario_id
            group by p1.id
            order by count(*) desc
            limit 6)
            """, nativeQuery = true)
    public List<UserEntity> topPropietarios();

    Optional<UserEntity> findFirstByEmail(String email);

}
