package com.customers.admin.services;

import com.customers.admin.models.entities.Customer;

import java.util.List;
import java.util.Optional;

public interface ICustomerService {

    List<Customer> findAllCustomer();

    Optional<Customer> findCustomerById(Long id);

    Customer customerUser(Customer customer);

    Optional<Customer> updateCustomer(Customer customer);

    Optional<Customer> deleteCustomer(Long customerId);



}
