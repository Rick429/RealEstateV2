package com.salesianostriana.dam.realestatev2.users.service;

import com.salesianostriana.dam.realestatev2.models.Inmobiliaria;
import com.salesianostriana.dam.realestatev2.models.Vivienda;
import com.salesianostriana.dam.realestatev2.services.base.BaseService;
import com.salesianostriana.dam.realestatev2.users.dto.CreateUserDto;
import com.salesianostriana.dam.realestatev2.users.model.UserEntity;
import com.salesianostriana.dam.realestatev2.users.model.UserRole;
import com.salesianostriana.dam.realestatev2.users.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service("userDetailsService")
@RequiredArgsConstructor
public class UserEntityService extends BaseService<UserEntity, UUID, UserEntityRepository> implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.repositorio.findFirstByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException(email + " no encontrado"));
    }


    // Este método lo mejoraremos en el próximo tema
    public UserEntity save(CreateUserDto newUser, UserRole role) {
        if (newUser.getPassword().contentEquals(newUser.getPassword2())) {
            UserEntity userEntity = UserEntity.builder()
                    .password(passwordEncoder.encode(newUser.getPassword()))
                    .avatar(newUser.getAvatar())
                    .nombre(newUser.getName())
                    .apellidos(newUser.getLastname())
                    .email(newUser.getEmail())
                    .role(role)
                    .build();
            return save(userEntity);
        } else {
            return null;
        }
    }

    public UserEntity saveGestor(CreateUserDto newUser, Inmobiliaria i) {
        if (newUser.getPassword().contentEquals(newUser.getPassword2())) {
            UserEntity userEntity = UserEntity.builder()
                    .password(passwordEncoder.encode(newUser.getPassword()))
                    .avatar(newUser.getAvatar())
                    .nombre(newUser.getName())
                    .apellidos(newUser.getLastname())
                    .email(newUser.getEmail())
                    .role(UserRole.GESTOR)
                    .inmobiliaria(i)
                    .build();
            return save(userEntity);
        } else {
            return null;
        }
    }



    public List<UserEntity> topPropietarios() {
        return repositorio.topPropietarios();
    }

    public List<UserEntity> findUserByRole(UserRole userRole) { return repositorio.findUserByRole(userRole);}

//    public UserEntity findGestor (UUID id) {return repositorio.findGestor(id);};

    public List<Vivienda> findViviendaById (UUID id) { return repositorio.findViviendaById(id);}
}
