package com.salesianostriana.dam.realestatev2.controllers;

import com.salesianostriana.dam.realestatev2.dto.CreateViviendaDto;
import com.salesianostriana.dam.realestatev2.dto.GetViviendaDto;
import com.salesianostriana.dam.realestatev2.dto.ViviendaDtoConverter;
import com.salesianostriana.dam.realestatev2.dto.editViviendaDto;
import com.salesianostriana.dam.realestatev2.models.*;
import com.salesianostriana.dam.realestatev2.services.InmobiliariaService;
import com.salesianostriana.dam.realestatev2.services.ViviendaService;
import com.salesianostriana.dam.realestatev2.upload.PaginationLinksUtils;
import com.salesianostriana.dam.realestatev2.users.dto.GetInteresadoDto;
import com.salesianostriana.dam.realestatev2.users.model.UserEntity;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequiredArgsConstructor
@RequestMapping("/vivienda")
@Tag(name="Vivienda", description = "Clase controladora de viviendas")
public class ViviendaController {

    private final ViviendaService service;
    private final UserEntityService userEntityService;
    private final ViviendaDtoConverter dtoConverter;
    private final InmobiliariaService inmobiliariaService;
    private final PaginationLinksUtils paginationLinksUtils;

    @Operation(summary = "Se añade una vivienda")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha creado correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Vivienda.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Hay un error en los datos",
                    content = @Content),
    })
    @PostMapping("/")
    public ResponseEntity<Vivienda> create(@RequestBody CreateViviendaDto viviendaNueva) {
        if (viviendaNueva.getPropietarioId() == null) {
            return ResponseEntity
                    .badRequest()
                    .build();
        }
        Vivienda v = dtoConverter.createViviendaDtoToVivienda(viviendaNueva);
        UserEntity p = userEntityService.findById(viviendaNueva.getPropietarioId()).orElse(null);
        Inmobiliaria i = inmobiliariaService.findById(viviendaNueva.getInmobiliariaId()).orElse(null);
        userEntityService.save(p);
        inmobiliariaService.save(i);
        v.setPropietario(p);
        v.setInmobiliaria(i);

        return ResponseEntity.
                status(HttpStatus.CREATED)
                .body(service.save(v));
    }

    @Operation(summary = "Obtener la lista de todas las viviendas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado una o varias viviendas",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Vivienda.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ninguna vivienda",
                    content = @Content),
    })
    @GetMapping("/")
    public ResponseEntity<Page<GetViviendaDto>> buscarViviendasPorVarios(@RequestParam("titulo") Optional<String> titulo,
                                                                         @RequestParam("provincia") Optional<String> provincia,
                                                                         @RequestParam("direccion") Optional<String> direccion,
                                                                         @RequestParam("poblacion") Optional<String> poblacion,
                                                                         @RequestParam("codigoPostal") Optional<Integer> codigoPostal,
                                                                         @RequestParam("numBanyos") Optional<Integer> numBanyos,
                                                                         @RequestParam("numHabitaciones") Optional<Integer> numHabitaciones,
                                                                         @RequestParam("metrosCuadrados") Optional<Integer> metrosCuadrados,
                                                                         @RequestParam("precio") Optional<Double> precio,
                                                                         @RequestParam("tienePiscina") Optional<Boolean> tienePiscina,
                                                                         @RequestParam("tieneAscensor") Optional<Boolean> tieneAscensor,
                                                                         @RequestParam("tieneGaraje") Optional<Boolean> tieneGaraje,
                                                                         @RequestParam("tipo") Optional<Tipo> tipo,
                                                                         @RequestParam("estado") Optional<Estado> estado,
                                                                         Pageable pageable, HttpServletRequest request) {


        Page<Vivienda> resultado = service.findByArgs(titulo, provincia, direccion, poblacion, codigoPostal, numBanyos, numHabitaciones, metrosCuadrados, precio, tienePiscina, tieneAscensor, tieneGaraje, tipo, estado, pageable);
        if (resultado.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {

            Page<GetViviendaDto> dtoList = resultado.map(dtoConverter::viviendaToGetViviendaDtoAll);
            UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString());
            return ResponseEntity.ok().header("link", paginationLinksUtils.createLinkHeader(dtoList, uriBuilder)).body(dtoList);
        }

    }

    @Operation(summary = "Se busca una vivienda por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado la vivienda",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Vivienda.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No existe la vivienda",
                    content = @Content),
    })
    @GetMapping("/{id}")
    public ResponseEntity<GetViviendaDto> findOne(@Parameter(description = "El ID de la vivienda que queremos consultar") @PathVariable UUID id) {
        return ResponseEntity
                .of(service.findById(id).map(dtoConverter::viviendaToGetViviendaDto));
    }

/* No será necesario usar este método porque se creo´uno que filtra
    @GetMapping("/")
    public ResponseEntity<Page<GetViviendaDto>> findAll(@PageableDefault(size = 10, page = 0) Pageable pageable, HttpServletRequest request){
        Page<Vivienda> v = service.findAll(pageable);
        if(v.isEmpty()){
            return ResponseEntity
                    .notFound()
                    .build();
        }else{
            Page<GetViviendaDto> listDto = v.map(dtoConverter::viviendaToGetViviendaDtoAll);
            UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString());


            return ResponseEntity.ok().header("link", paginationLinksUtils.createLinkHeader(listDto, uriBuilder)).body(listDto);
        }
    }

 */

    @Operation(summary = "Se modifica una vivienda por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Se ha encontrado la vivienda con ese ID y se edita correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Vivienda.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se encuentra una vivienda con ese ID",
                    content = @Content),
    })
    @PutMapping("/{id}")
    public ResponseEntity<Vivienda> edit(@RequestBody editViviendaDto vivienda, @Parameter(description = "El ID de la vivienda que queremos editar") @PathVariable UUID id) {
        return ResponseEntity.of(
                service.findById(id).map(v -> {
                    v.setTitulo(vivienda.getTitulo());
                    v.setDescripcion(vivienda.getDescripcion());
                    v.setAvatar(vivienda.getAvatar());
                    v.setLatlng(vivienda.getLatlng());
                    v.setDireccion(vivienda.getDireccion());
                    v.setCodigoPostal(vivienda.getCodigoPostal());
                    v.setPoblacion(vivienda.getPoblacion());
                    v.setProvincia(vivienda.getProvincia());
                    v.setTipo(vivienda.getTipo());
                    v.setEstado(vivienda.getEstado());
                    v.setPrecio(vivienda.getPrecio());
                    v.setNumHabitaciones(vivienda.getNumHabitaciones());
                    v.setNumBanyos(vivienda.getNumBanyos());
                    v.setTienePiscina(vivienda.isTienePiscina());
                    v.setTieneAscensor(vivienda.isTieneAscensor());
                    v.setTieneGaraje(vivienda.isTieneGaraje());
                    service.save(v);
                    return v;
                })
        );
    }

    @Operation(summary = "Borrar una vivienda")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Se elimina correctamente la vivienda",
                    content = {@Content(mediaType = "aplication/json",
                            schema = @Schema(implementation = Vivienda.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se encuentra una vivienda con ese id",
                    content = @Content),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@Parameter(description = "El id de la vivienda que queremos eliminar") @PathVariable UUID id) {
        if (service.findById(id).isEmpty()) {
            return ResponseEntity
                    .notFound()
                    .build();
        } else {
            Vivienda v=service.findById(id).get();
            if(v.getInmobiliaria()!=null){
                v.removeFromInmobiliaria(v.getInmobiliaria());
            }
            v.removePropietario(v.getPropietario());
            service.deleteById(id);
            return ResponseEntity
                    .noContent()
                    .build();
        }
    }

    /*
    @Operation(summary = "Filtrar lista de viviendas por ciertos parámetros")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se muestra la lista correctamente",
                    content = { @Content(mediaType =  "aplication/json",
                            schema = @Schema(implementation = Vivienda.class))}),
            @ApiResponse(responseCode = "404",
                    description = "La lista de viviendas esta vacia",
                    content = @Content),
    })
    @GetMapping(value = "/", params = {"search"})
    public ResponseEntity<List<?>> busquedaDeViviendas(@RequestParam("search") String search) {
        ViviendaSpecificationBuilder builder = new ViviendaSpecificationBuilder();

        // Validamos la cadena de búsqueda
        Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
        Matcher matcher = pattern.matcher(search + ",");
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
        }

        Specification<Vivienda> spec = builder.build();
        return buildResponseFromQuery(service.viviendasConSpecification(spec), dtoConverter::viviendaToGetViviendaDto);

    }

    private ResponseEntity<List<?>> buildResponseFromQuery(List<Vivienda> data, Function<Vivienda, GetViviendaDto> function) {
        if (data.isEmpty())
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(data.stream()
                    .map(function)
                    .collect(Collectors.toList())
            );

    }
*/

    @Operation(summary = "Lista de las 10 viviedas con más intereses")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se listan correctamente todas los viviendas",
                    content = {@Content(mediaType = "aplication/json",
                            schema = @Schema(implementation = UserEntity.class))}),
            @ApiResponse(responseCode = "404",
                    description = "La lista de viviendas está vacía",
                    content = @Content),
    })
    @GetMapping("/top")
    public ResponseEntity<List<GetViviendaDto>> findTop(){
        List<Vivienda> topViviendas = service.top3Viviendas();

        if(topViviendas.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        else {
            List<GetViviendaDto> viviendasDTO = new ArrayList<>();
            for (int i = 0; i<topViviendas.size(); i++) {
                viviendasDTO.add(dtoConverter.viviendaToGetViviendaDtoAll(topViviendas.get(i)));
            }
            return ResponseEntity
                    .ok().body(viviendasDTO);
        }
    }

    @Operation(summary = "Se establece una inmobiliaria dentro de una vivienda")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se añadió corectamente a la vivienda",
                    content = {@Content(mediaType = "aplication/json",
                            schema = @Schema(implementation = Vivienda.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se encuentra una vivienda o una inmobiliaria con ese id",
                    content = @Content),
    })
    @PostMapping("/{id}/inmobiliaria/{id2}")
    public ResponseEntity<GetViviendaDto> asignarInmobiliariaAVivienda(@Parameter(description = "El id de la vivienda a la que queremos añadirle una inmobiliaria ") @PathVariable UUID id, @Parameter(description = "El id de la inmobiliaria que añadiremos a una vivienda") @PathVariable UUID id2) {
        return service.asignarInmobiliariaAVivienda(id, id2);
    }

    @Operation(summary = "Borra una inmobiliaria de una vivienda")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "La inmobiliaria se ha borrado correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Vivienda.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No existe la inmobiliaria dentro de la vivienda",
                    content = @Content),
    })
    @DeleteMapping("/{id}/inmobiliaria/{id2}")
    public ResponseEntity<?> eliminarInmobiliariaDeVivienda(@Parameter(description = "El id de la vivienda a la que queremos eliminarle una inmobiliaria ") @PathVariable UUID id, @Parameter(description = "El id de la inmobiliaria que eliminaremos de una vivienda") @PathVariable UUID id2) {
        return service.eliminarInmobiliariaDeVivienda(id, id2);
    }

    @Operation(summary = "Listar interesados por una vivienda")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado la lista de interesados",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Vivienda.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No hay interesados",
                    content = @Content),
    })
    @GetMapping("/interesado/{id}")
    public ResponseEntity <List<Interesa>> listInteresados(@Parameter(description = "El ID de la vivienda que queremos consultar") @PathVariable UUID id) {
        return service.listInteresados(id);
    }

    @Operation(summary = "Añadir un nuevo me interesa a una vivienda, creando un interesado al mismo tiempo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se crea correctamente el me interesa",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Vivienda.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No existe la vivienda a la que queremos añadir el interés",
                    content = @Content),
    })
    @PostMapping("/{id}/meinteresa")
    public ResponseEntity<UserEntity> addInteresadoAndInteresa(@Parameter(description = "La dto del interesado a crear")@RequestBody GetInteresadoDto interesadoDTO, @Parameter(description = "El ID de la vivienda que queremos consultar") @PathVariable UUID id) {
        return service.createInteresaAndInteresado(interesadoDTO, id);
    }

    @Operation(summary = "Añadir un nuevo me interesa a una vivienda, con un interesado ya existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se crea correctamente el me interesa",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Vivienda.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No existe la vivienda o el interesado a la que queremos añadir el interés",
                    content = @Content),
    })
    @PostMapping("/{id}/meinteresa/{id2}")
    public ResponseEntity<UserEntity> createInteresa(GetInteresadoDto interesadoDto, UUID id){
        return service.createInteresa(interesadoDto, id);
    }



    @Operation(summary = "Eliminar el interés de un interesado por una vivienda")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "El interesado se ha borrado correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Vivienda.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No existe el interes",
                    content = @Content),
    })
    public ResponseEntity<?> eliminarInteres(GetInteresadoDto id2, UUID id){
        return service.eliminarInteres(id, id2);
    }

}