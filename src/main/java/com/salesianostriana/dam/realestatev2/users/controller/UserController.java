package com.salesianostriana.dam.realestatev2.users.controller;

import com.salesianostriana.dam.realestatev2.models.Vivienda;
import com.salesianostriana.dam.realestatev2.upload.PaginationLinksUtils;
import com.salesianostriana.dam.realestatev2.users.dto.GetPropietarioDto;
import com.salesianostriana.dam.realestatev2.users.dto.GetPropietarioDtoVi;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequiredArgsConstructor
@RequestMapping("/propietario")
@Tag(name = "Propietario", description = "Clase controladora de propietarios")
public class UserController {

    private final UserEntityService userEntityService;
    private final UserDtoConverter userDtoConverter;
    private final PaginationLinksUtils paginationLinksUtils;

    @Operation(summary = "Lista todos los propietarios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se listan correctamente todos los propietarios",
                    content = {@Content(mediaType = "aplication/json",
                            schema = @Schema(implementation = UserEntity.class))}),
            @ApiResponse(responseCode = "404",
                    description = "La lista de propietatios está vacía",
                    content = @Content),
    })
    @GetMapping("/")
    public ResponseEntity<List<GetPropietarioDto>> findAll() {
//        List<GetPropietarioDto> usuarios = userEntityService.findAll()
//                .stream().filter(usuario -> usuario.getRole().equals(UserRole.PROPIETARIO))
//                .map(userDtoConverter::propietarioToGetpropietarioDto)
//                .collect(Collectors.toList());
        List<GetPropietarioDto> usuarios = userEntityService.findUserByRole(UserRole.PROPIETARIO)
                .stream().map(userDtoConverter::propietarioToGetpropietarioDto)
                .collect(Collectors.toList());
        if (usuarios.isEmpty()) {
            return ResponseEntity
                    .notFound()
                    .build();
        } else {
            return ResponseEntity
                    .ok()
                    .body(usuarios);
        }
    }

    @Operation(summary = "Se busca un propietario por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Se ha encontrado el propietario con ese ID",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserEntity.class))}),
            @ApiResponse(responseCode = "403",
                    description = "No tiene permiso para realizar esta acción",
                    content = @Content),
    })
    @GetMapping("/{id}")
    public ResponseEntity<GetPropietarioDtoVi> findOne(@Parameter(description = "El ID del propietario que queremos consultar") @PathVariable UUID id, @AuthenticationPrincipal UserEntity user) {
        UserEntity usuarioId = userEntityService.findById(id).get();
        if (user.getEmail().equals(usuarioId.getEmail()) || user.getRole().equals(UserRole.ADMIN)) {
            return ResponseEntity
                    .of(userEntityService.findById(id).map(userDtoConverter::getPropietarioDatosVivienda));
        } else {
            return ResponseEntity.status(403).build();
        }
    }

    @Operation(summary = "Borrar un propietario por su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Se elimina correctamente el propietario",
                    content = {@Content(mediaType = "aplication/json",
                            schema = @Schema(implementation = UserEntity.class))}),
            @ApiResponse(responseCode = "403",
                    description = "No tiene permiso para realizar esta acción",
                    content = @Content),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@Parameter(description = "El id del propietario que queremos eliminar") @PathVariable UUID id,
                                        @AuthenticationPrincipal UserEntity user) {
        UserEntity userId = userEntityService.findById(id).get();
        if (user.getEmail().equals(userId.getEmail()) || user.getRole().equals(UserRole.ADMIN)) {
            userEntityService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity
                    .status(403)
                    .build();
        }
    }

    @Operation(summary = "Listar interesados por alguna vivienda")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado la lista de interesados",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Vivienda.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No hay interesados",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "No tiene permisos para realizar esta acción",
                    content = @Content),
    })
    @GetMapping("/interesado/")
    public ResponseEntity<List<GetUserDto>> listInteresados(@AuthenticationPrincipal UserEntity user) {

        if (user.getRole().equals(UserRole.ADMIN)) {
            List<UserEntity> intereses = userEntityService.findAll();
            List<UserEntity> interesados = new ArrayList<>();

            for (UserEntity interesado : intereses) {
                if (!interesado.getIntereses().isEmpty())
                    interesados.add(interesado);
            }
            if (interesados.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity
                    .ok().body(interesados.stream()
                            .map(userDtoConverter::convertUserEntityToGetUserDto)
                            .collect(Collectors.toList()));
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @Operation(summary = "Buscar un interesado por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado el interesado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Vivienda.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No hay un interesado con ese id",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "No tiene permisos para realizar esta acción",
                    content = @Content),
    })
    @GetMapping("/interesado/{id}")
    public ResponseEntity<GetUserDto> interesadoPorId(@AuthenticationPrincipal UserEntity user, @PathVariable UUID id) {
        Optional<UserEntity> interesado = userEntityService.findById(id);
        if(interesado.get().getIntereses().isEmpty()){
            return ResponseEntity.notFound().build();
        }
        if(user.getRole().equals(UserRole.PROPIETARIO)&&user.getId().equals(id)||
                user.getRole().equals(UserRole.ADMIN)){
            return ResponseEntity.of(userEntityService.findById(id).map(userDtoConverter::convertUserEntityToGetUserDto));

        }else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

    }

    @Operation(summary = "Lista de los 10 propietarios con más viviendas asociadas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se listan correctamente todos los propietarios",
                    content = {@Content(mediaType = "aplication/json",
                            schema = @Schema(implementation = UserEntity.class))}),
            @ApiResponse(responseCode = "404",
                    description = "La lista de propietarios está vacía",
                    content = @Content),
    })
    @GetMapping("/top")
    public ResponseEntity<List<GetPropietarioDtoVi>> findTop() {
        List<UserEntity> topPropietarios = userEntityService.topPropietarios();

        if (topPropietarios.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            List<GetPropietarioDtoVi> propietariosDTOVi = new ArrayList<>();
            for (int i = 0; i < topPropietarios.size(); i++) {
                propietariosDTOVi.add(userDtoConverter.getPropietarioDatosVivienda(topPropietarios.get(i)));
            }
            return ResponseEntity
                    .ok().body(propietariosDTOVi);
        }
    }

}
