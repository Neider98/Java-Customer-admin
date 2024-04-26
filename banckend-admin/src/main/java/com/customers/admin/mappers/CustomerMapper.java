package com.customers.admin.mappers;

import com.customers.admin.models.dtos.CustomerDTO;
import com.customers.admin.models.entities.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);


    @Mapping(target = "id", source = "id")
    @Mapping(target = "sharedKey", source = "sharedKey")
    @Mapping(target = "businessId", source = "businessId")
    @Mapping(target = "phone", source = "phone")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "startDate", source = "startDate")
    @Mapping(target = "endDate", source = "endDate")
    @Mapping(target = "dateAdded", source = "createAt")
    CustomerDTO customerToCustomerDTO(Customer customer);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "sharedKey", source = "sharedKey")
    @Mapping(target = "businessId", source = "businessId")
    @Mapping(target = "phone", source = "phone")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "startDate", source = "startDate")
    @Mapping(target = "endDate", source = "endDate")
    @Mapping(target = "createAt", source = "dateAdded")
    Customer customerDTOToCustomer(CustomerDTO customerDTO);

    List<CustomerDTO> mapToDtoList(List<Customer> entities);

    default Optional<CustomerDTO> mapToDtoOptional(Optional<Customer> entityOptional) {
        return entityOptional.map(this::customerToCustomerDTO);
    }

}
