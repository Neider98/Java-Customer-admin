package com.customers.admin.mappers;

import com.customers.admin.models.dtos.UserDTO;
import com.customers.admin.models.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);


    @Mapping(target = "id", source = "id")
    @Mapping(target = "sharedKey", source = "sharedKey")
    @Mapping(target = "businessId", source = "businessId")
    @Mapping(target = "phone", source = "phone")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "startDate", source = "startDate")
    @Mapping(target = "endDate", source = "endDate")
    @Mapping(target = "dateAdded", source = "createAt")
    UserDTO userToUserDTO(User user);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "sharedKey", source = "sharedKey")
    @Mapping(target = "businessId", source = "businessId")
    @Mapping(target = "phone", source = "phone")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "startDate", source = "startDate")
    @Mapping(target = "endDate", source = "endDate")
    User userDTOToUser(UserDTO userDTO);

    List<UserDTO> mapToDtoList(List<User> entities);

    default Optional<UserDTO> mapToDtoOptional(Optional<User> entityOptional) {
        return entityOptional.map(this::userToUserDTO);
    }

}
