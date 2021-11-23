package com.salesianostriana.dam.realestatev2.users.controller;

import com.salesianostriana.dam.realestatev2.models.Vivienda;
import com.salesianostriana.dam.realestatev2.upload.PaginationLinksUtils;
import com.salesianostriana.dam.realestatev2.users.dto.GetPropietarioDto;
import com.salesianostriana.dam.realestatev2.users.dto.GetPropietarioDtoVi;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequiredArgsConstructor
@RequestMapping("/propietario")
@Tag(name="Propietario", description = "Clase controladora de propietarios")
public class UserController {

    private final UserEntityService userEntityService;
    private final UserDtoConverter userDtoConverter;
    private final PaginationLinksUtils paginationLinksUtils;

    @Operation(summary = "Lista todos los propietarios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se listan correctamente todos los propietarios",
                    content = { @Content(mediaType =  "aplication/json",
                            schema = @Schema(implementation = UserEntity.class))}),
            @ApiResponse(responseCode = "404",
                    description = "La lista de propietatios está vacía",
                    content = @Content),
    })
    @GetMapping("/")
    public ResponseEntity<List<GetPropietarioDto>> findAll() {
        List<GetPropietarioDto> usuarios = userEntityService.findAll()
                .stream().filter(usuario -> usuario.getRole().equals(UserRole.PROPIETARIO))
                .map(userDtoConverter::propietarioToGetpropietarioDto)
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
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserEntity.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se encuentra el propietario con ese ID",
                    content = @Content),
    })
    @GetMapping("/{id}")
    public ResponseEntity<GetPropietarioDtoVi> findOne(@Parameter(description = "El ID del propietario que queremos consultar") @PathVariable UUID id) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//            String name=auth.getName();
//        if(name.equals(userEntityService.findById(id).get().getEmail())){
            return ResponseEntity
                    .of(userEntityService.findById(id).map(userDtoConverter::getPropietarioDatosVivienda));
//        }else {
//            return ResponseEntity
//                    .status(403)
//                    .build();
//        }

    }

    @Operation(summary = "Borrar un propietario por su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Se elimina correctamente de la lista",
                    content = { @Content(mediaType =  "aplication/json",
                            schema = @Schema(implementation = UserEntity.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se encuentra propietario con ese id",
                    content = @Content),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@Parameter(description = "El id del propietario que queremos eliminar")@PathVariable UUID id){
        if(userEntityService.findById(id).isEmpty()){
            return ResponseEntity
                    .notFound()
                    .build();
        }else {
            userEntityService.deleteById(id);
            return ResponseEntity
                    .noContent()
                    .build();
        }
    }

    @Operation(summary = "Lista de los 10 propietarios con más viviendas asociadas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se listan correctamente todos los propietarios",
                    content = { @Content(mediaType =  "aplication/json",
                            schema = @Schema(implementation = UserEntity.class))}),
            @ApiResponse(responseCode = "404",
                    description = "La lista de propietarios está vacía",
                    content = @Content),
    })
    @GetMapping("/top")
    public ResponseEntity<List<GetPropietarioDtoVi>> findTop(){
        List<UserEntity> topPropietarios = userEntityService.topPropietarios();

        if(topPropietarios.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        else {
            List<GetPropietarioDtoVi> propietariosDTOVi = new ArrayList<>();
            for (int i = 0; i<topPropietarios.size(); i++) {
                propietariosDTOVi.add(userDtoConverter.getPropietarioDatosVivienda(topPropietarios.get(i)));
            }
            return ResponseEntity
                    .ok().body(propietariosDTOVi);
        }
    }

}
