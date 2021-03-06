package com.salesianostriana.dam.realestatev2.controllers;

import com.salesianostriana.dam.realestatev2.dto.GetInmobiliariaDto;
import com.salesianostriana.dam.realestatev2.dto.GetInmobiliariaIDDto;
import com.salesianostriana.dam.realestatev2.dto.InmobiliariaDtoConverter;
import com.salesianostriana.dam.realestatev2.models.Inmobiliaria;
import com.salesianostriana.dam.realestatev2.services.InmobiliariaService;
import com.salesianostriana.dam.realestatev2.upload.PaginationLinksUtils;
import com.salesianostriana.dam.realestatev2.users.dto.CreateUserDto;
import com.salesianostriana.dam.realestatev2.users.dto.GetUserDto;
import com.salesianostriana.dam.realestatev2.users.dto.UserDtoConverter;
import com.salesianostriana.dam.realestatev2.users.model.UserEntity;
import com.salesianostriana.dam.realestatev2.users.model.UserRole;
import com.salesianostriana.dam.realestatev2.users.service.UserEntityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequiredArgsConstructor
@RequestMapping("/inmobiliaria")
@Tag(name = "Inmobiliaria", description = "Clase controladora de inmobiliarias")
public class InmobiliariaController {

    private final InmobiliariaService service;
    private final InmobiliariaDtoConverter dtoConverter;
    private final UserDtoConverter userDtoConverter;
    private final UserEntityService userEntityService;
    private final PaginationLinksUtils paginationLinksUtils;

    @Operation(summary = "Crear una nueva inmobiliaria ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se crea correctamente una inmobiliaria",
                    content = {@Content(mediaType = "aplication/json",
                            schema = @Schema(implementation = Inmobiliaria.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Hay un error en los datos",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "No tiene permisos para realizar esta acci??n",
                    content = @Content),
    })
    @PostMapping("/")
    public ResponseEntity<Inmobiliaria> create(@AuthenticationPrincipal UserEntity user, @RequestBody Inmobiliaria nuevo) {

        if (user.getRole().equals(UserRole.ADMIN)) {
            if (nuevo.getNombre().isEmpty()) {
                return ResponseEntity.badRequest().build();
            } else {
                return ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(service.save(nuevo));
            }
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @Operation(summary = "Asignar un gestor a una inmobiliaria ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se crea un gestor y se le asigna a la inmobiliaria",
                    content = {@Content(mediaType = "aplication/json",
                            schema = @Schema(implementation = Inmobiliaria.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Hay un error en los datos",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "No tiene permiso para realizar esta acci??n",
                    content = @Content),
    })
    @PostMapping("/{id}/gestor/")
    public ResponseEntity<CreateUserDto> crearGestor(@AuthenticationPrincipal UserEntity user,
                                                     @PathVariable UUID id, @RequestBody CreateUserDto nuevoGestor) {
        Optional<Inmobiliaria> i = service.findById(id);
        if (i.isPresent()) {
            if (user.getRole().equals(UserRole.GESTOR) && user.getInmobiliaria().equals(i.get().getId()) || user.getRole().equals(UserRole.ADMIN)) {
                if (nuevoGestor.getEmail().isEmpty()) {
                    return ResponseEntity.badRequest().build();
                } else {
                    UserEntity saved = userEntityService.saveGestor(nuevoGestor, i.get());
                    saved.addGestorInmobiliaria(i.get());
                    service.edit(i.get());
                    return ResponseEntity.ok().body(nuevoGestor);
                }
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @Operation(summary = "Eliminar gestor de una inmobiliaria ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Se elimina el gestor correctamente",
                    content = {@Content(mediaType = "aplication/json",
                            schema = @Schema(implementation = Inmobiliaria.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No existe un gestor con ese id",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "No tiene permiso para realizar esta acci??n",
                    content = @Content),
    })
    @DeleteMapping("/gestor/{id}")
    public ResponseEntity<Inmobiliaria> deleteGestor(@AuthenticationPrincipal UserEntity user,
                                                     @PathVariable UUID id) {
        Inmobiliaria inmoLog = user.getInmobiliaria();
        Optional<UserEntity> u = userEntityService.findById(id);
        if (u.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Inmobiliaria in = u.get().getInmobiliaria();
        if (user.getRole().equals(UserRole.GESTOR) && inmoLog.getId().equals(in.getId()) || user.getRole().equals(UserRole.ADMIN)) {
            u.get().removeGestorInmobiliaria(in);
            service.edit(in);
            userEntityService.delete(u.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @Operation(summary = "Obtener gestores de una inmobiliaria ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se devuelve una lista de gestores",
                    content = {@Content(mediaType = "aplication/json",
                            schema = @Schema(implementation = Inmobiliaria.class))}),
            @ApiResponse(responseCode = "403",
                    description = "No tiene permiso para realizar esta acci??n",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "No hay gestores",
                    content = @Content),
    })
    @GetMapping("/{id}/gestor/")
    public ResponseEntity<List<GetUserDto>> obtenerGestores(@PathVariable UUID id, @AuthenticationPrincipal UserEntity user) {

        Inmobiliaria i = service.findById(id).get();
        if (i.getGestores().isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            if (user.getRole().equals(UserRole.GESTOR) && user.getInmobiliaria().getId().equals(i.getId()) || user.getRole().equals(UserRole.ADMIN)) {
                return ResponseEntity.ok().body(i.getGestores().stream().map(userDtoConverter::convertUserEntityToGetUserDto).collect(Collectors.toList()));
            } else {
                return ResponseEntity.status(403).build();
            }
        }
    }

    @Operation(summary = "Lista todos las inmobiliarias")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se listan correctamente todas las inmobiliarias",
                    content = {@Content(mediaType = "aplication/json",
                            schema = @Schema(implementation = Inmobiliaria.class))}),
            @ApiResponse(responseCode = "404",
                    description = "La lista de inmobiliarias est?? vac??a",
                    content = @Content),
    })
    @GetMapping("/")
    public ResponseEntity<Page<GetInmobiliariaDto>> findAll(@PageableDefault(size = 10, page = 0) Pageable pageable, HttpServletRequest request) {
        Page<Inmobiliaria> inmobiliarias = service.findAll(pageable);
        if (inmobiliarias.isEmpty()) {
            return ResponseEntity
                    .notFound()
                    .build();
        } else {
            Page<GetInmobiliariaDto> listDto = inmobiliarias.map(dtoConverter::getInmobiliariaDatos);
            UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString());
            return ResponseEntity
                    .ok()
                    .header("link", paginationLinksUtils.createLinkHeader(listDto, uriBuilder)).body(listDto);
        }
    }


    @Operation(summary = "Se busca una inmobiliaria por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado la inmobiliaria con ese ID",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Inmobiliaria.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se encuentra la inmobiliaria con ese ID",
                    content = @Content),
    })
    @GetMapping("/{id}")
    public ResponseEntity<GetInmobiliariaIDDto> findOne(@Parameter(description = "El ID de la inmobiliaria que queremos consultar") @PathVariable UUID id) {
        return ResponseEntity
                .of(service.findById(id).map(dtoConverter::inmobiliariaToGetInmobiliariaIDDto));
    }

    @Operation(summary = "Se elimina una inmobiliaria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Se ha eliminado correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Inmobiliaria.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No existe la inmobiliaria",
                    content = @Content),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@Parameter(description = "Id de la inmobiliaria que queremos representar") @PathVariable UUID id) {
        if (service.findById(id).isEmpty()) {
            return ResponseEntity
                    .notFound()
                    .build();
        } else {
            service.deleteById(id);
            return ResponseEntity
                    .noContent()
                    .build();
        }
    }

    @Operation(summary = "Lista de las 10 inmobiliarias con m??s viviendas asociadas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se listan correctamente todas las inmobiliarias",
                    content = {@Content(mediaType = "aplication/json",
                            schema = @Schema(implementation = UserEntity.class))}),
            @ApiResponse(responseCode = "404",
                    description = "La lista de inmobiliarias est?? vac??a",
                    content = @Content),
    })
    @GetMapping("/top")
    public ResponseEntity<List<GetInmobiliariaDto>> findTop() {
        List<Inmobiliaria> topInmobiliarias = service.topInmobiliarias();

        if (topInmobiliarias.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            List<GetInmobiliariaDto> inmobiliariasDTO = new ArrayList<>();
            for (int i = 0; i < topInmobiliarias.size(); i++) {
                inmobiliariasDTO.add(dtoConverter.getInmobiliariaDatosVivienda(topInmobiliarias.get(i)));
            }
            return ResponseEntity
                    .ok().body(inmobiliariasDTO);
        }
    }

}
