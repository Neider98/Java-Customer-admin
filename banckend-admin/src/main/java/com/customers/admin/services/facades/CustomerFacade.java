package com.customers.admin.services.facades;

import com.customers.admin.mappers.CustomerMapper;
import com.customers.admin.models.dtos.CustomerDTO;
import com.customers.admin.models.entities.Customer;
import com.customers.admin.services.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CustomerFacade {

    private final ICustomerService customerService;
    private final CustomerMapper customerMapper;

    /**
     *
     * @param customerService
     * @param customerMapper
     */
    @Autowired
    public CustomerFacade(ICustomerService customerService, CustomerMapper customerMapper) {
        this.customerService = customerService;
        this.customerMapper = customerMapper;
    }

    /**
     *
     * @param id
     * @return
     */
    public Optional<CustomerDTO> findCustomerById(Long id) {
        return customerMapper.mapToDtoOptional(customerService.findCustomerById(id));
    }

    /**
     *
     * @return
     */
    public List<CustomerDTO> findAllCustomers() {
        return customerMapper.mapToDtoList(customerService.findAllCustomer());
    }

    /**
     *
     * @param user
     * @return
     */
    public CustomerDTO saveCustomers(CustomerDTO user) {
        Customer customerEntity = customerMapper.customerDTOToCustomer(user);
        return customerMapper.customerToCustomerDTO(customerService.customerUser(customerEntity));
    }

    /**
     *
     * @param customerDTO
     * @return
     */
    public Optional<CustomerDTO> updateCustomers(CustomerDTO customerDTO){
        Customer customerEntity = customerMapper.customerDTOToCustomer(customerDTO);
        return customerMapper.mapToDtoOptional(customerService.updateCustomer(customerEntity));
    }

}
