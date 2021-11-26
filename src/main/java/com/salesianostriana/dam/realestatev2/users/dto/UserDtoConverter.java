package com.salesianostriana.dam.realestatev2.users.dto;

import com.salesianostriana.dam.realestatev2.dto.InteresaDtoConverter;
import com.salesianostriana.dam.realestatev2.dto.ViviendaDtoConverter;
import com.salesianostriana.dam.realestatev2.models.Interesa;
import com.salesianostriana.dam.realestatev2.security.dto.LoginDto;
import com.salesianostriana.dam.realestatev2.users.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserDtoConverter {

    private final ViviendaDtoConverter viviendaDtoConverter;
    private final InteresaDtoConverter interesaDtoConverter;

    public GetUserDto convertUserEntityToGetUserDto(UserEntity user) {
        return GetUserDto.builder()
                .avatar(user.getAvatar())
                .id(user.getId())
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
                .intereses(p.getIntereses().stream().map(interesaDtoConverter::interesaTogetInteresaPropietarioDto).collect(Collectors.toList()))
                .build();
    }

    public LoginDto createUserDtoToLoginDto(CreateUserDto cu){
        return LoginDto.builder()
                .email(cu.getEmail())
                .password(cu.getPassword())
                .build();
    }

}
