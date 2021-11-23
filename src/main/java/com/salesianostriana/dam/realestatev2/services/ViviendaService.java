package com.salesianostriana.dam.realestatev2.services;

import com.salesianostriana.dam.realestatev2.dto.GetViviendaDto;
import com.salesianostriana.dam.realestatev2.dto.ViviendaDtoConverter;
import com.salesianostriana.dam.realestatev2.models.*;
import com.salesianostriana.dam.realestatev2.repositories.ViviendaRepository;
import com.salesianostriana.dam.realestatev2.services.base.BaseService;
import com.salesianostriana.dam.realestatev2.users.dto.GetInteresadoDto;
import com.salesianostriana.dam.realestatev2.users.dto.UserDtoConverter;
import com.salesianostriana.dam.realestatev2.users.model.UserEntity;
import com.salesianostriana.dam.realestatev2.users.service.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ViviendaService extends BaseService<Vivienda, UUID, ViviendaRepository> {

    private final InmobiliariaService inmobiliariaService;
    private final UserEntityService userEntityService;
    private final InteresaService interesaService;
    private final ViviendaDtoConverter dtoConverter;
    private final UserDtoConverter userDtoConverter;

    public List<Vivienda> viviendasConSpecification(Specification<Vivienda> spec) {
        return repositorio.findAll(spec);
    }

    public List<Vivienda> top3Viviendas() {
        return repositorio.topViviendas();
    }

    public Page<Vivienda> findByArgs (final Optional<String>titulo,
                                      final Optional<String> provincia,
                                      final Optional<String> direccion,
                                      final Optional<String> poblacion,
                                      final Optional<Integer>codigoPostal,
                                      final Optional<Integer>numBanyos,
                                      final Optional<Integer>numHabitaciones,
                                      final Optional<Integer>metrosCuadrados,
                                      final Optional<Double>precio,
                                      final Optional <Boolean>tienePiscina,
                                      final Optional<Boolean> tieneAscensor,
                                      final Optional<Boolean> tieneGaraje,
                                      final Optional<Tipo> tipo,
                                      final Optional<Estado> estado,
                                      Pageable pageable){

        Specification<Vivienda> specTituloVivienda = new Specification<Vivienda>() {
            @Override
            public Predicate toPredicate(Root<Vivienda> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if(titulo.isPresent()){
                    //Si siempre está presente se filtra por nombre
                    return criteriaBuilder.like(criteriaBuilder.lower(root.get("titulo")), "%" + titulo.get() + "%");
                }else{
                    //Si no está presente siempre estará en verdadero
                    return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
                }
            }
        };
        Specification<Vivienda> specProvinciaVivienda = new Specification<Vivienda>() {
            @Override
            public Predicate toPredicate(Root<Vivienda> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if(provincia.isPresent()){
                    //Si siempre está presente se filtra por nombre
                    return criteriaBuilder.like(criteriaBuilder.lower(root.get("provincia")), "%" + provincia.get() + "%");
                }else{
                    //Si no está presente siempre estará en verdadero
                    return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
                }
            }
        };
        Specification<Vivienda> specDireccionVivienda = new Specification<Vivienda>() {
            @Override
            public Predicate toPredicate(Root<Vivienda> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if(direccion.isPresent()){
                    //Si siempre está presente se filtra por nombre
                    return criteriaBuilder.like(criteriaBuilder.lower(root.get("direccion")), "%" + direccion.get() + "%");
                }else{
                    //Si no está presente siempre estará en verdadero
                    return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
                }
            }
        };
        Specification<Vivienda> specPoblacionVivienda = new Specification<Vivienda>() {
            @Override
            public Predicate toPredicate(Root<Vivienda> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if(poblacion.isPresent()){
                    //Si siempre está presente se filtra por nombre
                    return criteriaBuilder.like(criteriaBuilder.lower(root.get("poblacion")), "%" + poblacion.get() + "%");
                }else{
                    //Si no está presente siempre estará en verdadero
                    return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
                }
            }
        };
        Specification<Vivienda> specCodigoPostalVivienda = new Specification<Vivienda>() {
            @Override
            public Predicate toPredicate(Root<Vivienda> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if(codigoPostal.isPresent()){
                    //Si siempre está presente se filtra por nombre
                    return criteriaBuilder.equal(root.get("codigoPostal"),  codigoPostal.get() );
                }else{
                    //Si no está presente siempre estará en verdadero
                    return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
                }
            }
        };
        Specification<Vivienda> specBanyosVivienda = new Specification<Vivienda>() {
            @Override
            public Predicate toPredicate(Root<Vivienda> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if(numBanyos.isPresent()){
                    //Si siempre está presente se filtra por nombre
                    return criteriaBuilder.equal(root.get("numBanyos"),  numBanyos.get() );
                }else{
                    //Si no está presente siempre estará en verdadero
                    return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
                }
            }
        };
        Specification<Vivienda> specNumHabitacionesVivienda = new Specification<Vivienda>() {
            @Override
            public Predicate toPredicate(Root<Vivienda> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if(numHabitaciones.isPresent()){
                    //Si siempre está presente se filtra por nombre
                    return criteriaBuilder.equal(root.get("numHabitaciones"),  numHabitaciones.get() );
                }else{
                    //Si no está presente siempre estará en verdadero
                    return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
                }
            }
        };
        Specification<Vivienda> specMetrosCuadradosVivienda = new Specification<Vivienda>() {
            @Override
            public Predicate toPredicate(Root<Vivienda> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if(metrosCuadrados.isPresent()){
                    //Si siempre está presente se filtra por nombre
                    return criteriaBuilder.equal(root.get("metrosCuadrados"),  metrosCuadrados.get() );
                }else{
                    //Si no está presente siempre estará en verdadero
                    return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
                }
            }
        };
        Specification<Vivienda> precioMenorQue = new Specification<Vivienda>() {
            @Override
            public Predicate toPredicate(Root<Vivienda> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if(precio.isPresent()){
                    //Si siempre está presente se filtra por nombre
                    return criteriaBuilder.lessThanOrEqualTo(root.get("precio"), precio.get());
                }else{
                    //Si no está presente siempre estará en verdadero
                    return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
                }
            }
        };
        Specification<Vivienda> specTienePiscina = new Specification<Vivienda>() {
            @Override
            public Predicate toPredicate(Root<Vivienda> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if(tienePiscina.isPresent()){
                    //Si siempre está presente se filtra por nombre
                    return criteriaBuilder.equal(root.get("tienePiscina"), tienePiscina.get());
                }else{
                    //Si no está presente siempre estará en verdadero
                    return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
                }
            }
        };
        Specification<Vivienda> specAscensor = new Specification<Vivienda>() {
            @Override
            public Predicate toPredicate(Root<Vivienda> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if(tieneAscensor.isPresent()){
                    //Si siempre está presente se filtra por nombre
                    return criteriaBuilder.equal(root.get("tieneAscensor"), tieneAscensor.get());
                }else{
                    //Si no está presente siempre estará en verdadero
                    return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
                }
            }
        };
        Specification<Vivienda> specGaraje = new Specification<Vivienda>() {
            @Override
            public Predicate toPredicate(Root<Vivienda> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if(tieneGaraje.isPresent()){
                    //Si siempre está presente se filtra por nombre
                    return criteriaBuilder.equal(root.get("tieneGaraje"), tieneGaraje.get());
                }else{
                    //Si no está presente siempre estará en verdadero
                    return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
                }
            }
        };
        //Funciona usando mayúsculas
        Specification<Vivienda> specTipo = new Specification<Vivienda>() {
            @Override
            public Predicate toPredicate(Root<Vivienda> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if(tipo.isPresent()){
                    //Si siempre está presente se filtra por nombre
                    return criteriaBuilder.equal(root.get("tipo"), tipo.get());
                }else{
                    //Si no está presente siempre estará en verdadero
                    return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
                }
            }
        };
        Specification<Vivienda> specEstado = new Specification<Vivienda>() {
            @Override
            public Predicate toPredicate(Root<Vivienda> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if(estado.isPresent()){
                    //Si siempre está presente se filtra por nombre
                    return criteriaBuilder.equal(root.get("estado"), estado.get());
                }else{
                    //Si no está presente siempre estará en verdadero
                    return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
                }
            }
        };

        Specification<Vivienda> todas = specTituloVivienda
                .and(specProvinciaVivienda)
                .and(specDireccionVivienda)
                .and(specPoblacionVivienda)
                .and(specCodigoPostalVivienda)
                .and(specBanyosVivienda)
                .and(specNumHabitacionesVivienda)
                .and(specMetrosCuadradosVivienda)
                .and(precioMenorQue)
                .and(specTienePiscina)
                .and(specAscensor)
                .and(specGaraje)
                .and(specTipo)
                .and(specEstado);
        return this.repositorio.findAll(todas, pageable);
    }

    public ResponseEntity<GetViviendaDto> asignarInmobiliariaAVivienda(UUID id, UUID id2) {
        if (this.findById(id).isEmpty() || inmobiliariaService.findById(id2).isEmpty()) {
            return ResponseEntity
                    .notFound()
                    .build();
        } else {
            Vivienda v = this.findById(id).get();
            Inmobiliaria inmoAsignada = inmobiliariaService.findById(id2).get();
            v.addToInmobiliaria(inmoAsignada);
            this.save(v);
            GetViviendaDto viviendaDto = this.findById(id).map(dtoConverter::viviendaToGetViviendaDto).get();
            return ResponseEntity.ok().body(viviendaDto);
        }

    }

    public ResponseEntity<?> eliminarInmobiliariaDeVivienda(UUID id, UUID id2) {
        if (this.findById(id).isEmpty() || inmobiliariaService.findById(id2).isEmpty()) {
            return ResponseEntity
                    .notFound()
                    .build();
        } else {
            Vivienda v = this.findById(id).get();
            Inmobiliaria inmoAsignada = inmobiliariaService.findById(id2).get();
            v.removeFromInmobiliaria(inmoAsignada);
            this.save(v);
            return ResponseEntity
                    .noContent()
                    .build();
        }
    }

    public ResponseEntity <List<Interesa>> listInteresados(UUID id) {
        List<Interesa> intereses = this.findById(id).get().getIntereses();
        if(intereses.isEmpty()) {
            return ResponseEntity
                    .notFound()
                    .build();
        } else {
            return ResponseEntity
                    .ok().body(intereses);
        }
    }

    public ResponseEntity<UserEntity> createInteresaAndInteresado(GetInteresadoDto interesadoDTO, UUID id) {
        UserEntity interesado = userDtoConverter.getInteresadoDtoToInteresado(interesadoDTO);
        Interesa interesa = userDtoConverter.getMeInteresa(interesadoDTO);

        userEntityService.save(interesado);
        interesa.addToInteresado(interesado);
        interesa.addToVivienda(this.findById(id).get());

        interesaService.save(interesa);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userEntityService.save(interesado));
    }

    public ResponseEntity<UserEntity> createInteresa(GetInteresadoDto interesadoDto, UUID id){
        UserEntity interesado = userDtoConverter.getInteresadoDtoToInteresado(interesadoDto);
        Interesa interesa = userDtoConverter.getMeInteresa(interesadoDto);
        Vivienda vivienda = this.findById(id).get();
        interesaService.darMeInteresa(vivienda, interesado, interesadoDto.getMensaje());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userEntityService.save(interesado));
    }

    public ResponseEntity<?> eliminarInteres(UUID id, GetInteresadoDto id2){
        Interesa i = interesaService.findOne(id, id2.getId());
        interesaService.delete(i);
        return ResponseEntity.noContent().build();
    }
}
