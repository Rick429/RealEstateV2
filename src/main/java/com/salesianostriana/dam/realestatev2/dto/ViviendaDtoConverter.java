package com.salesianostriana.dam.realestatev2.dto;

import com.salesianostriana.dam.realestatev2.models.Vivienda;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ViviendaDtoConverter {

    private final InteresaDtoConverter interesaDtoConverter;

    public GetViviendaBasicaDto viviendaToGetViviendaBasicaDto(Vivienda v){
        return  GetViviendaBasicaDto
                .builder()
                .id(v.getId())
                .titulo(v.getTitulo())
                .direccion(v.getDireccion())
                .precio(v.getPrecio())
                .tipo(v.getTipo())
                .estado(v.getEstado())
                .inmobiliaria(v.getInmobiliaria()==null?"Sin inmobiliaria":v.getInmobiliaria().getNombre())
                .build();
    }

    public GetViviendaDatosDto viviendaToGetViviendaDatosDto(Vivienda v){
        return GetViviendaDatosDto.builder()
                .id(v.getId())
                .titulo(v.getTitulo())
                .direccion(v.getDireccion())
                .estado(v.getEstado())
                .tipo(v.getTipo())
                .precio(v.getPrecio())
                .propietarioId(v.getPropietario().getId())
                .build();

    }

    public EditViviendaDto viviendaToEditViviendaDto(Vivienda v){
        return  EditViviendaDto
                .builder()
                .id(v.getId())
                .titulo(v.getTitulo())
                .descripcion(v.getDescripcion())
                .avatar(v.getAvatar())
                .latlng(v.getLatlng())
                .direccion(v.getDireccion())
                .codigoPostal(v.getCodigoPostal())
                .poblacion(v.getPoblacion())
                .provincia(v.getProvincia())
                .tipo(v.getTipo())
                .estado(v.getEstado())
                .precio(v.getPrecio())
                .numHabitaciones(v.getNumHabitaciones())
                .numBanyos(v.getNumBanyos())
                .tienePiscina(v.isTienePiscina())
                .tieneAscensor(v.isTieneAscensor())
                .tieneGaraje(v.isTieneGaraje())
                .metrosCuadrados(v.getMetrosCuadrados())
                .build();
    }

    public Vivienda editViviendaDtoToVivienda(EditViviendaDto v){
        return  Vivienda
                .builder()
                .id(v.getId())
                .titulo(v.getTitulo())
                .descripcion(v.getDescripcion())
                .avatar(v.getAvatar())
                .latlng(v.getLatlng())
                .direccion(v.getDireccion())
                .codigoPostal(v.getCodigoPostal())
                .poblacion(v.getPoblacion())
                .provincia(v.getProvincia())
                .tipo(v.getTipo())
                .estado(v.getEstado())
                .precio(v.getPrecio())
                .numHabitaciones(v.getNumHabitaciones())
                .numBanyos(v.getNumBanyos())
                .tienePiscina(v.isTienePiscina())
                .tieneAscensor(v.isTieneAscensor())
                .tieneGaraje(v.isTieneGaraje())
                .metrosCuadrados(v.getMetrosCuadrados())
                .build();
    }

    public GetViviendaDto viviendaToGetViviendaDto(Vivienda v) {
        return GetViviendaDto
                .builder()
                .id(v.getId())
                .precio(v.getPrecio())
                .tipo(v.getTipo())
                .direccion(v.getDireccion())
                .descripcion(v.getDescripcion())
                .metrosCuadrados(v.getMetrosCuadrados())
                .latlng(v.getLatlng())
                .avatarVivienda(v.getAvatar())
                .avatarPropietario(v.getPropietario().getAvatar())
                .telefono(v.getPropietario().getTelefono())
                .email(v.getPropietario().getEmail())
                .intereses(v.getIntereses().size())
                .tieneGaraje(v.isTieneGaraje())
                .tieneAscensor(v.isTieneAscensor())
                .tienePiscina(v.isTienePiscina())
                .numHabitaciones(v.getNumHabitaciones())
                .numBanyos(v.getNumBanyos())
                .titulo(v.getTitulo())
                .nombre(v.getPropietario().getNombre())
                .apellidos(v.getPropietario().getApellidos())
                .poblacion(v.getPoblacion())
                .provincia(v.getProvincia())
                .codigoPostal(v.getCodigoPostal())
                .tipo(v.getTipo())
                .estado(v.getEstado())
                .propietarioId(v.getPropietario().getId())
                .inmobiliariaId(v.getInmobiliaria()==null?null:v.getInmobiliaria().getId())
                .interes(v.getIntereses().stream().map(interesaDtoConverter::interesaToGetInteresaDto).collect(Collectors.toList()))
                .build();
    }


    public GetViviendaDto viviendaToGetViviendaDtoAll(Vivienda v) {
        return GetViviendaDto
                .builder()
                .id(v.getId())
                .precio(v.getPrecio())
                .tipo(v.getTipo())
                .direccion(v.getDireccion())
                .descripcion(v.getDescripcion())
                .metrosCuadrados(v.getMetrosCuadrados())
                .latlng(v.getLatlng())
                .avatarVivienda(v.getAvatar())
                .avatarPropietario(v.getPropietario().getAvatar())
                .telefono(v.getPropietario().getTelefono())
                .email(v.getPropietario().getEmail())
                .intereses(v.getIntereses().size())
                .tieneGaraje(v.isTieneGaraje())
                .tieneAscensor(v.isTieneAscensor())
                .tienePiscina(v.isTienePiscina())
                .numHabitaciones(v.getNumHabitaciones())
                .numBanyos(v.getNumBanyos())
                .titulo(v.getTitulo())
                .nombre(v.getPropietario().getNombre())
                .apellidos(v.getPropietario().getApellidos())
                .poblacion(v.getPoblacion())
                .provincia(v.getProvincia())
                .codigoPostal(v.getCodigoPostal())
                .tipo(v.getTipo())
                .estado(v.getEstado())
                .propietarioId(v.getPropietario().getId())
                .inmobiliariaId(v.getInmobiliaria()==null?null:v.getInmobiliaria().getId())
                .build();
    }

    public Vivienda createViviendaDtoToVivienda(CreateViviendaDto v) {
        return Vivienda
                .builder()
                .titulo( v.getTitulo())
                .descripcion(v.getDescripcion())
                .avatar(v.getAvatar())
                .latlng(v.getLatlng())
                .direccion(v.getDireccion())
                .poblacion(v.getPoblacion())
                .provincia(v.getProvincia())
                .codigoPostal(v.getCodigoPostal())
                .numHabitaciones(v.getNumHabitaciones())
                .metrosCuadrados(v.getMetrosCuadrados())
                .numBanyos(v.getNumBanyos())
                .tipo(v.getTipo())
                .estado(v.getEstado())
                .precio(v.getPrecio())
                .tienePiscina(v.isTienePiscina())
                .tieneAscensor(v.isTieneAscensor())
                .tieneGaraje(v.isTieneGaraje())
                .build();

    }


}