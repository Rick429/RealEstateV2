package com.salesianostriana.dam.realestatev2.users.dto;

import com.salesianostriana.dam.realestatev2.dto.ViviendaDtoConverter;
import com.salesianostriana.dam.realestatev2.models.Interesa;
import com.salesianostriana.dam.realestatev2.users.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserDtoConverter {

    private final ViviendaDtoConverter viviendaDtoConverter;

    public GetUserDto convertUserEntityToGetUserDto(UserEntity user) {
        return GetUserDto.builder()
                .avatar(user.getAvatar())
                .name(user.getNombre())
                .lastname(user.getApellidos())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();


    }

    public GetPropietarioDto propietarioToGetpropietarioDto(UserEntity p){
        return GetPropietarioDto.builder()
                .id(p.getId())
                .avatar(p.getAvatar())
                .nombre(p.getNombre())
                .apellidos(p.getApellidos())
                .direccion(p.getDireccion())
                .email(p.getEmail())
                .telefono(p.getTelefono())
                .numeroViviendas(p.getViviendas().size())
                .build();
    }

    public GetPropietarioDtoVi getPropietarioDatosVivienda(UserEntity p){
        return GetPropietarioDtoVi
                .builder()
                .id(p.getId())
                .avatar(p.getAvatar())
                .nombre(p.getNombre())
                .apellidos(p.getApellidos())
                .direccion(p.getDireccion())
                .email(p.getEmail())
                .telefono(p.getTelefono())
                .listaViviendas(p.getViviendas().stream().map(viviendaDtoConverter::viviendaToGetViviendaBasicaDto).collect(Collectors.toList()))
                .build();
    }


    public UserEntity getInteresadoDtoToInteresado(GetInteresadoDto i){
        return UserEntity.builder()
                .id(i.getId())
                .nombre(i.getNombre())
                .apellidos(i.getApellidos())
                .direccion(i.getDireccion())
                .email(i.getEmail())
                .telefono(i.getTelefono())
                .avatar(i.getAvatar())
                .build();
    }

    public Interesa getMeInteresa(GetInteresadoDto i){
        return Interesa.builder()
                .createdDate(LocalDateTime.now())
                .mensaje(i.getMensaje())
                .build();
    }

    public GetInteresadoDto interesadoToGetInteresadoDto(UserEntity i){
        return GetInteresadoDto.builder()
                .id(i.getId())
                .nombre(i.getNombre())
                .apellidos(i.getApellidos())
                .direccion(i.getDireccion())
                .email(i.getEmail())
                .telefono(i.getTelefono())
                .avatar(i.getAvatar())
                .build();
    }

    public GetInteresadoIndividual interesadoToGetInteresadoIndividual(UserEntity i){
        return GetInteresadoIndividual.builder()
                .id(i.getId())
                .nombre(i.getNombre())
                .apellidos(i.getApellidos())
                .direccion(i.getDireccion())
                .email(i.getEmail())
                .telefono(i.getTelefono())
                .avatar(i.getAvatar()).build();
    }

    public UserEntity createUserDtoToUserEntity(CreateUserDto cu){
        return UserEntity.builder()
                .nombre(cu.getName())
                .password(cu.getPassword())
                .apellidos(cu.getLastname())
                .email(cu.getEmail())
                .build();
    }

}
